#!/bin/bash

case "${TRAVIS_OS_NAME}" in
  osx)
    rm -rf example/node_modules/native-store
  ;;
  linux)
    rm -rf example/node_modules/native-store
    rm -f $HOME/.gradle/caches/modules-2/modules-2.lock
  ;;
esac
