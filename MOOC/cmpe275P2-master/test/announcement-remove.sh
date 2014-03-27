#!/bin/bash
#
# test client access to our service

echo -e "\n"
curl -i -X DELETE http://localhost:8080/announcement/5186ee891d41c818b6b77a4d
echo -e "\n"
