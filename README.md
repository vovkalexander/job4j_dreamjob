[![Build Status](https://travis-ci.com/vovkalexander/job4j_dreamjob.svg?branch=master)](https://travis-ci.com/vovkalexander/job4j_dreamjob)
[![codecov](https://codecov.io/gh/vovkalexander/job4j_dreamjob/branch/master/graph/badge.svg?token=OFGU7MKIGM)](https://codecov.io/gh/vovkalexander/job4j_dreamjob)


# job4j_dreamjob
## Проект "Работа мечты"

<p>Цель данного проекта представить веб-приложение взаимодействия
кандидатов и менеджеров по персоналу.</p>
<ul>
<li>Кандидаты  публикуют резюме.</li>
<li>Менеджеры по персоналу  публикуют вакансии о работе</li>
</ul>

<em>Проект реализован на JavaEE-технологии и web-программирование с помощью Servlet и JSP.</em>

При загрузке Url сайта, появляется следующие:

![Image of Yaktocat](images/interface.png)

1. происходит аутентификация c помощью Servlet Filter

![Image of Yaktocat](images/authentication.png)

2. если нет доступа необходима регистрация
![Image of Yaktocat](images/registry.png)

3. дальше происходит авторизация
![Image of Yaktocat](images/authorisation.png)

* В данном проекте есть возможность  применять CRUD к пользователю и 
  кандидатам
* Подключение БД - PostgresSQL
* Реализован функционал добавления и скачивания файлов
![Image of Yaktocat](images/adding.png)
* Тестирование функционала с помощью библиотеки JUnit,
* Динамически через Ajax загружать Json данные




