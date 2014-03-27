#!/bin/bash
#
# test client access to our service

echo -e "\n"
curl -i -H "Content-Type: application/json" -X PUT --data '{"title":"wat..","content":"wor","discussion_id":"518aa63e1d41c804c04a468","created_by":"ss","created_at":"2013-04-18","updated_at":"2013-04-18"}'    http://localhost:8080/message/update/518aa91c1d41c8074c9d104e/discussion/518aa9101d41c8074c9d104d

#5186ee891d41c818b6b77a4 is message_id and 6666ee891d41c818b6b66a6 is discussion id
echo -e "\n"
