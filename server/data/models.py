from django.db import models
import json

version_string='version'
song_name_string='song'
song_singer_string='singer'
song_album_string='album'
song_coverFile_string='coverFile'
song_coverURL_string='coverURL'
song_songFile_string='songFile'
song_songURL_string='songURL'
question_question_string='question'
question_options_string='options'
question_optionsA_string='optionsA'
question_optionsB_string='optionsB'
question_optionsC_string='optionsC'
question_optionsD_string='optionsD'
question_coverFile_string='coverFile'
question_coverURL_string='coverURL'
question_correctAnswer_string='correctAnswer'
serializers_format='json'
user_username_string='username'
user_email_string='email'
user_score_string='score'
# Create your models here.

class Version(models.Model):
    ver = models.IntegerField(default=0)
    # date=models.DateField(default=timezone.now())
    def to_dict(self):
        content={
            version_string:self.ver
        }
        return content


class Song(models.Model):
    name = models.CharField(max_length=100, default='')
    singer = models.CharField(max_length=100, default='')
    album = models.CharField(max_length=100, default='')
    coverURL = models.CharField(max_length=1024, default='')
    songURL = models.CharField(max_length=1024, default='')

    def to_dict(self):
        content={
            song_name_string:self.name,
            song_singer_string:self.singer,
            song_album_string:self.album,
            song_coverURL_string:self.coverURL,
            song_songURL_string:self.songURL,
        }
        return content

class Question(models.Model):
    question=models.TextField()
    options=models.TextField()
    # optionsA=models.TextField()
    # optionsB = models.TextField()
    # optionsC = models.TextField()
    # optionsD = models.TextField()
    # coverFile = models.BinaryField()
    coverURL = models.CharField(max_length=1024, default='')
    correctAnswer=models.IntegerField(default=0)

    def to_dict(self):
        options=json.loads(self.options)
        content={
            question_question_string:self.question,
            question_options_string:options,
            question_coverURL_string:self.coverURL,
            question_correctAnswer_string:self.correctAnswer
        }
        return content

class User(models.Model):
    username=models.CharField(max_length=1024)
    email=models.EmailField()
    score=models.IntegerField()
    def to_dict(self):
        content={
            user_username_string:self.username,
            user_email_string:self.email,
            user_score_string:self.score,
        }
        return content
