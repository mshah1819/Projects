#!/bin/bash
#
# test client access to our service

echo -e "\n"
curl -i -X GET http://localhost:8080/course/list
echo -e "\n"
