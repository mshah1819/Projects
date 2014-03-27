#!/bin/bash
#
# test client access to our service

echo -e "\n"
curl -i -X GET http://localhost:8080/discussion/5189f3a71d41c830aeded606
echo -e "\n"
