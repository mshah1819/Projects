#!/bin/bash
#
# test client access to our service

echo -e "\n"
curl -i -X GET http://localhost:8080/discussion/518a23181d41c85d29bc7bbe/message/6666ee891d41c818b6b66a6

#5186ee891d41c818b6b77a4 is discussion_id and 6666ee891d41c818b6b66a6 is message id
echo -e "\n"
