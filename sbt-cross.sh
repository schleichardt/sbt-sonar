#!/bin/sh

#calls sbt with tasks for all supported sbt versions

sbt "^^0.11.3 $@" "^^0.12 $@" "^^0.13 $@"