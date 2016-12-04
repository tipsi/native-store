#!/bin/bash

set -e

$(dirname "$0")/unit-tests.sh
echo $?
$(dirname "$0")/e2e-tests.sh
