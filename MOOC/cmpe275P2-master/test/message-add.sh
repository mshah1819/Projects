#!/bin/bash
#
# test client access to our service

echo -e "\n"
curl -i -H "Content-Type: application/json" -X POST --data '{"title":"Title1","content":"content2","discussion_id":"5189c72e1d41c8255692a807","created_by":"sugandhi@abc.com","created_at":"2013-04-18","updated_at":"2013-04-18"}'   http://localhost:8080/discussion/add/5186ee891d41c818b6b77a4d/messages

# 5186ee891d41c818b6b77a4d is discussion id
echo -e "\n"
