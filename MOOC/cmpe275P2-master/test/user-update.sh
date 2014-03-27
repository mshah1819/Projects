#!/bin/bash
#
# test client access to our service

echo -e "\n"
curl -i -H "Content-Type: application/json" -X PUT --data '{"email": "pp@abc.com","own": ["course1","course2"],"enrolled": ["course3","course4"],"quizzes": [{"quiz": "id1","grade": 5,"submitDate": "03-21-2013"},{"quiz": "id1","grade": 5,"submitDate": "04-30-2013"}]}'  http://localhost:8080/user/update/pp@abc.com
echo -e "\n"
