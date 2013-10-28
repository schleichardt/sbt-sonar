#!/bin/sh

./sbt-cross.sh publish-local
cd integration-test-apps/JavaSbt013
sbt clean gen-sonar-prop
