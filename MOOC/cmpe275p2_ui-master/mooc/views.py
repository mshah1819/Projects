# Create your views here.

from django.shortcuts import render_to_response
#from django.template import RequestContext
from django.contrib.auth import authenticate, login, logout
from django.contrib.auth.models import User

from mooc.models import MOOC
from time import localtime, strftime
from cStringIO import StringIO

import json
import requests

#modules
course = "course"
category = "category"
announcement = "announcement"
discussion = "discussion"
message = "message"
quiz = "quiz"

#global variables
headers = {'content-type': 'application/json', 'charset': 'utf-8'}

latest_mooc_list = None
default_mooc = None

#dictionary to store user selected options
#example 
#dict = { "user1": { "url":"http://localhost:8080", "mooc":{"id":"id1", "name:"myMooc"}, "course": {"id": "id1", "name": "myCourse"}, 
#                    "category": {"id": "id1", "name": "myCategory"}, "discussion": {"id": "id1", "name": "mydiscussion"}, 
#                    "announcement": {"id": "id1", "name": "myAnnouncement"}, "quiz": {"id": "id1","name": "myQuiz"}
#                  },
#         "user2": { "url":"http://localhost:8080", "mooc":{"id":"id1", "name:"myMooc"}, "course": {"id": "id1", "name": "myCourse"}, 
#                    "category": {"id": "id1", "name": "myCategory"}, "discussion": {"id": "id1", "name": "mydiscussion"}, 
#                    "announcement": {"id": "id1", "name": "myAnnouncement"}, "quiz": {"id": "id1","name": "myQuiz"}
#                  }
#       }
#to access:
#    data = dict["user1"]
#    print data["category"]["id"]
#    OR
#    print dict["user1"]["category"]["id"]
user_dict = {}

# store mooc
def store_mooc():
    global latest_mooc_list, default_mooc, headers, user_dict
   
    latest_mooc_list = MOOC.objects.all()
    for mooc in latest_mooc_list :
        if mooc.is_default :
            print 'mooc Primary URL ===>', mooc.group
            default_mooc = mooc
            break    
    print 'default_mooc Primary URL ===>', default_mooc.group

# index function
def index(request):
    store_mooc()
    return render_to_response("login.html")

# Sign-Up function
def signup(request):
    return render_to_response("signup.html")

# add user
def add_user(request):
    global latest_mooc_list, default_mooc, headers, user_dict
   
    email = request.POST.get('email')
    password = request.POST.get('password')
    firstname = request.POST.get('firstname')
    lastname = request.POST.get('lastname')

    try: 
        local_user = User.objects.create_user(email, email, password)
        local_user.first_name = firstname
        local_user.last_name = lastname
        local_user.save()
    except: 
        ctx = {"message" : "User ID Exists. Please Try Again"}
        return render_to_response("signup.html", ctx)
     
    payload = {"email":email, "own":[], "enrolled":[], "quizzes":[]} 
    tempUrl = default_mooc.primary_URL + "/user"
    print 'add_user.URL ===>', tempUrl
    response = requests.post(tempUrl, data=json.dumps(payload), headers=headers)
    if response.status_code == 200 or response.status_code == 201:
        data = response.json()
        print 'add_user.data ===>', data
        ctx = {"message" : "User successfully registered, please login to continue."}
        return render_to_response("login.html",ctx)
    
    ctx = {"message" : "Sign Up Failed. Please Try Again"}
    return render_to_response("signup.html", ctx)

def get_context(request, msg=None):
    global latest_mooc_list, default_mooc, headers, user_dict
    ctx = {"fName": request.user.first_name, "lName": request.user.last_name, "latest_mooc_list": latest_mooc_list, "selectedMooc": user_dict[request.user.username]["mooc"]["name"]}
    if(msg is not None):
        ctx["message"] = msg
    if(user_dict.has_key(request.user.username)):        
        temp_dict = user_dict[request.user.username]
        if(temp_dict.has_key("course")):
            ctx["course_id"] = user_dict[request.user.username]["course"]["id"]
            ctx["course_name"] = user_dict[request.user.username]["course"]["name"]
        if(temp_dict.has_key("category")):        
            ctx["category_id"] = user_dict[request.user.username]["category"]["id"]
            ctx["category_name"] = user_dict[request.user.username]["category"]["name"]
        if(temp_dict.has_key("discussion")):        
            ctx["discussion_id"] = user_dict[request.user.username]["discussion"]["id"]
            ctx["discussion_name"] = user_dict[request.user.username]["discussion"]["name"]
        if(temp_dict.has_key("announcement")):        
            ctx["announcement_id"] = user_dict[request.user.username]["announcement"]["id"]
            ctx["announcement_name"] = user_dict[request.user.username]["announcement"]["name"]
        if(temp_dict.has_key("quiz")):        
            ctx["quiz_id"] = user_dict[request.user.username]["quiz"]["id"]
            ctx["quiz_name"] = user_dict[request.user.username]["quiz"]["name"]          
    return ctx

# User login related stuff
def login_user(request):
    global latest_mooc_list, default_mooc, headers, user_dict
   
    store_mooc()
    email = request.POST['email']
    password = request.POST['password']
   
    user = authenticate(username=email, password=password)
    if user is not None:
        if user.is_active:
            login(request, user)
#            user logged in successfully, now we need to start tracking his selections
#            delect any previous selections of this user if present
            if(user_dict.has_key(user.username)):
                del user_dict[user.username]; # remove entry with key 'Name'
#            create empty entry in dictionary so that we can add user selections later
            user_dict[user.username] = {"url": default_mooc.primary_URL, "mooc":{"id":default_mooc.id, "name":default_mooc.group}}
            ctx = get_context(request)
            return render_to_response("home.html",ctx)
        else: 
            # Return a 'disabled account' error message
            ctx = {"message" :"Login disabled. Please Contact Administrator"}
            return render_to_response("login.html",ctx)
    else:
        ctx = {"message" :"Login Failed. Please try Again"}
        return render_to_response("login.html",ctx)
# home page
def home(request):
    global latest_mooc_list, default_mooc, headers, user_dict
   
    mooc_id = request.GET.get('id')
    if mooc_id is not None :
        selected_mooc = MOOC.objects.get(pk=mooc_id)
        user_dict[request.user.username]["url"] = selected_mooc.primary_URL
        user_dict[request.user.username]["mooc"] = {"id":selected_mooc.id, "name":selected_mooc.group}

    print 'Users Primary URL ===>', user_dict[request.user.username]["url"]
    ctx = get_context(request)
    return render_to_response('home.html',ctx)

# user profile - We should show list of course user owns as well as enrolled here. It should be complete userprofile page
def profile(request):
    ctx = get_context(request)
    fetch_user(request, ctx)
    return render_to_response("profile.html",ctx)

def update_user(request):
    global latest_mooc_list, default_mooc, headers, user_dict
    
    password = request.POST['password']
    firstname = request.POST.get('firstname')
    lastname = request.POST.get('lastname')
   
    if password is not None:
        request.user.set_password(password)
    if firstname is not None:
        request.user.first_name = firstname
    if lastname is not None:
        request.user.last_name = lastname
    request.user.save()

    ctx = get_context(request)
    return render_to_response('home.html',ctx)
     
# log out function
def logout_user(request):
    global latest_mooc_list, default_mooc, headers, user_dict
    
    if(user_dict.has_key(request.user.username)):
        del user_dict[request.user.username]; # remove entry with key 'Name'
    
    logout(request)
    return index(request)

# list category
def list_category(request, msg=''):
    global latest_mooc_list, default_mooc, headers, user_dict
    
    ctx = get_context(request, msg)    
    
    file_str = StringIO()
    file_str.write(user_dict[request.user.username]["url"])
    file_str.write('/category/list')
    
    tempUrl = file_str.getvalue()
    print 'list_category.URL ===>', tempUrl
    response = requests.get(tempUrl)
    if response.status_code == 200:
        data = response.json()
        print 'list_category:data ===>', data
        ctx["category_list"] = data["list"]

    return render_to_response("categories.html",ctx)

# show category add / edit page
def category(request):
    global latest_mooc_list, default_mooc, headers, user_dict
    
    ctx = get_context(request)
    ctx["id"] = "-1"
    name = request.GET.get('id')
    if name is not None:
        file_str = StringIO()
        file_str.write(user_dict[request.user.username]["url"])
        file_str.write('/category/')
        file_str.write(name)
        
        tempUrl = file_str.getvalue()
        print 'category.URL ===>', tempUrl
        response = requests.get(tempUrl)
        if response.status_code == 200:
            data = response.json()
            print 'category:data ===>', data
            ctx["id"] = data["id"]
            ctx["name"] = data["name"]
            ctx["desc"] = data["description"]
    
    return render_to_response("category_add.html",ctx)

# add/edit category form submit request
def add_category(request):
    global latest_mooc_list, default_mooc, headers, user_dict
    
    cat_id = request.POST.get('catId')
    name = request.POST.get('catName')
    desc = request.POST.get('catDesc')
   
    if cat_id == "-1" :
        data = {"name":name, "description":desc, "createDate":strftime("%Y-%m-%d %H:%M:%S", localtime()), "status":0}    
    else:
        data = {"_id":cat_id, "name":name, "description":desc, "createDate":strftime("%Y-%m-%d %H:%M:%S", localtime()), "status":0} 
    print 'add_category:data ===>', data

    file_str = StringIO()
    file_str.write(user_dict[request.user.username]["url"])
    file_str.write('/category')
    
    tempUrl = file_str.getvalue()
    print 'add_category.URL ===>', tempUrl
    response = requests.post(tempUrl, json.dumps(data), headers=headers)
    if response.status_code == 200 or response.status_code == 201:
        if cat_id == "-1" :
            data = response.json()
            print 'category:data ===>', data            
            return list_category(request, "Category added!!!")
        else:
            return list_category(request, "Category edited!!!")
    
    ctx = get_context(request, "Failed to add Category")
    ctx["id"] = data["id"]
    ctx["name"] = data["name"]
    ctx["desc"] = data["description"]    
    
    return render_to_response("category_add.html",ctx)
    
def remove_category(request):
    global latest_mooc_list, default_mooc, headers, user_dict
   
    name = request.GET.get('id')
    if name is not None and name != "-1" :
        file_str = StringIO()
        file_str.write(user_dict[request.user.username]["url"])
        file_str.write('/category/')
        file_str.write(name)        
        
        tempUrl = file_str.getvalue()
        print 'remove_category.URL ===>', tempUrl
        response = requests.delete(tempUrl)
        if response.status_code == 200:
            return list_category(request, "Category removed!!!")

    return list_category(request, "Failed to remove category!!")    
    
# build list of courses owned and enrolled for this mooc by logged in user
# this is required so that we can allow only owner to edit/delete course and drop if already enrolled    
def fetch_user(request, ctx):
    global latest_mooc_list, default_mooc, headers, user_dict
    
    file_str = StringIO()
    file_str.write(default_mooc.primary_URL)
    file_str.write('/user/')
    file_str.write(request.user.username)
    
    tempUrl = file_str.getvalue()
    print 'fetch_user.URL ===>', tempUrl
    response = requests.get(tempUrl)
    if response.status_code == 200:
        data = response.json()
        print 'fetch_user:data ===>', data
        
        temp_own = []
        temp_list = data["own"]
        if(temp_list):
            for course_name in temp_list:
                if(course_name.find(user_dict[request.user.username]["mooc"]["name"]) != -1):
                    lst = course_name.split(":")
                    temp_own.append(lst[1])
        ctx["courses_own"] = temp_own

        temp_enrolled = []        
        temp_list = data["enrolled"]
        if(temp_list):
            temp_own = []
            for course_name in temp_list:
                if(course_name.find(user_dict[request.user.username]["mooc"]["name"]) != -1):
                    lst = course_name.split(":")
                    temp_enrolled.append(lst[1])
        ctx["courses_enrolled"] = temp_enrolled

#        TODO - Srinath - Please take care of parsing quiz if need be        
        ctx["quizzes"] = data["quizzes"]
      
        
# fetch course list for selected category and show them     
def list_course(request, msg=''):
    global latest_mooc_list, default_mooc, headers, user_dict

    name = -1
    if request.method == 'GET':
        name = request.GET.get('categoryId')
    else:
        name = request.POST.get('categoryId')
    if name is not None and name != "-1" :
        file_str_cat = StringIO()
        file_str_cat.write(user_dict[request.user.username]["url"])
        file_str_cat.write('/category/')
        file_str_cat.write(name)        
        
        tempUrl = file_str_cat.getvalue()
        print 'list_course.category.URL ===>', tempUrl
        response = requests.get(tempUrl)
        if response.status_code == 200:
            data = response.json()
            print 'list_course.category.Data ===>', data
            user_dict[request.user.username]["category"] = {"id": name, "name":data["name"]}
    else:
        try:
            del user_dict[request.user.username]["category"] # remove entry with key 'Name'
        except:
            print 'list_course.selected_category.delete ===> failed...'        
    
    ctx = get_context(request, msg)
    ctx["course_list"] = []
    
    file_str = StringIO()
    file_str.write(user_dict[request.user.username]["url"])
    file_str.write('/course/list')
    tempUrl = file_str.getvalue()
    print 'list_course.URL ===>', tempUrl
    response = requests.get(tempUrl)
    if response.status_code == 200:
        data = response.json()
        print 'list_course:data ===>', data
        ctx["course_list"] = data["list"]

    ctx["category_list"] = []
    file_str = StringIO()
    file_str.write(user_dict[request.user.username]["url"])
    file_str.write('/category/list')
    tempUrl = file_str.getvalue()
    print 'list_course.list_category.URL ===>', tempUrl
    response = requests.get(tempUrl)
    if response.status_code == 200:
        data = response.json()
        print 'list_course.list_category.data ===>', data
        ctx["category_list"] = data["list"] 
        
#    build list of courses enrolled and owned for this mooc
    fetch_user(request, ctx)
    return render_to_response("courses.html",ctx)

# show course add / edit page
def course(request):
    global latest_mooc_list, default_mooc, headers, user_dict
    
    ctx = get_context(request)
    ctx["id"] = "-1"
    ctx["instructor"] = request.user.username
    name = request.GET.get('id')
    if name is not None:
        file_str = StringIO()
        file_str.write(user_dict[request.user.username]["url"])
        file_str.write('/course/')
        file_str.write(name)
        
        tempUrl = file_str.getvalue()
        print 'course.URL ===>', tempUrl
        response = requests.get(tempUrl)
        if response.status_code == 200:
            data = response.json()
            print 'course:data ===>', data
            ctx["id"] = data["id"]
            ctx["title"] = data["title"]
            ctx["category"] = data["category"]
            ctx["section"] = data["section"]
            ctx["dept"] = data["dept"]
            ctx["term"] = data["term"]
            ctx["days"] = data["days"]
            ctx["description"] = data["description"]
            ctx["attachment"] = data["attachment"]
    
    ctx["category_list"] = []
    file_str = StringIO()
    file_str.write(user_dict[request.user.username]["url"])
    file_str.write('/category/list')
    
    tempUrl = file_str.getvalue()
    print 'course.list_category.URL ===>', tempUrl
    response = requests.get(tempUrl)
    if response.status_code == 200:
        data = response.json()
        print 'list_category:data ===>', data
        ctx["category_list"] = data["list"]    
    
    return render_to_response("course_add.html",ctx)

#
# Course
#
# @login_required(login_url='login') -- > Good to have, need to figure out usage.
def add_course(request):
    global latest_mooc_list, default_mooc, headers, user_dict
    data = {} 
    data["category"] = request.POST.get("category")
    data["title"] = request.POST.get("title")
    data["dept"] = request.POST.get("dept")
    data["section"] = request.POST.get("section")
    data["term"] = request.POST.get("term")
    data["instructor"] = request.user.username
    data["days"] = request.POST.get("days")
    data["description"] = request.POST.get("description")
    data["attachment"] = request.POST.get("attachment")
    data["hours"] = ["8:00AM","9:15:PM"]
    data["version"] = "1"
    data["year"] = "2013"

    print 'add_course:data ===>', data
    
    file_str = StringIO()
    file_str.write(user_dict[request.user.username]["url"])
    file_str.write('/course')
    
    course_id = request.POST.get('courseId')
    if course_id is not None and course_id != "-1" :
        file_str.write('/update/')
        file_str.write(course_id)
    
    tempUrl = file_str.getvalue()
    print 'add_course.URL ===>', tempUrl
    if course_id is not None and course_id != "-1" :
        response = requests.put(tempUrl, json.dumps(data), headers=headers)
    else:
        response = requests.post(tempUrl, json.dumps(data), headers=headers)

    if response.status_code == 200 or response.status_code == 201:
        res_data = response.json()
        print 'add_course:data ===>', res_data
        if course_id == "-1" :
            course_id =  res_data["id"]
            # Now we need to add course to user object
            file_str = StringIO()
            file_str.write(default_mooc.primary_URL)
            file_str.write('/user/')
            file_str.write(request.user.username)
    
            tempUrl = file_str.getvalue()
            print 'add_course.fetch_user.URL ===>', tempUrl
            user_response = requests.get(tempUrl)
            if user_response.status_code == 200:
                user_data = user_response.json()
                print 'add_course.fetch_user:data ===>', user_data
            
                own_course = user_dict[request.user.username]["mooc"]["name"] + ":" + course_id
                user_data["own"].append(own_course)
                
                file_str = StringIO()
                file_str.write(default_mooc.primary_URL)
                file_str.write('/user/update/')
                file_str.write(request.user.username)
                tempUrl = file_str.getvalue()
                user_update_response = requests.put(tempUrl, data=json.dumps(user_data), headers=headers)
                if user_update_response.status_code == 200:
                    user_update_data = user_update_response.json()
                    print 'add_course.update_user:data ===>', user_update_data
                    # TODO - what to do if add to user fails
                return list_course(request, "Course added!!!")
        else:
            return list_course(request, "Course edited!!!")
    
    ctx = get_context(request, "Failed to add Course")
    if (course_id is not None):
        ctx["id"] = course_id
    else:
        ctx["id"] = "-1"    
    ctx["instructor"] = request.user.username
    ctx["title"] = data["title"]
    ctx["category"] = data["category"]
    ctx["dept"] = data["dept"]
    ctx["section"] = data["section"]
    ctx["term"] = data["term"]
    ctx["days"] = data["days"]
    ctx["description"] = data["description"]
    ctx["attachment"] = data["attachment"]    
    
    ctx["category_list"] = []
    file_str = StringIO()
    file_str.write(user_dict[request.user.username]["url"])
    file_str.write('/category/list')
    
    tempUrl = file_str.getvalue()
    print 'course.list_category.URL ===>', tempUrl
    response = requests.get(tempUrl)
    if response.status_code == 200:
        data = response.json()
        print 'list_category:data ===>', data
        ctx["category_list"] = data["list"]   
        
    return render_to_response("course_add.html",ctx)    

def remove_course(request):
    global latest_mooc_list, default_mooc, headers, user_dict
   
    name = request.GET.get('id')
    if name is not None and name != "-1" :
        file_str = StringIO()
        file_str.write(user_dict[request.user.username]["url"])
        file_str.write('/course/')
        file_str.write(name)        
        
        tempUrl = file_str.getvalue()
        print 'remove_course.URL ===>', tempUrl
        response = requests.delete(tempUrl)
        if response.status_code == 200:
            data = response.json()
            print 'remove_course:data ===>', data            
            # Now we need to remove course from user object
            file_str = StringIO()
            file_str.write(default_mooc.primary_URL)
            file_str.write('/user/')
            file_str.write(request.user.username)
    
            tempUrl = file_str.getvalue()
            print 'remove_course.fetch_user.URL ===>', tempUrl
            user_response = requests.get(tempUrl)
            if user_response.status_code == 200:
                user_data = user_response.json()
                print 'remove_course.fetch_user:data ===>', user_data
            
                own_course = user_dict[request.user.username]["mooc"]["name"] + ":" + name
                try:
                    user_data["own"].remove(own_course)
                    
                    file_str = StringIO()
                    file_str.write(default_mooc.primary_URL)
                    file_str.write('/user/update/')
                    file_str.write(request.user.username)
                    tempUrl = file_str.getvalue()
                    user_update_response = requests.put(tempUrl, data=json.dumps(user_data), headers=headers)
                    if user_update_response.status_code == 200:
                        user_update_data = user_update_response.json()
                        print 'remove_course.update_user:data ===>', user_update_data                    
                except:
                    print 'remove_course.remove_from_user ===> failed'

            return list_course(request, "Course removed!!!")
    return list_course(request, "Failed to remove course!!")  

def get_course(request):
    name = request.GET.get('id')
    if name is not None and name != "-1" :
        return view_course(request, name)

def view_course(request, course_id):
    global latest_mooc_list, default_mooc, headers, user_dict
    
    ctx = get_context(request)
    #    build list of courses enrolled and owned for this mooc
    fetch_user(request, ctx)
    
    ctx["instructor"] = request.user.username

    file_str = StringIO()
    file_str.write(user_dict[request.user.username]["url"])
    file_str.write('/course/')
    file_str.write(course_id)
    
    tempUrl = file_str.getvalue()
    print 'get_course.URL ===>', tempUrl
    response = requests.get(tempUrl)
    if response.status_code == 200:
        data = response.json()
        print 'get_course:data ===>', data
        ctx["id"] = data["id"]
        ctx["title"] = data["title"]
        ctx["category"] = data["category"]
        ctx["section"] = data["section"]
        ctx["dept"] = data["dept"]
        ctx["term"] = data["term"]
        ctx["days"] = data["days"]
        ctx["description"] = data["description"]
        ctx["attachment"] = data["attachment"]
        ctx["enrolled"] = False
        
        courses_enrolled = ctx["courses_enrolled"]
        if(courses_enrolled):
            enroll_course = user_dict[request.user.username]["mooc"]["name"] + ":" + ctx["id"]
            for course_name in courses_enrolled:
                if( course_name == enroll_course ):
                    ctx["enrolled"] = True
                    break
    
        file_str = StringIO()
        file_str.write(user_dict[request.user.username]["url"])
        file_str.write('/category/')
        file_str.write(ctx["category"])
        
        tempUrl = file_str.getvalue()
        print 'get_course.get_category.URL ===>', tempUrl
        response = requests.get(tempUrl)
        if response.status_code == 200:
            data = response.json()
            print 'get_course.get_category:data ===>', data
            ctx["category"] = data["name"]    
    
    return render_to_response("course_view.html",ctx)   

# enroll user
def enroll_course(request):
    global latest_mooc_list, default_mooc, headers, user_dict
    
    name = request.GET.get('id')
    if name is not None and name != "-1" :
        # Now we need to enroll course for user object
        file_str = StringIO()
        file_str.write(default_mooc.primary_URL)
        file_str.write('/user/')
        file_str.write(request.user.username)
    
        tempUrl = file_str.getvalue()
        print 'enroll_user.fetch_user.URL ===>', tempUrl
        response = requests.get(tempUrl)
        if response.status_code == 200:
            data = response.json()
            print 'enroll_user.fetch_user:data ===>', data
        
            enroll_course = user_dict[request.user.username]["mooc"]["name"] + ":" + name
            try:
                data["enrolled"].append(enroll_course)
                
                file_str = StringIO()
                file_str.write(default_mooc.primary_URL)
                file_str.write('/user/update/')
                file_str.write(request.user.username)
                
                tempUrl = file_str.getvalue()                
                user_update_response = requests.put(tempUrl, data=json.dumps(data), headers=headers)
                if user_update_response.status_code == 200:
                    user_update_data = user_update_response.json()
                    print 'enroll_user.update_user:data ===>', user_update_data                    
            except:
                print 'enroll_user.append_to_user ===> failed'    
    else:
        print 'enroll_user ===> failed'  
        return list_course(request, "Course not found!!!")
    return view_course(request, name)

# enroll user
def drop_course(request):
    global latest_mooc_list, default_mooc, headers, user_dict
    
    name = request.GET.get('id')
    if name is not None and name != "-1" :
        # Now we need to enroll course for user object
        file_str = StringIO()
        file_str.write(default_mooc.primary_URL)
        file_str.write('/user/')
        file_str.write(request.user.username)
    
        tempUrl = file_str.getvalue()
        print 'drop_course.fetch_user.URL ===>', tempUrl
        response = requests.get(tempUrl)
        if response.status_code == 200:
            data = response.json()
            print 'drop_course.fetch_user:data ===>', data
        
            enroll_course = user_dict[request.user.username]["mooc"]["name"] + ":" + name
            try:
                data["enrolled"].remove(enroll_course)
                
                file_str = StringIO()
                file_str.write(default_mooc.primary_URL)
                file_str.write('/user/update/')
                file_str.write(request.user.username)
                
                tempUrl = file_str.getvalue()                  
                user_update_response = requests.put(tempUrl, data=json.dumps(data), headers=headers)
                if user_update_response.status_code == 200:
                    user_update_data = user_update_response.json()
                    print 'drop_course.update_user:data ===>', user_update_data                    
            except:
                print 'drop_course.remove_from_user ===> failed'    
    else:
        print 'drop_course ===> failed'    
        return list_course(request, "Course not found!!!")
    return view_course(request, name)    
    
#
# announcement
#
# fetch announcement list for selected course and show them   
def list_announcement(request, msg=''):
    global latest_mooc_list, default_mooc, headers, user_dict

    courseId = -1
    if request.method == 'GET':
        courseId = request.GET.get('courseId')
    else:
        courseId = request.POST.get('courseId')
    if courseId is not None and courseId != "-1" :
        file_str_cat = StringIO()
        file_str_cat.write(user_dict[request.user.username]["url"])
        file_str_cat.write('/course/')
        file_str_cat.write(courseId)        
        
        tempUrl = file_str_cat.getvalue()
        print 'list_announcement.get_course.URL ===>', tempUrl
        response = requests.get(tempUrl)
        if response.status_code == 200:
            data = response.json()
            print 'list_announcement.get_course.DATA ===>', data
            user_dict[request.user.username]["course"] = {"id": courseId, "name":data["title"]}
    else :
        try:
            del user_dict[request.user.username]["course"] # remove entry with key 'Name'
        except:
            print 'list_announcement.selected_course.delete ===> failed...'        

    ctx = get_context(request, msg)
    ctx["announcement_list"] = []

    file_str = StringIO()
    file_str.write(user_dict[request.user.username]["url"])
    file_str.write('/announcement/list')
    tempUrl = file_str.getvalue()
    print 'list_announcement.URL ===>', tempUrl
    response = requests.get(tempUrl)
    if response.status_code == 200:
        data = response.json()
        print 'list_announcement.DATA ===>', data
        ctx["announcement_list"] = data["list"]

    ctx["course_list"] = []
    file_str = StringIO()
    file_str.write(user_dict[request.user.username]["url"])
    file_str.write('/course/list')
    tempUrl = file_str.getvalue()
    print 'list_announcement.list_course.URL ===>', tempUrl
    response = requests.get(tempUrl)
    if response.status_code == 200:
        data = response.json()
        print 'list_announcement.list_course.DATA ===>', data
        ctx["course_list"] = data["list"] 
        
    return render_to_response("announcements.html",ctx)

# show announcement add / edit page
#{"id": str(name),"courseId": c["courseId"],"title": c["title"],"description":c["description"],"postDate": c["postDate"],"status": c["status"]}
def announcement(request):
    global latest_mooc_list, default_mooc, headers, user_dict
    
    ctx = get_context(request)
    ctx["id"] = "-1"

    name = request.GET.get('id')
    if name is not None:
        file_str = StringIO()
        file_str.write(user_dict[request.user.username]["url"])
        file_str.write('/announcement/')
        file_str.write(name)
        tempUrl = file_str.getvalue()
        print 'announcement.URL ===>', tempUrl
        response = requests.get(tempUrl)
        if response.status_code == 200:
            data = response.json()
            print 'announcement:data ===>', data
            
            ctx["id"] = data["id"]
            ctx["title"] = data["title"]
            ctx["course"] = data["courseId"]
            ctx["description"] = data["description"]
    
    ctx["course_list"] = []
    file_str = StringIO()
    file_str.write(user_dict[request.user.username]["url"])
    file_str.write('/course/list')
    tempUrl = file_str.getvalue()
    print 'announcement.list_course.URL ===>', tempUrl
    response = requests.get(tempUrl)
    if response.status_code == 200:
        data = response.json()
        print 'announcement.list_course.DATA ===>', data
        ctx["course_list"] = data["list"] 
        
    return render_to_response("announcement_add.html",ctx)

def edit_message(request):
    global latest_mooc_list, default_mooc, headers, user_dict
    ctx = get_context(request)
    message_id = request.GET.get('message_id')
    discussion_id = request.GET.get("id")
    
    print "mess ", message_id, " desc ", discussion_id
    file_str = StringIO()
    file_str.write(user_dict[request.user.username]["url"])
    file_str.write('/discussion/')        
    file_str.write(discussion_id)
    file_str.write('/message/')
    file_str.write(message_id)       
        
    tempUrl = file_str.getvalue()
    print 'edit_message.get_message.URL ===>', tempUrl
    response = requests.get(tempUrl)
    if response.status_code == 200:
            data = response.json()
            print 'get_message:data ===>', data
            ctx = get_context(request, "")
            ctx["id"] = message_id
            ctx["discussion_id"] = discussion_id
            ctx["title"] = data["title"]
            ctx["content"] = data["content"]
            ctx["created_at"] = data["created_at"]
            ctx["created_by"] = data["created_by"]
            ctx["updated_at"] = data["updated_at"]
            fetch_user(request, ctx)        

            return render_to_response("message_edit.html",ctx)
    else:
            return get_discussion(request, "Failed to get message!!")  
#{"id": str(name),"courseId": c["courseId"],"title": c["title"],"description":c["description"],"postDate": c["postDate"],
#  "status": c["status"]}
def add_announcement(request):
    global latest_mooc_list, default_mooc, headers, user_dict
    
    data = {} 
    data["title"] = request.POST.get("title")
    data["courseId"] = request.POST.get("course")
    data["description"] = request.POST.get("description")        
    data["postDate"] = strftime("%Y-%m-%d %H:%M:%S", localtime())
    data["status"] = 0

    print 'add_announcement:data ===>', data
    
    file_str = StringIO()
    file_str.write(user_dict[request.user.username]["url"])
    file_str.write('/announcement')
    
    announcement_Id = request.POST.get('announcementId')
    if announcement_Id is not None and announcement_Id != "-1" :
        file_str.write('/update/')
        file_str.write(announcement_Id)
    
    tempUrl = file_str.getvalue()
    print 'add_announcement.URL ===>', tempUrl
    if announcement_Id is not None and announcement_Id != "-1" :
        response = requests.put(tempUrl, json.dumps(data), headers=headers)
    else:
        response = requests.post(tempUrl, json.dumps(data), headers=headers)

    if response.status_code == 200 or response.status_code == 201:
        res_data = response.json()
        print 'add_announcement:data ===>', res_data
        if announcement_Id is None or announcement_Id == "-1" :
            return list_announcement(request, "Announcement added!!!")
        else:
            return list_announcement(request, "Announcement edited!!!")
    
    ctx = get_context(request, "Failed to add Announcement")
    if (announcement_Id is not None):
        ctx["id"] = announcement_Id
    else:
        ctx["id"] = "-1"
    ctx["title"] = data["title"]
    ctx["courseId"] = data["courseId"]
    ctx["description"] = data["description"]
    
    ctx["course_list"] = []
    file_str = StringIO()
    file_str.write(user_dict[request.user.username]["url"])
    file_str.write('/course/list')
    tempUrl = file_str.getvalue()
    print 'announcement.list_course.URL ===>', tempUrl
    response = requests.get(tempUrl)
    if response.status_code == 200:
        data = response.json()
        print 'announcement.list_course.DATA ===>', data
        ctx["course_list"] = data["list"] 
    
    return render_to_response("announcement_add.html",ctx)     

def remove_announcement(request):
    global latest_mooc_list, default_mooc, headers, user_dict
   
    name = request.GET.get('id')
    if name is not None and name != "-1" :
        file_str = StringIO()
        file_str.write(user_dict[request.user.username]["url"])
        file_str.write('/announcement/')
        file_str.write(name)        
        
        tempUrl = file_str.getvalue()
        print 'remove_announcement.URL ===>', tempUrl
        response = requests.delete(tempUrl)
        if response.status_code == 200:
            data = response.json()
            print 'remove_announcement:data ===>', data            
            return list_announcement(request, "Announcement removed!!!")
    return list_announcement(request, "Failed to remove announcement!!")      

def get_announcement(request):
    global latest_mooc_list, default_mooc, headers, user_dict
    
    ctx = get_context(request)
    name = request.GET.get('id')
    if name is not None and name != "-1" :
        file_str = StringIO()
        file_str.write(user_dict[request.user.username]["url"])
        file_str.write('/announcement/')
        file_str.write(name)
    
        tempUrl = file_str.getvalue()
        print 'get_announcement.URL ===>', tempUrl
        response = requests.get(tempUrl)
        if response.status_code == 200:
            data = response.json()
            print 'get_announcement:data ===>', data
            ctx["id"] = data["id"]
            ctx["title"] = data["title"]
            ctx["description"] = data["description"]
    
            file_str = StringIO()
            file_str.write(user_dict[request.user.username]["url"])
            file_str.write('/course/')
            file_str.write(data["courseId"])
        
            tempUrl = file_str.getvalue()
            print 'get_announcement.get_course.URL ===>', tempUrl
            response = requests.get(tempUrl)
            if response.status_code == 200:
                data = response.json()
                print 'get_announcement.get_course.DATA ===>', data
                ctx["courseId"] = data["title"]    
    
    return render_to_response("announcement_view.html",ctx)  

def update_announcement(request):
    global latest_mooc_list, default_mooc, headers, user_dict
    
    announcement_id = request.POST.get('id')
    course_id  = request.POST.get('courseId')
    
    if announcement_id is not None and announcement_id != "-1" :    
        payload = { "courseId" : course_id, "title" : request.POST.get('title'),"postDate":request.POST.get('postDate'),"status":request.POST.get('status'),"description":request.POST.get('description')}
        
        data = json.dumps(payload)
        print "The JSON file of Question and answers", data
        print "URL ", user_dict[request.user.username]["url"] + "/announcement/update/" + announcement_id
        response = requests.put(user_dict[request.user.username]["url"] + "/announcement/update/"+ announcement_id , data, headers=headers)

        if response.status_code == 200:
            return list_announcement(request, "Announcement Updated Successfully !!")
        else:
            return list_announcement(request, "Failed to Update announcement!!")  

#
# discussion
#
def discussion(request):
    global latest_mooc_list, default_mooc, headers, user_dict
    ctx = get_context(request)

    ctx["id"] = "-1"
    name = request.GET.get('id')
    if name is not None:
        file_str = StringIO()
        file_str.write(user_dict[request.user.username]["url"])
        file_str.write('/course/discussion/')
        file_str.write(name)
        
        tempUrl = file_str.getvalue()
        print 'discussion.URL ===>', tempUrl
        response = requests.get(tempUrl)
        if response.status_code == 200:
            data = response.json()
            print 'quiz:data ===>', data
            ctx["id"] = data["id"]
            ctx["name"] = data["name"]
            ctx["category"] = data["category"]
    
    ctx["course_list"] = []
    file_str = StringIO()
    file_str.write(user_dict[request.user.username]["url"])
    file_str.write('/course/list')
    
    tempUrl = file_str.getvalue()
    print 'course.list_category.URL ===>', tempUrl
    response = requests.get(tempUrl)
    if response.status_code == 200:
        data = response.json()
        print 'list_category:data ===>', data
        ctx["course_list"] = data["list"]    
    
    return render_to_response("discussion_add.html",ctx)

def add_discussion(request):
    global latest_mooc_list, default_mooc, headers, user_dict
    data = {} 
    
    discussion_id = request.POST.get('discussion_id')
    if discussion_id is not None and discussion_id != "-1" :
        data["_id"] = discussion_id
    data["course_id"] = request.POST.get("course_id")
    data["title"] = request.POST.get("title")
    data["created_by"] = request.user.email
    data["created_at"] = strftime("%Y-%m-%d %H:%M:%S", localtime())
    data["updated_at"] = strftime("%Y-%m-%d %H:%M:%S", localtime())

    print 'add_discussion:data ===>', data
    
    file_str = StringIO()
    file_str.write(user_dict[request.user.username]["url"])
    file_str.write('/discussions')
    
    tempUrl = file_str.getvalue()
    print 'add_discussion.URL ===>', tempUrl
    response = requests.post(tempUrl, json.dumps(data), headers=headers)
    if response.status_code == 200 or response.status_code == 201:
        res_data = response.json()
        print 'add_discussion:data ===>', res_data
        if discussion_id is None or discussion_id == "-1" :
            discussion_id =  res_data["id"]
            return list_discussion(request, "Discussion added!!!")
        else:
            return list_discussion(request, "Discussion edited!!!")
    
    ctx = get_context(request, "Failed to add Discussion")
    ctx["id"] = discussion_id
    ctx["course_id"]  = data["course_id"]
    ctx["title"] = data["title"]
    ctx["created_by"] = request.user.username
    ctx["created_at"] = data["created_at"]
    ctx["updated_at"] = data["updated_at"]
    
    return render_to_response("discussion_add.html",ctx)  
  
def get_discussion(request,msg=''):
    global latest_mooc_list, default_mooc, headers, user_dict
    discussion_id = request.GET.get('id')
    if discussion_id is None:
        discussion_id = request.POST.get("id")
    if discussion_id is not None and discussion_id != "-1" :
        file_str = StringIO()
        file_str.write(user_dict[request.user.username]["url"])
        file_str.write('/discussion/')
        file_str.write(discussion_id)        
        
        tempUrl = file_str.getvalue()
        print 'get_discussion.URL ===>', tempUrl
        response = requests.get(tempUrl)
        if response.status_code == 200:
            data = response.json()
            print 'get_discussion:data ===>', data
            ctx = get_context(request, "")
            ctx["id"] = discussion_id
            ctx["course_id"]  = data["course_id"]
            ctx["title"] = data["title"]
            ctx["created_by"] = data["created_by"]
            ctx["created_at"] = data["created_at"]
            ctx["updated_at"] = data["updated_at"]
            
            
            ctx["message_list"] = []
            
            file_str = StringIO()
            file_str.write(user_dict[request.user.username]["url"])
            file_str.write('/discussion/')
            file_str.write(discussion_id)
            file_str.write('/messages')
              
            tempUrl = file_str.getvalue()
            print 'list_message.URL ===>', tempUrl
            response = requests.get(tempUrl)
            if response.status_code == 200:
                data = response.json()
                print 'list_message:data ===>', data
                ctx["message_list"] = data["list"]

            fetch_user(request, ctx)
            ctx["message"] = msg
            return render_to_response("discussion_view.html",ctx)
        else:
            return list_discussion(request, "Failed to get discussion!!")  


def list_discussion(request,msg=''):
    global latest_mooc_list, default_mooc, headers, user_dict

    name = request.GET.get('courseId')
    if name is not None and name != "-1" :
        file_str_cat = StringIO()
        file_str_cat.write(user_dict[request.user.username]["url"])
        file_str_cat.write('/course/')
        file_str_cat.write(name)        
        
        tempUrl = file_str_cat.getvalue()
        print 'list_discussion.course.URL ===>', tempUrl
        response = requests.get(tempUrl)
        if response.status_code == 200:
            data = response.json()
            print 'list_discussion.course.Data ===>', data
            user_dict[request.user.username]["course"] = {"id": name, "title":data["title"]}
    else:
        try:
            del user_dict[request.user.username]["course"] # remove entry with key 'Name'
        except:
            print 'list_discussion.selected_course.delete ===> failed...'        
  
    ctx = get_context(request, msg)
    ctx["discussion_list"] = []
    
    file_str = StringIO()
    file_str.write(user_dict[request.user.username]["url"])
    file_str.write('/discussion/list')
   
    tempUrl = file_str.getvalue()
    print 'list_discussion.URL ===>', tempUrl
    response = requests.get(tempUrl)
    if response.status_code == 200:
        data = response.json()
        print 'list_discussion:data ===>', data
        ctx["discussion_list"] = data["list"]
        
    fetch_user(request, ctx)
    return render_to_response("discussions.html",ctx)

def edit_discussion(request):
    global latest_mooc_list, default_mooc, headers, user_dict
    ctx = get_context(request)
   
    discussion_id = request.GET.get('id')

    file_str = StringIO()
    file_str.write(user_dict[request.user.username]["url"])
    file_str.write('/discussion/')
    file_str.write(discussion_id)        
        
    tempUrl = file_str.getvalue()
    print 'edit_discussion.get_discussion.URL ===>', tempUrl
    response = requests.get(tempUrl)
    if response.status_code == 200:
            data = response.json()
            print 'get_discussion:data ===>', data
            ctx = get_context(request, "")
            ctx["id"] = discussion_id
            ctx["courseId"]  = data["course_id"]
            ctx["title"] = data["title"]
            ctx["created_by"] = data["created_by"]
            ctx["created_at"] = data["created_at"]
            ctx["updated_at"] = data["updated_at"]

            
            file_str_cat = StringIO()
            file_str_cat.write(user_dict[request.user.username]["url"])
            file_str_cat.write('/course/')
            file_str_cat.write(data["course_id"])        
            
            tempUrl = file_str_cat.getvalue()
            print 'edit_discussion.get_course.URL ===>', tempUrl
            response = requests.get(tempUrl)
            if response.status_code == 200:
                data = response.json()
                print 'list_announcement.get_course.DATA ===>', data
                ctx["courseTitle"] = data["title"]
        
            fetch_user(request, ctx)
            return render_to_response("discussion_edit.html",ctx)
    else:
            return list_discussion(request, "Failed to get discussion!!")

def remove_discussion(request):
    global latest_mooc_list, default_mooc, headers, user_dict
    name = request.GET.get('id')
    if name is not None and name != "-1" :
        file_str = StringIO()
        file_str.write(user_dict[request.user.username]["url"])
        file_str.write('/discussion/delete/')
        file_str.write(name)        
        
        tempUrl = file_str.getvalue()
        print 'remove_discussion.URL ===>', tempUrl
        response = requests.delete(tempUrl)
        if response.status_code == 200:
            return list_discussion(request, "Discussion removed!!!")
    return list_discussion(request, "Failed to remove discussion!!")  

def update_discussion(request):
    global latest_mooc_list, default_mooc, headers, user_dict
    
    discussion_id = request.POST.get('discId')
    course_id  = request.POST.get('courseId')
    print 'discussion_id  >> ' ,discussion_id
    if discussion_id is not None and discussion_id != "-1" :    
        payload = { "course_id" : course_id, "title" : request.POST.get('discTitle'),"created_by":request.POST.get('created_by'),"created_at":request.POST.get('created_at'),"updated_at":strftime("%Y-%m-%d %H:%M:%S", localtime())}
        
        data = json.dumps(payload)
        print "The JSON file of Question and answers", data
        print "URL ", user_dict[request.user.username]["url"] + "/discussion/update/" + discussion_id
        response = requests.put(user_dict[request.user.username]["url"] + "/discussion/update/"+ discussion_id , data, headers=headers)

        if response.status_code == 200:
            return list_discussion(request, "Discussion Updated Successfully !!")
        else:
            return list_discussion(request, "Failed to Update discussion!!")  

def message(request):
    global latest_mooc_list, default_mooc, headers, user_dict
    ctx = get_context(request)
    ctx["id"] =  request.GET.get('id')
    return render_to_response("message_add.html",ctx)

def edit_announcement(request):
    global latest_mooc_list, default_mooc, headers, user_dict
    ctx = get_context(request)
   
    announcement_id = request.GET.get('id')

    file_str = StringIO()
    file_str.write(user_dict[request.user.username]["url"])
    file_str.write('/announcement/')
    file_str.write(announcement_id)        
        
    tempUrl = file_str.getvalue()
    print 'edit_announcement.get_announcement.URL ===>', tempUrl
    response = requests.get(tempUrl)
    if response.status_code == 200:
            data = response.json()
            print 'get_discussion:data ===>', data
            ctx = get_context(request, "")
            ctx["id"] = announcement_id
            ctx["title"] = data["title"]
            ctx["courseId"] = data["courseId"]
            ctx["description"] = data["description"]    
            ctx["postDate"] = data["postDate"]
            ctx["status"] = data["status"]
            
            file_str_cat = StringIO()
            file_str_cat.write(user_dict[request.user.username]["url"])
            file_str_cat.write('/course/')
            file_str_cat.write(data["courseId"])        
            
            tempUrl = file_str_cat.getvalue()
            print 'edit_discussion.get_course.URL ===>', tempUrl
            response = requests.get(tempUrl)
            if response.status_code == 200:
                data = response.json()
                print 'list_announcement.get_course.DATA ===>', data
                ctx["courseTitle"] = data["title"]
        
            fetch_user(request, ctx)
 
            return render_to_response("announcement_edit.html",ctx)
    else:
            return list_announcement(request, "Failed to get announcement!!")


def add_message(request):
    global latest_mooc_list, default_mooc, headers, user_dict
    data = {} 
    message_id = request.POST.get('message_id')
    
    discussion_id = request.POST.get("id")
    if message_id is not None and message_id != "-1" :
        data["_id"] = message_id

   
    if message_id == "-1" or message_id is None :
        data = {"discussion_id" : discussion_id, "title":request.POST.get('title'), "content":request.POST.get('content'), "created_by":request.user.email,"created_at":strftime("%Y-%m-%d %H:%M:%S", localtime()),"updated_at":strftime("%Y-%m-%d %H:%M:%S", localtime())}    
    else:
        data = {"_id":message_id,"discussion_id": discussion_id, "title":request.POST.get('title'), "content":request.POST.get('content'), "created_by":request.user.email,"created_at":strftime("%Y-%m-%d %H:%M:%S", localtime()),"updated_at":strftime("%Y-%m-%d %H:%M:%S", localtime())} 
    print 'add_message:data ===>', data

    file_str = StringIO()
    file_str.write(user_dict[request.user.username]["url"])
    file_str.write('/discussion/add/')
    file_str.write(discussion_id)
    file_str.write('/messages')
    
    tempUrl = file_str.getvalue()
    print 'add_message.URL ===>', tempUrl
    response = requests.post(tempUrl, json.dumps(data), headers=headers)
    if response.status_code == 200 or response.status_code == 201:
        if message_id == "-1" or message_id is None :
            data = response.json()
            print 'message:data ===>', data     
                
            return get_discussion(request, "Message added!!!")
        else:
            return get_discussion(request, "Message edited!!!")
    
    ctx = get_context(request, "Failed to add Message")
    ctx["id"] = message_id
    ctx["discussion_id"] = discussion_id
    ctx["title"] = data["title"]
    ctx["content"] = data["content"]
    ctx["created_at"] = data["created_at"]
    ctx["created_by"] = data["created_by"]
    ctx["updated_at"] = data["updated_at"]      
    
    return render_to_response("message_add.html",ctx)  

def update_message(request):
    global latest_mooc_list, default_mooc, headers, user_dict
    message_id = request.POST.get('message_id')
    
    print "message id ", message_id
    if message_id is not None and message_id != "-1" :
        discussion_id = request.POST.get("id")
        file_str = StringIO()
        file_str.write(user_dict[request.user.username]["url"])
        file_str.write('/message/update/')        
        file_str.write(message_id)
        file_str.write('/discussion/')
        file_str.write(discussion_id)        
            
        payload = { "discussion_id" : discussion_id, "title" : request.POST.get('title'), "content" : request.POST.get('content'),"created_by":request.POST.get('created_by'),"created_at":request.POST.get('created_at'),"updated_at":strftime("%Y-%m-%d %H:%M:%S", localtime())}
        
        data = json.dumps(payload)
        print "The JSON file of Message  ", data
        print "URL ", user_dict[request.user.username]["url"] + "/message/update/" + message_id + "/discussion/" +  discussion_id 
        response = requests.put(user_dict[request.user.username]["url"] + "/message/update/"+  message_id + "/discussion/" + discussion_id , data, headers=headers)

        if response.status_code == 200:
            return get_discussion(request, "Message Updated Successfully !!")
        else:
            return get_discussion(request, "Failed to Update message!!") 
        
# def edit_message(request):
#     global latest_mooc_list, default_mooc, headers, user_dict
#     ctx = get_context(request)
#     message_id = request.GET.get('message_id')
#     discussion_id = request.GET.get("id")
#     
#     print "mess ", message_id, " desc ", discussion_id
#     file_str = StringIO()
#     file_str.write(user_dict[request.user.username]["url"])
#     file_str.write('/discussion/')        
#     file_str.write(discussion_id)
#     file_str.write('/message/')
#     file_str.write(message_id)       
#         
#     tempUrl = file_str.getvalue()
#     print 'edit_message.get_message.URL ===>', tempUrl
#     response = requests.get(tempUrl)
#     if response.status_code == 200:
#             data = response.json()
#             print 'get_message:data ===>', data
#             ctx = get_context(request, "")
#             ctx["id"] = message_id
#             ctx["discussion_id"] = discussion_id
#             ctx["title"] = data["title"]
#             ctx["content"] = data["content"]
#             ctx["created_at"] = data["created_at"]
#             ctx["created_by"] = data["created_by"]
#             ctx["updated_at"] = data["updated_at"]
#             fetch_user(request, ctx)        
# 
#             return render_to_response("message_edit.html",ctx)
#     else:
#             return get_discussion(request, "Failed to get message!!")

def list_message(request,msg=''):
    global latest_mooc_list, default_mooc, headers, user_dict
    discussion_id = request.GET.get('id')
    ctx = get_context(request, msg)
    ctx["message_list"] = []
    
    file_str = StringIO()
    file_str.write(user_dict[request.user.username]["url"])
    file_str.write('/discussion/')
    file_str.write(discussion_id)
    file_str.write('/messages')
      
    tempUrl = file_str.getvalue()
    print 'list_message.URL ===>', tempUrl
    response = requests.get(tempUrl)
    if response.status_code == 200:
        data = response.json()
        print 'list_message:data ===>', data
        ctx["message_list"] = data["list"]
        
    fetch_user(request, ctx)
    return render_to_response("discussion_view.html",ctx)

def remove_message(request):
    global latest_mooc_list, default_mooc, headers, user_dict
   
    message_id = request.GET.get('message_id')
    
    if message_id is not None and message_id != "-1" :
        discussion_id = request.GET.get("id")
        file_str = StringIO()
        file_str.write(user_dict[request.user.username]["url"])        
        file_str.write('/message/')
        file_str.write(message_id)  
        file_str.write('/discussion/')
        file_str.write(discussion_id)      
        
        tempUrl = file_str.getvalue()
        print 'remove_message.URL ===>', tempUrl
        response = requests.delete(tempUrl)
        if response.status_code == 200:
            data = response.json()
            print 'remove_message:data ===>', data            
            return get_discussion(request, "Message removed!!!") 
             
    return get_discussion(request, "Failed to remove message!!")  
 
#
# quiz
#
def quiz(request):
    global latest_mooc_list, default_mooc, headers, user_dict

    ctx = get_context(request)

    ctx["id"] = "-1"
    name = request.GET.get('id')
    if name is not None:
        file_str = StringIO()
        file_str.write(user_dict[request.user.username]["url"])
        file_str.write('/course/quiz/')
        file_str.write(name)
        
        tempUrl = file_str.getvalue()
        print 'quiz.URL ===>', tempUrl
        response = requests.get(tempUrl)
        if response.status_code == 200:
            data = response.json()
            print 'quiz:data ===>', data
            ctx["id"] = data["id"]
            ctx["name"] = data["name"]
            ctx["category"] = data["category"]
    
    ctx["course_list"] = []
    file_str = StringIO()
    file_str.write(user_dict[request.user.username]["url"])
    file_str.write('/course/list')
    
    tempUrl = file_str.getvalue()
    print 'course.list_category.URL ===>', tempUrl
    response = requests.get(tempUrl)
    if response.status_code == 200:
        data = response.json()
        print 'list_category:data ===>', data
        ctx["course_list"] = data["list"]    
    return render_to_response("quiz_add.html",ctx)

def add_quiz(request):
    global latest_mooc_list, default_mooc, headers, user_dict
    data = {} 

    courseId = request.POST.get("courseId")
    data["courseId"] = courseId
    data["questions"] = []
    
    quizId = request.POST.get("quizId")
    
    if quizId is not None and quizId != "-1" :
        data["id"] = quizId

    print 'add_quiz:data ===>', data
    
    file_str = StringIO()
    file_str.write(user_dict[request.user.username]["url"])
    file_str.write('/quizzes')
    
    tempUrl = file_str.getvalue()
    print 'add_quiz.URL ===>', tempUrl
    response = requests.post(tempUrl, json.dumps(data), headers=headers)
    if response.status_code == 200 or response.status_code == 201:
        res_data = response.json()
        print 'add_quiz:data ===>', res_data
        return list_quiz(request, "Quiz added!!!")
    else:
        return list_quiz(request, "Quiz edited!!!")
    
    ctx = get_context(request, "Failed to add Quiz")
    ctx["id"] = data["id"]
    return render_to_response("quiz_add.html",ctx)


#   payload = {"_id": "quizid1","courseId": "courseId","questions": [{"question": "Que1","options": ["option1","option2"],"answer": "option1","point": 1},{"question": "Que2","options": ["option1","option2"],"answer":"option1","point": 1}]}
#    response = requests.post(user_dict[request.user.username]["url"] + quiz, data=json.dumps(payload), headers=headers)
#    print response.text
   
def list_quiz(request,msg=''):
    global latest_mooc_list, default_mooc, headers, user_dict
    
    ctx = get_context(request, msg)
    ctx["quiz_list"] = []
    
    file_str = StringIO()
    file_str.write(user_dict[request.user.username]["url"])
    file_str.write('/quiz/list')

    name = request.GET.get('quizCourse')
    if name is not None and name != "-1" :
        file_str_cat = StringIO()
        file_str_cat.write(user_dict[request.user.username]["url"])
        file_str_cat.write('/quiz')
        file_str_cat.write(name)        
        
        tempUrl = file_str_cat.getvalue()
        print 'list_quiz.URL ===>', tempUrl
        response = requests.get(tempUrl)
        if response.status_code == 200:
            data = response.json()
            user_dict[request.user.username]["quiz"] = {"id": name, "name":data["'title"]}
        
        file_str.write("/")
        file_str.write(name)
   
    tempUrl = file_str.getvalue()
    print 'list_quiz.URL ===>', tempUrl
    response = requests.get(tempUrl)
    if response.status_code == 200:
        data = response.json()
        print 'course_quiz:data ===>', data
        ctx["quiz_list"] = data["list"]

    ctx["quiz_list"] = []
    file_str = StringIO()
    file_str.write(user_dict[request.user.username]["url"])
    file_str.write('/quiz/list')
    
    tempUrl = file_str.getvalue()
    print 'quiz.list_course.URL ===>', tempUrl
    response = requests.get(tempUrl)
    if response.status_code == 200:
        data = response.json()
        print 'list_quite:data ===>', data
        ctx["quiz_list"] = data["list"] 
        
    fetch_user(request, ctx)
    return render_to_response("quiz.html",ctx)


def remove_quiz(request):
    global latest_mooc_list, default_mooc, headers, user_dict
    name = request.GET.get('id')
    if name is not None and name != "-1" :
        file_str = StringIO()
        file_str.write(user_dict[request.user.username]["url"])
        file_str.write('/quiz/')
        file_str.write(name)        
        
        tempUrl = file_str.getvalue()
        print 'remove_quiz.URL ===>', tempUrl
        response = requests.delete(tempUrl)
        if response.status_code == 200:
            return list_quiz(request, "Quiz removed!!!")
    return list_quiz(request, "Failed to remove quiz!!")  

def edit_quiz(request):
    global latest_mooc_list, default_mooc, headers, user_dict
    quiz_id = request.GET.get('id')
    if quiz_id is not None and quiz_id != "-1" :
        file_str = StringIO()
        file_str.write(user_dict[request.user.username]["url"])
        file_str.write('/quiz/')
        file_str.write(quiz_id)        
        
        tempUrl = file_str.getvalue()
        print 'edit_quiz.URL ===>', tempUrl
        response = requests.get(tempUrl)
        if response.status_code == 200:
            data = response.json()
            print 'edit_quiz:data ===>', data
            ctx = get_context(request, "")
            ctx["id"] = quiz_id 
            ctx["questions"] = data["questions"]
            ctx["courseId"] = data["courseId"]
            fetch_user(request, ctx)
            return render_to_response("quiz_edit.html",ctx)
        else:
            return list_quiz(request, "Failed to get quiz!!")  

def update_quiz(request):
    global latest_mooc_list, default_mooc, headers, user_dict
    quiz_id = request.POST.get('quizId')
    course_id = request.POST.get('courseId')
    if quiz_id is not None and quiz_id != "-1" :
        file_str = StringIO()
        file_str.write(user_dict[request.user.username]["url"])
        file_str.write('/quiz/update')
        file_str.write(quiz_id)        
        i = 1
        payload = { "courseId" : course_id, "questions" : []}
        while True:
            question = request.POST.get('ques'+str(i))
            if question is None:
                break
            option1 = request.POST.get('ques'+str(i) +'.option1')
            option2 = request.POST.get('ques'+str(i) +'.option2')
            option3 = request.POST.get('ques'+str(i) +'.option3')
            option4 = request.POST.get('ques'+str(i) +'.option4')
            coAO = request.POST.get('ques'+str(i) +'.correctAnswer')
            correct_ans = request.POST.get('ques'+str(i) +'.' +coAO) 

            print question,option1 , option2, option3, option4, coAO, correct_ans
            payload['questions'].append({'question': question,'options': [option1,option2,option3,option4],'answer': correct_ans,'point': '1'})
            i = i + 1
            
        tempUrl = file_str.getvalue()
        print 'update_quiz.URL ===>', tempUrl
        data = json.dumps(payload)
        print "The JSON file of Question and answers", data
        print "URL ", user_dict[request.user.username]["url"] + "/quiz/update/" + quiz_id
        response = requests.put(user_dict[request.user.username]["url"] + "/quiz/update/"+ quiz_id , data, headers=headers)
        if response.status_code == 200: 
            return list_quiz(request, "Quiz Updated !")
    else :
        return list_quiz(request, "Quiz Update Failed")  
    
def get_quiz(request):
    global latest_mooc_list, default_mooc, headers, user_dict
    quiz_id = request.GET.get('id')
    if quiz_id is not None and quiz_id != "-1" :
        file_str = StringIO()
        file_str.write(user_dict[request.user.username]["url"])
        file_str.write('/quiz/')
        file_str.write(quiz_id)        
        
        tempUrl = file_str.getvalue()
        print 'get_quiz.URL ===>', tempUrl
        response = requests.get(tempUrl)
        if response.status_code == 200:
            data = response.json()
            print 'get_quiz:data ===>', data
            ctx = get_context(request, "")
            ctx["questions"] = data["questions"]
            ctx["id"] = quiz_id 
            fetch_user(request, ctx)
            return render_to_response("quiz_take.html",ctx)
        else:
            return list_quiz(request, "Failed to get quiz!!")  

def show_addquestionhtml(request):
    global latest_mooc_list, default_mooc, headers, user_dict
    quiz_id = request.GET.get('id')
    course_id = request.GET.get('courseId')    
    print quiz_id , course_id
    ctx = get_context(request, "")
    ctx["id"] = quiz_id
    ctx["course_id"] = course_id
    return render_to_response("question_add.html",ctx)  

def add_question(request):
    global latest_mooc_list, default_mooc, headers, user_dict
    payload = {}
    quizId = request.POST.get('quizId')
#     courseId = request.POST.get('courseId')
    question = request.POST.get('question')
    answer1 = request.POST.get('answer1')
    answer2 = request.POST.get('answer2')
    answer3 = request.POST.get('answer3')
    answer4 = request.POST.get('answer4')
    coAO = request.POST.get('correctAnswer')
    correctAnswer = request.POST.get(coAO) 

    file_str = StringIO()
    file_str.write(user_dict[request.user.username]["url"])
    file_str.write('/quiz/')
    file_str.write(quizId)        
    tempUrl = file_str.getvalue()
    print 'get_quiz.URL ===>', tempUrl
    response = requests.get(tempUrl)
    if response.status_code == 200:
        payload = response.json()
        print 'get_quiz:data ===>', payload

    payload["questions"].append({'question': question,'options': [answer1,answer2,answer3,answer4],'answer': correctAnswer,'point': '1'})
    data = json.dumps(payload)
    print "The JSON file of Question and answers", data
    print "URL ", user_dict[request.user.username]["url"] + "/quiz/update/" + quizId
    response = requests.put(user_dict[request.user.username]["url"] + "/quiz/update/"+ quizId , data, headers=headers)
    ctx = get_context(request, "Question Added!")
    ctx["id"] = quizId
    fetch_user(request, ctx)
    return render_to_response("question_add.html",ctx) 

def evaluate_quiz(request):
    global latest_mooc_list, default_mooc, headers, user_dict
    ctx = get_context(request)
    fetch_user(request, ctx)
    quizId = request.POST.get('quizId')
    score = 0
    i = 1     
    while True:
        selected_ans = request.POST.get('Que'+ str(i))
        correct_ans = request.POST.get('Ans'+ str(i))
        if correct_ans is None:
            break
        if selected_ans == correct_ans:
            score = score + 1
        print "Question", selected_ans
        print "Answer", correct_ans
        i = i + 1

    name = request.user.username
    if name is not None and name != "-1" :
        # Now we need to enroll course for user object
        file_str = StringIO()
        file_str.write(default_mooc.primary_URL)
        file_str.write('/user/')
        file_str.write(request.user.username)
    
        tempUrl = file_str.getvalue()
        response = requests.get(tempUrl)
        if response.status_code == 200:
            data = response.json()
            print 'evaluate_quiz.fetch_user:data ===>', data
        
            try:
                data["quizzes"].append({'quiz': quizId,'grade': score,'submitDate': strftime("%Y-%m-%d %H:%M:%S", localtime())})
                
                file_str = StringIO()
                file_str.write(default_mooc.primary_URL)
                file_str.write('/user/update/')
                file_str.write(request.user.username)
                
                tempUrl = file_str.getvalue()                
                user_update_response = requests.put(tempUrl, data=json.dumps(data), headers=headers)
                if user_update_response.status_code == 200:
                    user_update_data = user_update_response.json()
                    print 'evaluate_quiz.update_user:data ===>', user_update_data                    
            except:
                print 'evaluate_quiz.append_to_user ===> failed'    
    else:
        print 'evaluate_quiz ===> failed'  
        return list_quiz(request,"Quiz Graded. Score "+ str(score))  
    return list_quiz(request,"Quiz Graded. Score "+ str(score))  

        