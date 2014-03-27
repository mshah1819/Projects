#!/bin/bash
#
# test client access to our service

echo -e "\n"
curl -i -H "Content-Type: application/json" -X PUT --data '{"courseId":"518602951d41c84259ab414d","questions": [{"question": "Que1","options": ["option1","option2"],"answer": "option2","point": 1},{"question": "Que2","options": ["option1","option2"],"answer":"option1","point": 1}]}'  http://localhost:8080/quiz/update/5189426b421aa98ce0fedf53
echo -e "\n"
