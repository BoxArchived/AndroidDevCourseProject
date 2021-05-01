from django.test import TestCase
from . import models
from django.core import serializers
# Create your tests here.

options=['asdas','ada','adad']
s='ad'
question=models.Question(question=s,
                         options=serializers.serialize(models.serializers_format,options),
                         optionsA=s,
                         optionsB=s,
                         optionsC=s,
                         optionsD=s,
                         correctAnswer=1,
                         coverFile=s,
                         coverURL=s)
question.save()