#!/bin/bash
#
# test client access to our service

echo -e "\n"
curl -i -H "Content-Type: application/json" -X PUT --data '{"courseId": "courseId","title": "Title1234","description":"desc","postDate": "04-22-2013","status": 0}'  http://localhost:8080/announcement/update/518abfa11d41c81204edac1b
echo -e "\n"
