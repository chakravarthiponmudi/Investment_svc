#!/bin/sh

curl --location --request POST 'localhost:8443/upload/cams' \
--form 'file=@"/home/chakra/Playground/Investments/investment/upload-dir/20-21_cams.json"'

curl --location --request POST 'localhost:8443/upload/cams' \
--form 'file=@"/home/chakra/Playground/Investments/investment/upload-dir/21-22_cams.json"'

curl --location --request POST 'localhost:8443/upload/cams' \
--form 'file=@"/home/chakra/Playground/Investments/investment/upload-dir/22-23_1_cams.json"'

curl --location --request POST 'localhost:8443/upload/cams' \
--form 'file=@"/home/chakra/Playground/Investments/investment/upload-dir/22-23-2-cams.json"'