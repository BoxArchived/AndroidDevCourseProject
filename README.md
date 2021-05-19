# Android Development Course Project

This is my project for [CISC3002](http://cis.um.edu.mo/bsccourses.php?code=CISC3002&year=16)

## Android
`client` folder
### Development environment
- Android Gradle Plugin 4.1.2
- Gradle 6.5
- Compile SDK Version 30
  - API 30: Android 11.0(R)
- Build Version 30.0.3
- JDK 1.8
### Testing environment
- Android 11.0 Virtual Machine

### Requirement
- Network access

## Server/ Python
`server` folder
### Development environment
- Windows 10
- Python 3.8.3
- pip 21.0.1

### Testing/ Running environment (Temporary server)
- Ubuntu 20.04 (LTS) x64
- Python 3.8.5
- pip 20.0.2

### Requirement
- Django
  - `pip3 install django`
- Write permissions to database files
  - `chmod 777 db.sqlite3`
### Other
- Run server
  - `python3 manage.py runserver`
  - Use `uwsgi`
    - [Setting up Django and your web server with uWSGI and nginx](https://uwsgi-docs.readthedocs.io/en/latest/tutorials/Django_and_nginx.html)