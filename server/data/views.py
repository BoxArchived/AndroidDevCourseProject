import json
from django.http import HttpResponse, JsonResponse
from . import models
import os
from server import settings
# Create your views here.

def test(request):
    options = ['asdas', 'ada', 'adad']
    s = 'ad'
    question = models.Question(question=s,
                                options=json.dumps(options),
                                correctAnswer=1,
                                # coverFile=f,
                                coverURL=s)
    question.save()
    s=models.Question.objects.all().first()
    s=s.to_dict()
    return JsonResponse(s)