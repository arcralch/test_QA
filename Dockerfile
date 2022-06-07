FROM maven:3.6.0-jdk-10

ARG localuid=101
WORKDIR /test_QA/
EXPOSE 9001
#RUN apt-get update
#ENV PUSH_GATE="192.168.0.12:9091"
ENV localuid=$arglocaluid

RUN apt-get update && apt-get install -y --no-install-recommends \
    jq curl git chromium vim nano wget
RUN apt-get update
RUN apt-get -f install 

#Install Gauge
RUN curl -Ssl https://downloads.gauge.org/stable | sed 's/latest/15176631/g' | sh -s -- --location=/usr/sbin/
RUN gauge telemetry off
RUN gauge install java
RUN gauge install json-report
RUN gauge install html-report
RUN gauge install screenshot

#Install Chrome
RUN wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
RUN dpkg -i google-chrome-stable_current_amd64.deb; apt-get -fy install

COPY ./bin/ /test_QA/bin/

#Tasker will make sure it runs every 5 minutes
RUN chmod a+x /test_QA/bin/*
RUN git clone https://arcralch:ralch.07@github.com/arcralch/test_QA.git

RUN useradd -ms /bin/bash ngnix
USER ngnix
RUN echo user created
USER root
CMD ["sh", "-c", "/test_QA/bin/entrypoint.sh"]
