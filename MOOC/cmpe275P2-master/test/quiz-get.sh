#!/bin/bash
#
# test client access to our service

echo -e "\n"
curl -i -X GET http://localhost:8080/quiz/5185b9131d41c83d4672e6e3
echo -e "\n"
