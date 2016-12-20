#!/bin/bash

set -e

isOSX() {
  [ "$(uname)" == "Darwin" ]
}

###################
# BEFORE INSTALL  #
###################

# Check is OSX
! isOSX && echo "Current os is not OSX, setup for iOS will be skipped"

###################
# INSTALL         #
###################

# Install dependencies
npm install

###################
# BEFORE BUILD    #
###################

###################
# BUILD           #
###################

###################
# TESTS           #
###################

# Run Android e2e tests
npm run test:android
# Run iOS e2e tests
if isOSX; then
  npm run test:ios
fi
