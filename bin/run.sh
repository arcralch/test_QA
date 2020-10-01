#!/bin/bash
echo $(date)
echo $CASES
echo $ENV
echo $METRIC_COMPL
cd /test_QA/test_QA && git pull
cd /ngt-codex-qa/ngt-codex-qa && mvn clean
cd /ngt-codex-qa/ngt-codex-qa && mvn package -DskipTests 
cd /ngt-codex-qa/ngt-codex-qa && mvn gauge:execute -DspecsDir=specs/$CASES -Denv=$ENV
sh /test_QA/test_QA/bin/prometrics.sh
sh /test_QA/test_QA/bin/checkiotenv.sh
