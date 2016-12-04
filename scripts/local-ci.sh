#!/bin/bash

set -e

$(dirname "$0")/unit-tests.sh
$(dirname "$0")/e2e-tests.sh
