#!/bin/bash

case "${TRAVIS_OS_NAME}" in
  osx)
    npm install
    cd example
    npm install
  ;;
  linux)
    npm install
    cd example
    npm install
  ;;
esac
