"""
6, Apr 2013

Example bottle (python) RESTful web service.

This example provides a basic setup of a RESTful service

Notes
1. example should perform better content negotiation. A solution is
   to use minerender (https://github.com/martinblech/mimerender)
"""

# bottle framework
from bottle import request, response, route

# moo
from classroom import Room
# virtual classroom implementation
room = None

def setup(base,conf_fn):
   print '\n**** service initialization ****\n'
   global room 
   room = Room(base,conf_fn)

#
# setup the configuration for our service
@route('/')
def root():
   print "--> root"
   return 'welcome'



# ************************************CATEGORY ************************************************
#
# add category
#
# sample request
# /category/add
#
# {"_id":"zzz", "name":"My Category"}
#
@route('/category', method='POST')
def category_add():
   output = None
   result = room.insert(request.json, "category")
   
   print "--> res ", result["res_code"]
   if result["res_code"] == 201:
      output = {"success" : True, "id": result["id"]}
   else:
      output = {"success" : False}
      
   response.status = result["res_code"]    
   response.add_header("Content-Type", "application/json")   
   return output

# sample request
# /category/ZZZ
@route('/category/:catId', method='GET')
def category_get(catId):
   resCode = 200

   output = room.get(catId, "category")
   if output == None :
      resCode = 400
      output = {"success" : False}
   elif output == 500:
      resCode = 500
   else:
      resCode = 200
    
   response.status = resCode    
   
   response.add_header("Content-Type", "application/json")
   return output 

# sample request
# /category/ZZZ
@route('/category/:catId', method='DELETE')
def category_delete(catId):
   output = None
    
   res_code =  room.remove(catId, "category")
   if res_code == 200:
      output = {"success" : True}
   else:
      output = {"success" : False}
   
   response.status = res_code    
   response.add_header("Content-Type", "application/json")
   return output    

# sample request
# /category/view
@route('/category/list', method='GET')
def category_list():
   resCode = 200

   output = room.list("category") 
   if output == None :
      resCode = 500
      output = {"success" : False}
   else:
      resCode = 200
         
   response.status = resCode    
   response.add_header("Content-Type", "application/json")
   return output 

# ************************************CATEGORY ************************************************


# ************************************DISCUSSION ************************************************
#
# add discussion
#
# sample request
# /discussion
#
@route('/discussions', method='POST')
def discussion_add():
   output = None
   result = room.insert(request.json, "discussion")
   
   if result["res_code"] == 201:
      output = {"success" : True, "id": result["id"]}
   else:
      output = {"success" : False}
      
   response.status = result["res_code"]   
   response.add_header("Content-Type", "application/json")   
   return output

@route('/discussion/update/:dis_id', method='PUT')
def discussion_edit(dis_id):
   output = None
   res_code = room.update(request.json, dis_id, "discussion")
   
   if res_code == 200:
      output = {"success" : True}
   else:
      output = {"success" : False}
           
   response.status = res_code
   response.add_header("Content-Type", "application/json")
   return output  


@route('/discussion/:req_id', method='GET')
def discussion_get(req_id):
   resCode = 200
   output = room.get(req_id, "discussion")
   if output is None:
      resCode = 400
      output = {"success" : False}
   elif output == 500:
      resCode = 500
   else:
      resCode = 200
    
   response.status = resCode    
   
   response.add_header("Content-Type", "application/json")
   return output 

@route('/discussion/:discussion_id/messages', method='GET')
def discussion_messages_get(discussion_id):
   resCode = 200
   output = room.discussion_messages_get(discussion_id)
   if output is None:
      resCode = 400
      output = {"success" : False}
   elif output == 500:
      resCode = 500
   else:
      resCode = 200
    
   response.status = resCode    
   
   response.add_header("Content-Type", "application/json")
   return output 

# sample request
# /discussion/ZZZ
@route('/discussion/delete/:reqid', method='DELETE')
def discussion_delete(reqid):
   output = None
   res_code =  room.remove(reqid, "discussion")
   if res_code == 200:
      output = {"success" : True}
   else:
      output = {"success" : False}
   
   response.status = res_code 
   response.add_header("Content-Type", "application/json")
   return output    

# sample request
# /discussion/list
@route('/discussion/list', method='GET')
def discussion_list():
   resCode = 200

   output = room.list("discussion") 
   if output == None :
      resCode = 500
      output = {"success" : False}
   else:
      resCode = 200
         
   response.status = resCode    
   response.add_header("Content-Type", "application/json")
   return output 

# ************************************DISCUSSION************************************************

# ************************************MESSAGE************************************************
#
# add message
#
# sample request
# /discussion/add/:discussion_id/messages
#
@route('/discussion/add/:discussion_id/messages', method='POST')
def message_add(discussion_id):
   output = None

   result = room.insert(request.json, "message")
   
   if result["res_code"] == 201:
      output = {"success" : True, "id": result["id"]}
   else:
      output = {"success" : False}
      
   response.status = result["res_code"]    
   response.add_header("Content-Type", "application/json")   
   return output

# sample request
# /discussion/:discussion_id/message/:message_id
@route('/message/:message_id/discussion/:discussion_id', method='DELETE')
def message_delete(discussion_id,message_id):
   output = None
   print "mess ", message_id , " dsc", discussion_id
   res_code =  room.remove_message(discussion_id, message_id)
   if res_code == 200:
      output = {"success" : True}
   else:
      output = {"success" : False}
   
   response.status = res_code  
   response.add_header("Content-Type", "application/json")
   return output    

# sample request
# /message/update/:message_id/discussion/:discussion_id
#
@route('/message/update/:message_id/discussion/:discussion_id', method='PUT')
def message_edit(discussion_id, message_id):

   output = None
 
   print "json for messge update  ===> " , request.json
   res_code = room.message_update(request.json, discussion_id, message_id)
   
   if res_code == 200:
      output = {"success" : True}
   else:
      output = {"success" : False}
           
   response.status = res_code
   response.add_header("Content-Type", "application/json")
   return output  

# sample request
# /user/ZZZ
@route('/discussion/:discussion_id/message/:message_id', method='GET')
def message_discussion_specific_get(discussion_id, message_id):
   resCode = 200
   output = room.message_discussion_specific_get(discussion_id, message_id)
   if output == None :
      resCode = 400
      output = {"success" : False}
   elif output == 500:
      resCode = 500
   else:
      resCode = 200
     
   response.status = resCode    
     
   response.add_header("Content-Type", "application/json")
   return output 

# ************************************MESSAGE ************************************************

# ************************************USER ************************************************
#
# add user
#
# sample request
# /user
#
@route('/user', method='POST')
def user_add():
   output = None
   result = room.insert(request.json, "user")
   
   if result["res_code"] == 201:
      output = {"success" : True, "id":result["id"]}
   else:
      output = {"success" : False}
      
   response.status = result["res_code"]    
   response.add_header("Content-Type", "application/json")   
   return output

# sample request
# /user
#
@route('/user/update/:email', method='PUT')
def user_edit(email):
   output = None
   res_code = room.update(request.json, email, "user")
   if res_code == 200 or res_code == 201:
         output = {"success" : True}
   else:
         output = {"success" : False}
         
   response.status = res_code
   
   response.add_header("Content-Type", "application/json")
   return output  

@route('/user/:req_id', method='GET')
def user_get(req_id):
   resCode = 200
   output = room.get(req_id, "user")
   if output == None :
      resCode = 400
      output = {"success" : False}
   elif output == 500:
      resCode = 500
   else:
      resCode = 200
    
   response.status = resCode    
    
   response.add_header("Content-Type", "application/json")
   return output 

# sample request
# /user/ZZZ
@route('/user/:req_id', method='DELETE')
def user_delete(req_id):
   output = None
   res_code =  room.remove(req_id, "user")
   if res_code == 200:
      output = {"success" : True}
   else:
      output = {"success" : False}
   
   response.status = res_code 
   response.add_header("Content-Type", "application/json")
   return output    


# ************************************USER************************************************


# ************************************COURSE************************************************
#
# add course
#
# sample request
# /course
@route('/course', method='POST')
def course_add():
   output = None

   result = room.insert(request.json, "course")
   
   if result["res_code"] == 201:
      output = {"success" : True, "id": result["id"]}
   else:
      output = {"success" : False}
      
   response.status = result["res_code"]    
   response.add_header("Content-Type", "application/json")   
   return output

# sample request
# /course/enroll
#
@route('/course/enroll', method='PUT')
def course_enroll():
   output = None
   email = str(request.query.get("email"))
   course_id = str(request.query.get("courseId"))
   
   res_code = room.enroll_course(email, course_id)
   
   if res_code == 200:
      output = {"success" : True}
   else:
      output = {"success" : False}

   response.status = res_code    
   response.add_header("Content-Type", "application/json")
   return output  

# sample request
# /course/drop
#
@route('/course/drop', method='PUT')
def course_drop():
   output = None
   email = str(request.query.get("email"))
   course_id = str(request.query.get("courseId"))
   
   res_code = room.drop_course(email, course_id)
   
   if res_code == 200:
      output = {"success" : True}
   else:
      output = {"success" : False}

   response.status = res_code    
   response.add_header("Content-Type", "application/json")
   return output  

# sample request
# /course
@route('/course/update/:course_id', method='PUT')
def course_edit(course_id):
   output = None
   res_code = room.update(request.json,course_id, "course")
   
   if res_code == 200:
      output = {"success" : True}
   else:
      output = {"success" : False}
           
   response.status = res_code
   response.add_header("Content-Type", "application/json")
   return output  

# sample request
# /course/ZZZ
@route('/course/:course_id', method='GET')
def course_get(course_id):
   resCode = 200

   output = room.get(course_id, "course")
   if output == None :
      resCode = 400
      output = {"success" : False}
   elif output == 500:
      resCode = 500
   else:
      resCode = 200
    
   response.status = resCode    
   response.add_header("Content-Type", "application/json")
   return output 

# sample request
# /course/ZZZ
@route('/course/:course_id', method='DELETE')
def course_delete(course_id):

   output = None
   res_code =  room.remove(course_id, "course")
   if res_code == 200:
      output = {"success" : True}
   else:
      output = {"success" : False}
   
   response.status = res_code    
   response.add_header("Content-Type", "application/json")
   return output    

# sample request
# /course/list
@route('/course/list', method='GET')
def course_list():
   resCode = 200

   output = room.list("course") 
   if output == None :
      resCode = 500
      output = {"success" : False}
   else:
      resCode = 200
      print "output " , output
#       for c in output:
#          # c["id"] = str(c["_id"])
#           del c["_id"]
#     
         
   response.status = resCode    
   response.add_header("Content-Type", "application/json")
   return output 

# ************************************COURSE************************************************

# ************************************QUIZ************************************************
#
# add quiz
#
# sample request
# /quiz
#
@route('/quizzes', method='POST')
def quiz_add():
   output = None

   result = room.insert(request.json, "quiz")
   
   if result["res_code"] == 201:
      output = {"success" : True, "id": result["id"]}
   else:
      output = {"success" : False}
      
   response.status = result["res_code"]    
   response.add_header("Content-Type", "application/json")   
   return output

# sample request
# /quiz
#
@route('/quiz/update/:quiz_id', method='PUT')
def quiz_edit(quiz_id):
 
   res_code = room.update(request.json,quiz_id, "quiz")
   
   if res_code == 200:
      output = {"success" : True}
   else:
      output = {"success" : False}
           
   response.status = res_code
   response.add_header("Content-Type", "application/json")
   return output  

# sample request
# /quiz/ZZZ
@route('/quiz/:reqid', method='GET')
def quiz_get(reqid):
   output = room.get(reqid, "quiz")
   if output == None :
      resCode = 400
      output = {"success" : False}
   elif output == 500:
      resCode = 500
   else:
      resCode = 200
    
   response.status = resCode    
   response.add_header("Content-Type", "application/json")
   return output 

# sample request
# /quiz/ZZZ
@route('/quiz/:reqid', method='DELETE')
def quiz_delete(reqid):
   output = None
   res_code =  room.remove(reqid, "quiz")
   if res_code == 200:
      output = {"success" : True}
   else:
      output = {"success" : False}
   
   response.status = res_code  
   response.add_header("Content-Type", "application/json")
   return output    

# sample request
# /quiz/list
@route('/quiz/list', method='GET')
def quiz_list():
   resCode = 200
   output = room.list("quiz") 
   if output == None :
      resCode = 500
      output = {"success" : False}
   else:
      resCode = 200
         
   response.status = resCode    
   response.add_header("Content-Type", "application/json")
   return output 

# ************************************QUIZ ************************************************

# ************************************ANNOUNCEMENT ************************************************
#
# add announcement
#
# sample request
# /announcement
#
@route('/announcement', method='POST')
def announcement_add():
   output = None

   result = room.insert(request.json, "announcement")
   
   if result["res_code"] == 201:
      output = {"success" : True, "id": result["id"]}
   else:
      output = {"success" : False}
      
   response.status = result["res_code"]    
   response.add_header("Content-Type", "application/json")   
   return output

# sample request
# /announcement
#
@route('/announcement/update/:anc_id', method='PUT')
def announcement_edit(anc_id):
   output = None
    
   res_code = room.update(request.json,anc_id, "announcement")
   
   if res_code == 200:
      output = {"success" : True}
   else:
      output = {"success" : False}
           
   response.status = res_code
   response.add_header("Content-Type", "application/json")
   return output  

# sample request
# /announcement/ZZZ
@route('/announcement/:reqid', method='GET')
def announcement_get(reqid):
   resCode = 200
   output = room.get(reqid, "announcement")
   if output == None :
      resCode = 400
      output = {"success" : False}
   elif output == 500:
      resCode = 500
   else:
      resCode = 200
    
   response.status = resCode    
      
   response.add_header("Content-Type", "application/json")
   return output 

# sample request
# /announcement/ZZZ
@route('/announcement/:reqid', method='DELETE')
def announcement_delete(reqid):
   output = None
    
   res_code =  room.remove(reqid, "announcement")
   if res_code == 200:
      output = {"success" : True}
   else:
      output = {"success" : False}
   
   response.status = res_code 
   response.add_header("Content-Type", "application/json")
   return output    

# sample request
# /announcement/list
@route('/announcement/list', method='GET')
def announcement_list():
   resCode = 200

   output = room.list("announcement") 
   if output == None :
      resCode = 500
      output = {"success" : False}
   else:
      resCode = 200
         
   response.status = resCode    
   response.add_header("Content-Type", "application/json")
   return output 

# ************************************ANNOUNCEMENT ************************************************


# Determine the format to return data (does not support images)
#
# TODO method for Accept-Charset, Accept-Language, Accept-Encoding, 
# Accept-Datetime, etc should also exist
#
def __format(request):
   #for key in sorted(request.headers.iterkeys()):
   #   print "%s=%s" % (key, request.headers[key])

   types = request.headers.get("Accept",'')
   subtypes = types.split(",")
   for st in subtypes:
      sst = st.split('')
      if sst[0] == "application/json":
         return Room.json
      elif sst[0] == "*/*":
         return Room.json

      # TODO
      # xml: application/xhtml+xml, application/xml
      # image types: image/jpeg, etc

   # default
   return Room.json

#
# The content type on the reply
#
def __response_format(reqfmt):
      if reqfmt == Room.json:
         return "application/json"
      else:
         return "*/*"
         
      # TODO
      # xml: application/xhtml+xml, application/xml
      # image types: image/jpeg, etc