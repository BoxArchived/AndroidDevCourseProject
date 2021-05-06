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
    return  JsonResponse(list)

def get_questions(request):
    list=[]
    questions=models.Question.objects.all()
    for question in questions:
        list.append(question.to_dict())
    return JsonResponse(list)

def test(request):
    list=[]
    list.append('Explain the difference between a regressive tax and a progressive tax.')
    list.append('Unlike progressive taxes, regressive taxes are sometimes referred to as  "Robin Hood" taxes')
    list.append('A regressive tax imposes a greater burden on the rich than the poor')
    list.append('A progressive tax imposes a greater burden on the rich than the poor')
    q = models.Question(
        question='Explain the difference between a regressive tax and a progressive tax.',
        options=json.dumps(list),
        coverURL='https://www.advisoryexcellence.com/wp-content/uploads/2019/09/tax13-getty.jpg',
        correctAnswer=3
    )
    q.save()
    return HttpResponse('ok')