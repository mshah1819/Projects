"""
6, Apr 2013

Example domain logic for the RESTful web service example.

This class provides basic configuration, storage, and logging.
"""

import os
import socket
import StringIO
import json

# moo 
from data.mongo import Storage

#
# Room (virtual classroom -> Domain) functionality - note this is separated 
# from the RESTful implementation (bottle)
#
class Room(object):
   # very limited content negotiation support - our format choices 
   # for output. This also shows _a way_ of representing enums in python
   json, xml, html, text = range(1,5)
   
   # course implementation
   course = None

   # user implementation
   user = None
   
   # discussion implementation
   discussion = None
   
   # category implementation
   category = None
   
   #
   # setup the configuration for our service
   #
   def __init__(self,base,conf_fn):
      self.host = socket.gethostname()
      self.base = base
      self.conf = {}
      
      # should emit a failure (file not found) message
      if os.path.exists(conf_fn):
         with open(conf_fn) as cf:
            for line in cf:
               name, var = line.partition("=")[::2] #returns 1st and 3rd param of list.
               self.conf[name.strip()] = var.strip()
      else:
         raise Exception("configuration file not found.")

      # create storage
      self.__store = Storage()

   #
   # example: find data
   #
   def find(self,name):
      print '---> classroom.find:',name
      return self.__store.find(name)
   #
   # add collection
   #
   def insert(self, json_obj, collection):
      return self.__store.insert(json_obj, collection)

   #
   # update collection
   #
   def update(self, json_obj, id, collection):
      return self.__store.update(json_obj, id,collection)

   #
   # update message
   #
   def message_update(self, json_obj, discussion_id, message_id):
      return self.__store.message_update(json_obj, discussion_id, message_id)

   #
   # discussion messages get
   #
   def discussion_messages_get(self, discussion_id):
      return self.__store.discussion_messages_get(discussion_id, )

   #
   # enroll course
   #
   def enroll_course(self, email, course_id):
      return self.__store.enroll_course(email, course_id)
  
   #
   # enroll course
   #
   def drop_course(self, email, course_id):
      return self.__store.drop_course(email, course_id)
    
   #
   # get collection
   #
   def get(self, reqid, collection):
      return self.__store.find(reqid, collection);
   
   #
   # delete collection
   #
   def remove(self, reqid, collection):
      return self.__store.remove(reqid, collection);
 
   #
   # delete message
   #
   def remove_message(self, discussion_id, message_id):
      return self.__store.remove_message(discussion_id, message_id);

   #
   # get message specific to discussion
   #
   def message_discussion_specific_get(self, discussion_id, message_id):
      return self.__store.message_discussion_specific_get(discussion_id, message_id);
   
   #
   # list collection
   #
   def list(self, collection):
      return self.__store.list(collection);

   #
   # dump the configuration in the requested format. Note placing format logic
   # in the functional code is not really a good idea. However, it is here to
   # provide an example.
   #
   #
   def dump_conf(self,format_req):
      if format_req == Room.json:
         return self.__conf_as_json()
      elif format_req == Room.html:
         return self.__conf_as_html()
      elif format_req == Room.xml:
         return self.__conf_as_xml()
      elif format_req == Room.text:
         return self.__conf_as_text()
      else:
         return self.__conf_as_text()

   #
   # output as xml is supported through other packages. If
   # you want to add xml support look at gnosis or lxml.
   #
   def __conf_as_xml(self):
      return "xml is hard"

   #
   #
   #
   def __conf_as_json(self):
      try:
         all_entry = {}
         all_entry["base.dir"] = self.base
         all_entry["conf"] = self.conf
         return json.dumps(all)
      except:
         return "error: unable to return configuration"

   #
   #
   #
   def __conf_as_text(self):
      try:
         sb = StringIO.StringIO()
         sb.write("Room Configuration\n")
         sb.write("base directory = ")
         sb.write(self.base)
         sb.write("\n\n")
         sb.write("configuration:\n")
        
         for key in sorted(self.conf.iterkeys()):
            print >>sb, "%s=%s" % (key, self.conf[key])
        
         return sb.getvalue()
      finally:
         sb.close()

#
      return "text"

   #
   #
   #
   def __conf_as_html(self):
      try:
         sb = StringIO.StringIO()
         sb.write("<html><body>")
         sb.write("<h1>")
         sb.write("Room Configuration")
         sb.write("</h1>")
         sb.write("<h2>Base Directory</h2>\n")
         sb.write(self.base)
         sb.write("\n\n")
         sb.write("<h2>Configuration</h2>\n")
        
         sb.write("<pre>")
         for key in sorted(self.conf.iterkeys()):
            print >>sb, "%s=%s" % (key, self.conf[key])
         sb.write("</pre>")
     
         sb.write("</body></html>")

         return sb.getvalue()
      finally:
         sb.close()
