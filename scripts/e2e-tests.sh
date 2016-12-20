#!/bin/bash

set -e

if [[ $@ == *"--skip-new"* ]]; then
  skip_new=true
else
  skip_new=false
fi

if [[ $@ == *"--use-old"* ]]; then
  use_old=true
else
  use_old=false
fi

proj_dir_old=example
proj_dir_new=example_tmp

react_native_version=$(cd $proj_dir_old && npm view react-native version)
library_name=$(node -p "require('./package.json').name")

files_to_copy=(
  package.json
  index.{ios,android}.js
  android/app/build.gradle
  src
  scripts
  tests
)

isOSX() {
  [ "$(uname)" == "Darwin" ]
}

###################
# BEFORE INSTALL  #
###################

# Skip iOS step if current os is not OSX
! isOSX && echo "Current os is not OSX, setup for iOS will be skipped"
# Install react-native-cli if not exist
if ! type react-native > /dev/null; then
  npm install -g react-native-cli
fi

if ($skip_new && ! $use_old); then
  echo "Creating new example project skipped"
  # Go to new test project
  cd $proj_dir_new
elif (! $skip_new && ! $use_old); then
  echo "Creating new example project"
  # Remove old test project and tmp dir if exist
  rm -rf $proj_dir_new tmp
  # Init new test project in tmp directory
  mkdir tmp
  cd tmp
  react-native init $proj_dir_old --version $react_native_version
  # Move new project from tmp dir and remove tmp dir
  cd ..
  mv tmp/$proj_dir_old $proj_dir_new
  rm -rf tmp
  # Copy necessary files from example project
  for i in ${files_to_copy[@]}; do
    if [ -e $proj_dir_old/$i ]; then
      cp -Rp $proj_dir_old/$i $proj_dir_new/$i
    fi
  done
  # Go to new test project
  cd $proj_dir_new
else
  echo "Using example folder for tests"
  # Go to old test project
  cd $proj_dir_old
fi

###################
# INSTALL         #
###################

# Install dependencies
npm install
# Link project
react-native unlink $library_name
react-native link

###################
# BEFORE BUILD    #
###################

# Run appium
appiumPID=$(ps -A | grep -v grep | grep appium | awk '{print $1}')
if [ -z $appiumPID ]; then
  npm run appium > /dev/null 2>&1 &
else
  echo "appium is already running, restart appium"
  kill -9 $appiumPID
  npm run appium > /dev/null 2>&1 &
fi

###################
# BUILD           #
###################

# Configure Stripe variables
npm run configure
# Build Android app
npm run build:android
# Build iOS app
isOSX && npm run build:ios

###################
# TESTS           #
###################

# Run Android e2e tests
npm run test:android
# Run iOS e2e tests
if isOSX; then
  npm run test:ios
fi
