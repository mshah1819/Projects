#!/bin/bash
#
# test client access to our service

echo -e "\n"
curl -i -X GET http://localhost:8080/category/5186ede01d41c818b6b77a4b
echo -e "\n"
