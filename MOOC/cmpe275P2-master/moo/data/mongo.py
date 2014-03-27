"""
Mongo Storage interface
"""
import sys
import traceback
from bson.objectid import ObjectId

from pymongo import Connection


class Storage(object):     
   def __init__(self):
      # initialize our storage, data is a placeholder
      connection = Connection()              
      db = connection['cmpe275']
      self.course = db['course']
      self.quiz = db['quiz']
      self.discussion = db['discussion']
      self.announcement = db['announcement']
      self.categories = db['categories']
      self.message = db['message']
      self.user = db['user']       
  
   # ************************************GENERIC HANDLING************************************************
   #
   # find 
   #
   def find(self,name, collection):
      if collection != "user":
         obj_id = ObjectId(name)  
         query = {"_id": obj_id}     
         print '---> collection.find:', collection , " ==> " , query
      try:      
         if collection == "user":
            query = {"email": name}
            print 'query is ', query
            for c in self.user.find(query):
               return {"email": c["email"],"own": c["own"],"enrolled": c["enrolled"],"quizzes": c["quizzes"]}
         elif collection == "course":
            for c in self.course.find(query):
               return {"id":str(name),"category":c["category"],"title":c["title"],"section":c["section"],"dept":c["dept"],"term":c["term"],"year":c["year"],"instructor":c["instructor"],"days":c["days"],"hours":c["hours"],"description":c["description"],"attachment":c["attachment"],"version":c["version"]}
         elif collection == "category":
            for c in self.categories.find(query):
               return {"id": str(name),"name": c["name"],"description": c["description"],"createDate": c["createDate"],"status": c["status"]}
         elif collection == "quiz":
            for c in self.quiz.find(query):
               return {"id": str(name),"courseId": c["courseId"],"questions": c["questions"]}
         elif collection == "announcement":
            for c in self.announcement.find(query):
               return {"id": str(name),"courseId": c["courseId"],"title": c["title"],"description":c["description"],"postDate": c["postDate"],"status": c["status"]}
         elif collection == "discussion":
            for c in self.discussion.find(query):
               return {"id":str(name), "course_id": c["course_id"],"title": c["title"],"created_by":c["created_by"],"created_at":c["created_at"],"updated_at":c["updated_at"]}
         return None
        
      except:
          traceback.print_exc()
          return 500


   #
   # course enroll 
   #
   def enroll_course(self,email, course_id):
      try:
         self.user.update({"email":email}, {'$push': {'enrolled': course_id}})
         return 200
      except:
         return 500
             
   #
   # course drop  
   #
   def drop_course(self,email, course_id):
      try:
         self.user.update({"email":email}, {'pull': {'enrolled': course_id}})
         return 200
      except:
         return 500              
   #
   # remove 
   #
   def remove(self,name, collection):
      if collection != "user":
         obj_id = ObjectId(name)  
         query = {"_id": obj_id}     
         print '---> collection.find:', collection , " ==> " , query

      try:
         if collection == "user" :
            query = {"email": name}
            print '---> query', query
            if self.user.find(query).count() == 0:
               res_code = 404
            else:
               self.user.remove(query)
               res_code = 200
         elif collection == "course" :
            if self.course.find(query).count() == 0:
               res_code = 404
            else:
               self.course.remove(query)
               res_code = 200
         elif collection == "category":
            if self.categories.find(query).count() == 0:
               res_code = 404
            else:
               self.categories.remove(query)
               res_code = 200
             
         elif collection == "discussion":
            if self.discussion.find(query).count() == 0:
               res_code = 404
            else:
                if self.message.find({"discussion_id":name}).count() == 0:
                     self.message.remove({"discussion_id":name})
                self.discussion.remove(query)             
                res_code = 200
         elif collection == "quiz":
            if self.quiz.find(query).count() == 0:
               res_code = 404
            else:
               self.quiz.remove(query)
               res_code = 200
         elif collection == "announcement":
            if self.announcement.find(query).count() == 0:
               res_code = 404
            else:
               self.announcement.remove(query)
               res_code = 200
         return res_code
      except:
          traceback.print_exc()
          return 500

  #
   # remove message
   #
   def remove_message(self,discussion_id, message_id):
         obj_id = ObjectId(message_id)  
         query = {"_id": obj_id, "discussion_id":discussion_id}     
         print '---> message del:', query

         try:
            if self.message.find(query).count() == 0:
               res_code = 404
            else:
               self.message.remove(query)
               res_code = 200
            return res_code
         except:
             return 500

   #
   # get discussion message 
   #
   def message_discussion_specific_get(self, discussion_id, message_id):

         obj_id = ObjectId(message_id)  
         query = {"_id": obj_id, "discussion_id":discussion_id}     
         print '---> message discussion specific remove:',  query
         try: 
            for c in self.message.find(query):
               return {"id":str(message_id),"title":c["title"],"title":c["title"],"content":c["content"],"discussion_id":c["discussion_id"],"created_by":c["created_by"],"created_at":c["created_at"],"updated_at":c["updated_at"]}
            return None
        
         except:
             return 500
   #
   # get discussion message 
   #
   def message_update(self, json, discussion_id, message_id):
      print "---> message.update:", json     
      
      try:
                obj_id = ObjectId(message_id)
                query = {"_id" : obj_id, "discussion_id":discussion_id}
                print "message_update.query ==> ", query
                if self.message.find().count(query) == 0:
                    res_code = 404
                else:
                    self.message.update(query, json) 
                    res_code = 200             
                return res_code
      except:
         print "Unexpected error:", sys.exc_info()[0]
         traceback.print_exc()
         return 500
   #  edit 
   #
#    def update(self,json,collection, email):
#       print "---> collection.update:", collection , " ==> " , json      
#       
#       res_code = 200
#       try:
#          if collection == "user" :
#             query = {"email": json["email"]}
#             count = self.user.find(query).count()
#             if count == 0:  
#                res_code = 201
#             else:
#                res_code = 200
#                
#             self.user.save(json)
#          elif collection == "course" :
# #                   
#                owner = self.user.find({"own":{'$in' :[json["id"]]}})
#                print "owner ", owner
#                if owner.count() == 0:
#                   res_code = 400
#                else:
#                   for c in owner:
#                      if c["email"] == email:
#                         obj_id = json["id"]
#                         json["id"] = ObjectId(obj_id)
#                         if self.course.find({"_id" : json["id"]}).count() == 0:
#                            res_code = 404
#                         else:
#                            res_code = 200
#                            self.course.save(json)
#                      else:
#                         res_code = 400
#          elif collection == "discussion":
#             obj_id = json["id"]
#             json["id"] = ObjectId(obj_id)
#             if self.discussion.find({"_id" : json["id"]}).count() == 0:
#                res_code = 404
#             else:
#                self.discussion.save(json)
#             
#          elif collection == "quiz":                   
#                owner = self.user.find({"own":{'$in' :[json["courseId"]]}})
#                if owner.count() == 0:
#                   res_code = 400
#                else:
#                   for c in owner:                        
#                      if c["email"] == email:
#                         obj_id = json["id"]
#                         json["id"] = ObjectId(obj_id)
#                         if self.quiz.find({"_id" : json["id"]}).count() == 0:
#                            res_code = 404
#                         else:    
#                            self.quiz.save(json)
#                      else:
#                         res_code = 400
#          elif collection == "announcement":
#                owner = self.user.find({"own":{'$in' :[json["courseId"]]}})
#                if owner.count() == 0:
#                   res_code = 400
#                else:
#                   for c in owner:                        
#                      if c["email"] == email:
#                         obj_id = json["id"]
#                         json["id"] = ObjectId(obj_id)
#                         if self.announcement.find({"_id" : json["id"]}).count() == 0:
#                            res_code = 404
#                         else:    
#                            self.announcement.save(json)
#                      else:
#                         res_code = 400
#          
#          return res_code
#       except:
#          print "Unexpected error:", sys.exc_info()[0]
#          traceback.print_exc()
#          return 500
   def update(self,json, id,collection):
      print "---> collection.update:", collection , " ==> " , json      
      
      try:
         if collection == "user" :
            query = {"email": json["email"]}
            count = self.user.find(query).count()
            if count == 0:  
                self.user.insert(json)
                res_code = 201
            else:              
               self.user.update({"email": json["email"]}, json)
               res_code = 200       
         elif collection == "course" :
                obj_id = ObjectId(id)
                if self.course.find({"_id" : obj_id}).count() == 0:
                    res_code = 404
                else:
                    self.course.update({"_id": obj_id}, json)                     
                    res_code = 200     
         elif collection == "discussion":
            obj_id = ObjectId(id)
            if self.discussion.find({"_id" : obj_id}).count() == 0:
               res_code = 404
            else:
               self.discussion.update({"_id": obj_id}, json) 
               res_code = 200    
            
         elif collection == "quiz":                   
                 obj_id = ObjectId(id)
                 if self.quiz.find({"_id" : obj_id}).count() == 0:
                    res_code = 404
                 else: 
                    self.quiz.update({"_id": obj_id}, json)  
                    res_code = 200   
         elif collection == "announcement":
              obj_id = ObjectId(id)
              print "id announcement find " , obj_id
              if self.announcement.find({"_id" : obj_id}).count() == 0:
                    res_code = 404
              else:   
                  self.announcement.update({"_id": obj_id}, json)
                  res_code = 200     
                     
         return res_code
      except:
         print "Unexpected error:", sys.exc_info()[0]
         traceback.print_exc()
         return 500
   #
   # add  
   #
   def insert(self,json,collection):
      print "---> collection.insert:", collection , " ==> " , json    
      obj_id = None
      res_code = 201
      try:
         if collection == "user" :
            query = {"email": json["email"]}
            count = self.user.find(query).count()
            if count == 0:
               obj_id = self.user.insert(json)
               obj_id = json["email"]
            else:
               res_code = 409
         if collection == "course" :
            obj_id = self.course.insert(json)
            obj_id = str(obj_id)
         elif collection == "category":
            query = {"name":json["name"]}
            if self.categories.find(query).count() == 0:
               obj_id = self.categories.save(json)
               obj_id = str(obj_id)
            else:
               res_code = 409
            
         elif collection == "discussion":
            obj_id = self.discussion.save(json)
            obj_id = str(obj_id)
            
         elif collection == "quiz":
            obj_id = self.quiz.save(json)
            obj_id = str(obj_id)
         elif collection == "announcement":
            obj_id = self.announcement.save(json)
            obj_id = str(obj_id)
         elif collection == "message":
            obj_id = self.message.save(json)
            obj_id = str(obj_id)

         return {"res_code": res_code,"id" : obj_id}
      except:
         print "Unexpected error:", sys.exc_info()[0]
         traceback.print_exc()
         return {"res_code": 500,"id" : obj_id}

   #
   # list
   #
   def list(self, collection):
      try:
         output = []
         if collection == "category":
            for c in self.categories.find():
               c["id"] = str(c["_id"])
               del c["_id"]
               output.append(c)
         elif collection == "course":
            for c in self.course.find():
               c["id"] = str(c["_id"])
               del c["_id"]
               output.append(c)
         elif collection == "discussion":
            for c in self.discussion.find():
               c["id"] = str(c["_id"])
               del c["_id"]
               output.append(c)
         elif collection == "quiz":
            for c in self.quiz.find():
               c["id"] = str(c["_id"])
               del c["_id"]
               output.append(c)
         elif collection == "announcement":
            for c in self.announcement.find():
               c["id"] = str(c["_id"])
               del c["_id"]
               output.append(c)
                
         return { "success" : True, "list" : output };
      except:
         print "Unexpected error:", sys.exc_info()[0]
         traceback.print_exc()
         return None
     
 #
   # list
   #
   def discussion_messages_get(self, discussion_id):
      try:
         output = []
         query = {"discussion_id" : discussion_id}
         print "discussion messages get ==>  " , query
         for c in self.message.find(query):
               c["id"] = str(c["_id"])
               del c["_id"]
               output.append(c)    
         return { "success" : True, "list" : output };
      except:
         print "Unexpected error:", sys.exc_info()[0]
         traceback.print_exc()
         return 500   
     
      # ************************************GENERIC HANDLING************************************************