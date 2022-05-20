#!/bin/bash

mvn clean package
cp ./target/worktime-1.0-SNAPSHOT.jar ~/dev/releases/worktime/worktime.jar