#!/bin/bash
#
# test client access to our service

echo -e "\n"
curl -i -X GET http://localhost:8080/user/sugandhi@abc.com
echo -e "\n"		
