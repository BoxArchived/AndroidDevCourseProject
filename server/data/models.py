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
    coverFile = models.BinaryField()
    coverURL = models.CharField(max_length=1024, default='')
    songFile = models.BinaryField()
    songURL = models.CharField(max_length=1024, default='')

    def to_dict(self):
        content={
            song_name_string:self.name,
            song_singer_string:self.singer,
            song_album_string:self.album,
            song_coverFile_string:self.coverFile,
            song_coverURL_string:self.coverURL,
            song_songFile_string:self.songFile,
            song_songURL_string:self.songURL
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
            # question_optionsA_string:self.optionsA,
            # question_optionsB_string:self.optionsB,
            # question_optionsC_string:self.optionsC,
            # question_optionsD_string:self.optionsD,
            # question_coverFile_string:self.coverFile,
            question_coverURL_string:self.coverURL,
            question_correctAnswer_string:self.correctAnswer
        }
        return content