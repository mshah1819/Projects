#!/bin/bash
#
# test client access to our service

echo -e "\n"
curl -i -H "Content-Type: application/json" -X POST --data '{"status": 0, "createDate": "01-01-2013", "name": "XMW", "description": "desc"}'  http://localhost:8080/category

echo -e "\n"
