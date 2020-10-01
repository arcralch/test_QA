#!/bin/bash
echo $(date)
cd /test_QA/test_QA && git pull
cd /test_QA/test_QA && mvn test
sh /test_QA/test_QA/bin/prometrics.sh
sh /test_QA/test_QA/bin/checkiotenv.sh
