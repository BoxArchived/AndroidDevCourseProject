from django.http import HttpResponse, JsonResponse
from . import models
import json

# Create your views here.

def get_version(request):
    version = models.Version.objects.all().first()
    return JsonResponse(version.to_dict())

def get_songs(request):
    list=[]
    songs=models.Song.objects.all()
    for song in songs:
        list.append(song.to_dict())
    return  JsonResponse(list, safe=False)

def get_questions(request):
    list=[]
    questions=models.Question.objects.all()
    for question in questions:
        list.append(question.to_dict())
    return JsonResponse(list)

def get_rank(request):
    list=[]
    rank=models.User.objects.all()
    for i in rank:
        list.append(i.to_dict())
    return JsonResponse(list, safe=False)

def submit_rank(request):
    username=request.POST[models.user_username_string]
    email=request.POST[models.user_email_string]
    score=request.POST[models.user_score_string]
    if len(models.User.objects.filter(email=email))==0:
        user=models.User(
            username=username,
            email=email,
            score=score
        )
        user.save()
    else:
        user=models.User.objects.filter(email=email).first()
        user.score=score
        user.save()
    return HttpResponse("OK")