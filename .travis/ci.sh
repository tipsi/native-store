#!/bin/bash

set -e

case "${TRAVIS_OS_NAME}" in
  osx)
    npm run test:ios
    cd example
    set -o pipefail && npm run build:ios | xcpretty -c -f `xcpretty-travis-formatter`
    npm run test:ios
  ;;
  linux)
    npm run test:android
    cd example
    npm run build:android
    npm run test:android
  ;;
esac
