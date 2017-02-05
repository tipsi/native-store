#!/bin/bash

library_name=$(node -p "require('./package.json').name")

case "${TRAVIS_OS_NAME}" in
  osx)
    npm install
    cd example_tmp
    npm install
    react-native unlink $library_name
    react-native link
  ;;
  linux)
    npm install
    npm install react-native
    cd example_tmp
    npm install
    react-native unlink $library_name
    react-native link
  ;;
esac
