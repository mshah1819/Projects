#!/bin/bash
#
# test client access to our service

echo -e "\n"
curl -i -X DELETE http://localhost:8080/category/annonymous_1
echo -e "\n"