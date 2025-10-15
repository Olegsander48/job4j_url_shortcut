## job4j_url_shortcut
[![Java CI with Maven](https://github.com/Olegsander48/job4j_url_shortcut/actions/workflows/maven.yml/badge.svg)](https://github.com/Olegsander48/job4j_url_shortcut/actions/workflows/maven.yml) [![Build JAR](https://github.com/Olegsander48/job4j_url_shortcut/actions/workflows/build_jar.yml/badge.svg)](https://github.com/Olegsander48/job4j_url_shortcut/actions/workflows/build_jar.yml)
## Описание проекта:
Данное rest-приложение служит для форматирования ссылок браузера. Мы отправляем исходную ссылку и на выходе получаем короткий уникальный код. Обратившись по этому коду, мы сможем получить редирект на нашу страницу. Этот механизм позволяет сокращать и маскировать ссылки, подобно тому, как это делают Google, Facebook, Meta и прочие техно-гиганты.

## Стек технологий:
- Java 21
- PostgreSQL 17
- Maven 4.0.0
- Spring boot 3.5.6
  - starter-data-jpa 
  - starter-security 
  - starter-web 
  - starter-validation 
- Liquibase 4.31.1
- H2 db 2.2.232
- Lombok 1.18.40
- java-jwt 4.4.0

## Требования к окружению:
- Java 21
- PostgreSQL 17

## Запуск проекта:
1. Необходимо создать базу данных **url_shortcut** в PostgreSQL
```SQL
create database url_shortcut
```
2. Перейдите на [страницу](https://github.com/Olegsander48/job4j_url_shortcut/actions/workflows/build_jar.yml) и выберите последний выполненный worflow, скачайте архив my-app-jar
3. Из архива достаньте файл job4j_url_shortcut-0.0.1-SNAPSHOT.jar
4. Перейдите в директорию с jar-файлом, выполните команду:
```CMD
java -jar job4j_url_shortcut-0.0.1-SNAPSHOT.jar
```
5. Приложение запущено
   
## Функционал приложения:
Функционал приложения тестировался при помощи Postman ([скачать можно тут](https://www.postman.com/downloads/))

Все доступные endpoints:

![Регистрация домена](https://github.com/Olegsander48/job4j_url_shortcut/blob/master/images/endpoints.png)

### Регистрация и аутентификация :
1. Необходимо зарегистрировать домен. Для этого отправим запрос:
![Регистрация домена](https://github.com/Olegsander48/job4j_url_shortcut/blob/master/images/site_registration.png)

  В ответ от сервера получим сообщение об успешной регистрации сайта, а так же логин и пароль:
![Данные для входа](https://github.com/Olegsander48/job4j_url_shortcut/blob/master/images/site_credentials.png)

2. После регистрации необходимо войти в систему под полученными логином и паролем. Для этого отправляем запрос:
![аутентификация](https://github.com/Olegsander48/job4j_url_shortcut/blob/master/images/site_login.png)

  В ответ от сервера получим сообщение об успешной аунтефикации на сейте, а так же JWT-токен, позволяющий моментально идентифицировать зарегестрированного пользователя (P.S. токен актуален 10 дней, после чего необходимо будет повторно проходить аунтефикацию):
![JWT токен](https://github.com/Olegsander48/job4j_url_shortcut/blob/master/images/site_bearer_token.png)

Данный токен необходимо указать на вкладке "authorization" при отправке последующих запросов или в заголовке запроса:
![Окно ввода токена](https://github.com/Olegsander48/job4j_url_shortcut/blob/master/images/conversion_with_token.png)

### Основной функционал:
1. Теперь перейдем к основному функционалу - преобразованию ссылок. Для этого отправляем post-запрос:
![Преобразование запроса](https://github.com/Olegsander48/job4j_url_shortcut/blob/master/images/url_conversion.png)

В ответ нам приходит уникальный код для сокращенного URL:
![Преобразованый url в код](https://github.com/Olegsander48/job4j_url_shortcut/blob/master/images/conversion_code.png)

2. Для обращения к зарегестрированной ссылке мы отправляем get-запрос с использованием полученного выше кода:
![Get + code](https://github.com/Olegsander48/job4j_url_shortcut/blob/master/images/redirection_by_url_code.png)

Выполняется redirect на исходную страницу
![Ответ сервера](https://github.com/Olegsander48/job4j_url_shortcut/blob/master/images/redirection_result.png)

3. Приложение ведет учет количества обращений по ссылкам и позволяет получить статистику:
![Получить статистику](https://github.com/Olegsander48/job4j_url_shortcut/blob/master/images/statistic.png)

Статистика содержит url и количество обращений
![Статистика](https://github.com/Olegsander48/job4j_url_shortcut/blob/master/images/all_urls.png)

## Мои контакты:
Обращайтесь по любым вопросам и обратной связи, буду рад ответить :blush::blush:
<p align="center">
<a href="https://t.me/Olegsander48" target="blank"><img align="center" src="https://cdn.jsdelivr.net/npm/simple-icons@3.0.1/icons/telegram.svg" alt="olegsander48" height="30" width="30" /></a>&nbsp;
<a href="https://linkedin.com/in/aleksandr-prigodich-b7028a1b3" target="blank"><img align="center" src="https://cdn.jsdelivr.net/npm/simple-icons@3.0.1/icons/linkedin.svg" alt="aleksandr-prigodich" height="30" width="30" /></a>&nbsp;
<a href="http://discord.com/users/olegsander48" target="blank"><img align="center" src="https://cdn.jsdelivr.net/npm/simple-icons@3.0.1/icons/discord.svg" alt="olegsander48" height="40" width="30" /></a>&nbsp;
<a href="mailto:prigodichaleks@gmail.com?subject=Hi%20Aleks.%20I%20saw%20your%20GitHub%20profile%20&body=I'm%20writing%20to%20you%20because%20...%0A"><img align="center" src="https://cdn.jsdelivr.net/npm/simple-icons@3.0.1/icons/gmail.svg" alt="prigodichaleks@gmail.com" height="40" width="30" /></a>&nbsp;
</p>
