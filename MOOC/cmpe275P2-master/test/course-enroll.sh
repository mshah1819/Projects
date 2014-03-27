#!/bin/bash
#
# test client access to our service

echo -e "\n"
curl -i -X PUT http://localhost:8080/course/enroll

curl -i -X PUT  http://localhost:8080/course/enroll --data 'email=myemail@gmail.com?courseid=course1'
echo -e "\n"
