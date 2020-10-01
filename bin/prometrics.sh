#!/bin/bash
REPORT=/test_QA/test_QA/reports/json-report
FILE=$REPORT/result.json
DATE=$(date +"%F")

# Validate & push the metrics
if [ -f $FILE ]; then
    echo "File $FILE exists."
    cat $REPORT/result.json | \
    # Extract a nice json from last report
    #jq -r '.projectName as $project |.environment as $env | .specResults[] |"test_QA {env=\"\($env)\", spec=\"\(.specHeading|gsub("\"";"\\\""))\", result=\"\(.executionStatus)\"} \(.executionTime)"' | \
    jq -r '.projectName as $project |.environment as $env | .specResults[] |.specHeading as $spec| .scenarios[] |.scenarioHeading as $scen|.items[]| select(.itemType=="step") |.stepText as $step|"test_QA{env=\"\($env)\", project=\"\($project|gsub("\"";"\\\""))\", operation=\"du\", spec=\"\($spec|gsub("\"";"\\\""))\", scenario=\"\($scen|gsub("\"";"\\\""))\", step=\"\($step|gsub("\"";"\\\""))\", result=\"\(.result.status)\"}  \(.result.executionTime)"' | \
    # Send to the metric server
    curl -v --data-binary @- $PUSH_GATE/metrics/job/ngt
    name=`uname -n`
    timestamp=`date +%s`
    result=`jq -r '.executionStatus' $REPORT/result.json`
    echo "prometrics_exec{name=\"$name\", result=\"$result\"} $timestamp" | curl --data-binary @- $PUSH_GATE/metrics/job/ngt_prometrics
    cp -r /test_QA/test_QA/reports/html-report/*  /root/test_QA/HttpShared/
    cp -r /test_QA/test_QA/reports/html-report /root/test_QA/HttpShared/html-report.$DATE    
    # usermod -u 101 /root/ngt-bcr-qa/HttpShared/
    cd /root/test_QA/HttpShared/ & chown 101:101 -R *


else
    echo "File $FILE does not exist."
fi
