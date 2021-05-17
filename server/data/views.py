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