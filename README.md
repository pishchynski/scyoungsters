# scyoungsters
Назначение веб-сервиса - получить Идентификатор и Имя, и, если такого
имени нет в БД MySQL, добавить его в БД и вернуть OK, если такое имя есть - вернуть ERROR.

Веб-сервис тестировался с помощью программы **SoapUI**.

WSDL можно получить по адресу http://localhost:8080/CTTTest/services/NamesService?wsdl

* arg0 - ИД
* rg1 - Имя

######Данные БД:
- database: ctttest
- username: manager	(granted all privileges, except grant option, for 'ctttest' DB)
- password: qwerty
