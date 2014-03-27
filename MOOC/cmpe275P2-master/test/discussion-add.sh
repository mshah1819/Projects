#!/bin/bash
#
# test client access to our service

echo -e "\n"
curl -i -H "Content-Type: application/json" -X POST --data '{"course_id": "course1","title": "Title","created_by":"sugandhi","created_at":"04-22-2012","updated_at":"04-22-2012"}'  http://localhost:8080/discussions
echo -e "\n"
