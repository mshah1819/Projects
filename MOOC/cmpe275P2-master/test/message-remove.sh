#!/bin/bash
#
# test client access to our service

echo -e "\n"
curl -i -X DELETE http://localhost:8080/message/518ab2501d41c80bd3c175/discussion/518ab2421d41c80bd3c175fe
#5186ee891d41c818b6b77a4 is message_id and 6666ee891d41c818b6b66a6 is discussion id
echo -e "\n"
