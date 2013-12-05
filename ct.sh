#!/bin/sh

sbt clean "^ publish-local" && \
cd integration-test-apps/JavaSbt013 && \
sbt clean gen-sonar-prop && \
cd target && \
/home/michael/Projekte/sonar/vagrant-sonar/sonar-runner-2.3/bin/sonar-runner && \
cd /home/michael/Projekte/sonar/sbt-sonar/integration-test-apps/JavaPlay215 && \
sbt clean jacoco:cover gen-sonar-prop && \
cd target && \
/home/michael/Projekte/sonar/vagrant-sonar/sonar-runner-2.3/bin/sonar-runner && \
echo $?