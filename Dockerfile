FROM maven:3.6.0-jdk-10

ARG localuid=101
WORKDIR /test_QA/
EXPOSE 9001
#RUN apt-get update
#ENV PUSH_GATE="192.168.0.12:9091"
ENV localuid=$arglocaluid

RUN apt-key adv --keyserver keyserver.ubuntu.com --recv-keys 648ACFD622F3D138
RUN apt-get update && apt-get install -y --no-install-recommends \
    jq curl git chromium vim nano

RUN curl -Ssl https://downloads.gauge.org/stable | sed 's/latest/15176631/g' | sh -s -- --location=/usr/sbin/
RUN gauge telemetry off
RUN gauge install java --version 0.7.1
RUN gauge install json-report
RUN gauge install html-report --version 4.0.6

COPY ./bin/ /test_QA/bin/

#Tasker will make sure it runs every 5 minutes
RUN chmod a+x /test_QA/bin/*
RUN git clone https://arcralch:ralch.07@github.com/arcralch/test_QA.git

RUN useradd -ms /bin/bash ngnix
USER ngnix
RUN echo user created
USER root
CMD ["sh", "-c", "/test_QA/bin/entrypoint.sh"]
