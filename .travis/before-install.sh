#!/bin/bash

case "${TRAVIS_OS_NAME}" in
  osx)
    $HOME/.nvm/nvm.sh
    nvm install 6.8.1
  ;;
  linux)
    $HOME/.nvm/nvm.sh
    nvm install 6.8.1
  ;;
esac
