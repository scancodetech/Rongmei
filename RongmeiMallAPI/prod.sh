#!/usr/bin/env bash
ssh -T ubuntu@42.192.14.102 <<'ENDSSH'
      project_dir="/home/ubuntu/project/RongmeiMallAPI"  #代码被部署的目录
      cd $project_dir #进入代码目录
      git checkout -b master
      git reset --hard #清理git
      git pull origin master #拉取仓管最新代码
      ./gradlew build bootProdJar && ps ax | grep -i 'RongmeiMallAPI-1.0.jar' | grep java | grep -v grep | awk '{print $1}' | xargs kill -9; nohup java -jar build/libs/prod/RongmeiMallAPI-1.0.jar>RongmeiMallAPI.log &
ENDSSH