#!/bin/bash
#
# test client access to our service

echo -e "\n"
curl -i -H "Content-Type: application/json" -X PUT --data '{"course_id": "course1","title": "New","created_by":"sugandhi","created_at":"04-22-2012","updated_at":"04-22-2012"}'  http://localhost:8080/discussion/update/5189f3a71d41c830aeded606
echo -e "\n"
