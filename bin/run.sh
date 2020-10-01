#!/bin/bash
echo $(date)
echo $CASES
echo $ENV
echo $METRIC_COMPL
cd /test_QA/test_QA && git pull
cd /test_QA/test_QA && mvn clean
cd /test_QA/test_QA && mvn package -DskipTests 
cd /test_QA/test_QA && mvn gauge:execute -DspecsDir=specs/$CASES -Denv=$ENV
sh /test_QA/test_QA/bin/prometrics.sh
sh /test_QA/test_QA/bin/checkiotenv.sh
