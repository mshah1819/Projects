#!/bin/bash
#
# test client access to our service

echo -e "\n"
curl -i -H "Content-Type: application/json" -X PUT --data '{"category":"cat1","title":"machine learning","section":2,"dept":"eng","term":"Spring","year":2013,"instructor":[{"name":"russel Doe","id":29}],"days":["Monday","Wednesday","Friday"],"hours":["8:00AM","9:15:AM"],"Description":"My course","attachment":"PATH","version":"1"}'  http://localhost:8080/course/51887d291d41c849a93f8417
echo -e "\n"
