#!/bin/bash
#
# test client access to our service

echo -e "\n"
curl -i -X DELETE http://localhost:8080/discussion/delete/518aad1d1d41c808c6f3ecac
echo -e "\n"
