#!/bin/bash

curl -L --max-time 5 -s -o /dev/null -I -w "%{http_code}" http://192.168.0.12:80/ | \
    awk '{print "env_status " $1 }' | \
    curl -v --data-binary @- $PUSH_GATE/metrics/job/environment_status