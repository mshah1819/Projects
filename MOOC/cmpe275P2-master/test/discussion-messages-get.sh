#!/bin/bash
#
# test client access to our service

echo -e "\n"
curl -i -X GET http://localhost:8080/discussion/5189c72e1d41c8255692a807/messages
echo -e "\n"
