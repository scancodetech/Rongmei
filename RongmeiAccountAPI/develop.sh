#!/usr/bin/env bash
ssh -T ubuntu@81.70.102.195 <<'ENDSSH'
      project_dir="/home/ubuntu/project/RongmeiAccountAPI"  #代码被部署的目录
      cd $project_dir #进入代码目录
      git checkout -b develop
      git reset --hard #清理git
      git pull origin develop #拉取仓管最新代码
      ./gradlew build bootDevJar && ps ax | grep -i 'RongmeiAccountAPI-1.0.jar' | grep java | grep -v grep | awk '{print $1}' | xargs kill -9; nohup java -jar build/libs/dev/RongmeiAccountAPI-1.0.jar>rongmeiaccountapi.log &
ENDSSH