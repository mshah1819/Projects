#!/bin/bash
#
# test client access to our service

echo -e "\n"
curl -i -H "Content-Type: application/json" -X POST --data '{"courseId": "courseId","title": "Title","description":"desc","postDate": "04-22-2013","status": 0}'  http://localhost:8080/announcements
echo -e "\n"
