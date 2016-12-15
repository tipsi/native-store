#!/bin/bash

case "${TRAVIS_OS_NAME}" in
  osx)
    npm install
    cd example
    npm install
  ;;
  linux)
    rm -rf example/node_modules
    npm install
    cd example
    npm install
  ;;
esac
