#!/usr/bin/env bash
git checkout -b master
git reset --hard #清理git
git pull origin master #拉取仓管最新代码
./gradlew build bootJar && ps ax | grep -i 'RongmeiMetrics-1.0.jar' | grep java | grep -v grep | awk '{print $1}' | xargs kill -9; nohup java -jar build/libs/RongmeiMetrics-1.0.jar>RongmeiMetrics.log &