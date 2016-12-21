#!/bin/bash

set -e

isMacOS() {
  [ "$(uname)" == "Darwin" ]
}

###################
# BEFORE INSTALL  #
###################

# Check is macOS
! isMacOS && echo "Current os is not macOS, setup for iOS will be skipped"

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
if isMacOS; then
  npm run test:ios
fi
