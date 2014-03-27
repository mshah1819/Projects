#!/bin/bash
#
# test client access to our service

echo -e "\n"
curl -i -X GET http://localhost:8080/course/51887d291d41c849a93f8417
echo -e "\n"
