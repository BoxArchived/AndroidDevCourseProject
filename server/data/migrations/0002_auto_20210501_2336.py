# Generated by Django 3.0.8 on 2021-05-01 15:36

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('data', '0001_initial'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='song',
            name='coverFile',
        ),
        migrations.RemoveField(
            model_name='song',
            name='songFile',
        ),
    ]