#!/bin/bash

isIOS() {
  [ "$(uname)" == "Darwin" ]
}

###################
# BEFORE INSTALL  #
###################

# Check is OSX
! isIOS && echo "Current os is not OSX, setup for iOS will be skipped"

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
# npm run test:android || true
# Run iOS e2e tests
(isIOS && (npm run test:ios || true)) || true
