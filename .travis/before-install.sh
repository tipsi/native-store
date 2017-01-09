#!/bin/bash

init_new_example_project() {
  proj_dir_old=example
  proj_dir_new=example_tmp

  react_native_version=$(cat $proj_dir_old/package.json | sed -n 's/"react-native": "\(\^|~\)*\(.*\)",*/\2/p')

  files_to_copy=(
    .appiumhelperrc
    package.json
    index.{ios,android}.js
    ios/Dummy
    ios/example/AppDelegate.m
    ios/example.xcodeproj/project.pbxproj
    android/app/src/main/java/com/example/dummy
    android/app/src/main/java/com/example/MainActivity.java
    android/app/build.gradle
    src
    scripts
    __tests__
  )

  mkdir tmp
  cd tmp
  react-native init $proj_dir_old --version $react_native_version
  rm -rf $proj_dir_old/__tests__
  cd ..
  mv tmp/$proj_dir_old $proj_dir_new

  for i in ${files_to_copy[@]}; do
    if [ -e $proj_dir_old/$i ]; then
      cp -Rp $proj_dir_old/$i $proj_dir_new/$i
    fi
  done
}

case "${TRAVIS_OS_NAME}" in
  osx)
    $HOME/.nvm/nvm.sh
    nvm install 6.8.1
    npm install -g react-native-cli
    init_new_example_project
  ;;
  linux)
    $HOME/.nvm/nvm.sh
    nvm install 6.8.1
    npm install -g react-native-cli
    init_new_example_project
  ;;
esac
