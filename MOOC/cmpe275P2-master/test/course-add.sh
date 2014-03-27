#!/bin/bash
#
# test client access to our service

echo -e "\n"
curl -i -H "Content-Type: application/json" -X POST --data '{"category":"cat1","title":"introduction to algebra","section":2,"dept":"eng","term":"Spring","year":2013,"instructor":[{"name":"russel Doe","id":29}],"days":["Monday","Wednesday","Friday"],"hours":["8:00AM","9:15:AM"],"Description":"My course","attachment":"PATH","version":"1"}'  http://localhost:8080/course
echo -e "\n"
