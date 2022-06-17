#!/usr/bin/env bash
ssh -T root@39.102.36.169 <<'ENDSSH'
      project_dir="/root/project/RongmeiMallAPI"  #代码被部署的目录
      cd $project_dir #进入代码目录
      git checkout -b develop
      git reset --hard #清理git
      git pull origin develop #拉取仓管最新代码
      ./gradlew build bootDevJar && ps ax | grep -i 'RongmeiMallAPI-1.0.jar' | grep java | grep -v grep | awk '{print $1}' | xargs kill -9; nohup java -jar build/libs/dev/RongmeiMallAPI-1.0.jar>RongmeiMallAPI.log &
ENDSSH