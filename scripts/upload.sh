#!/bin/sh

curl --location --request POST 'localhost:8443/upload/cams' \
--form 'file=@"/home/chakra/Playground/Investments/investment/upload-dir/complete1.json"'

curl --location --request POST 'localhost:8443/upload/cams' \
--form 'file=@"/home/chakra/Playground/Investments/investment/upload-dir/complete2.json"'

