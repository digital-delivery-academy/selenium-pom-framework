#!/usr/bin/env sh

curl https://www.gov.uk/bank-holidays.json --output src/main/resources/bank-holidays.json
ls src/main/resources