# Тестовое задание javaRush

### Задача

#### Требуемые технологии:
* Maven 3 (v.3.3.9) (для сборки проекта);
* Tomcat8 (для тестирования своего приложения);
* Spring (версия не ниже 4.3.0.RELEASE);
* Hibernate (версия не ниже 5.2.1.Final);
* MySQL (база данных). 
    >Для упрощения тестирования называйте все свою базу `test`, с логином и паролем `root`(нам не нужно будет для тестирования создавать кучу лишних и ненужных баз);
* Frontend: Spring MVC, или Angular, или Vaadinor, или ZK framework;
* Результат выложить на GitHub или Bitbucket.

>Версии можно смело брать самые последние. Конфликтов быть не должно.

#### CRUD(create, read, update, delete).
Необходимо реализовать стандартное CRUD приложение, которое отображаем список всех книг в базе (с пейджингом по 10 книг на странице). С возможностью их удаления, редактирования, добавления новых, и поиска по уже существующим.

У вас есть всего 1 таблица book. В ней хранится список книг(например, на книжной полке).Книги на полку можно добавлять (create), брать посмотреть(read), заменять на новый выпуск (update), убирать (delete).

**Данные, которые должны быть в таблице:**
* `id` –идентификатор книги в БД;
* `title` –название книги. Можно использовать тип VARCHAR(100);
* `description` –краткое описание о чем книга. Можно использовать тип VARCHAR(255);
* `author` –фамилия и имя автора. Можно использовать тип VARCHAR(100);
* `isbn` –ISBNкниги. Можно использовать тип VARCHAR(20);
* `printYear` –в каком году напечатана книга (INT);
* `readAlready` –читал ли кто-то эту книгу. Это булевополе.

**Бизнес-требование:** 
при редактировании может быть 2 варианта поведения:
* Книгу прочитали, и тогда изменяется только поле `readAlready`, и только, если оно было `false`.Значения поля должно стать `true`.
* Книгу заменили на новое издание. В этом случае должна быть возможность изменить `title`, `description`, `isbn`, `printYear`. А поле `readAlready` нужновыставить в `false`.Поле `author` должно быть неизменяемым с момента создания книги. 
* По какому полю искать – каждый решает для себя сам. Можно ограничиться полем title, но согласитесь, удобно просмотреть книги, которые еще не читал, или, которые вышли после 2016 года.


#### NOTES
Реализовать простенькое приложение **Notes-list**, для отображения списка заметок.

Нужно показывать список уже созданных заметок(с пейджингомпо 10 заметокна странице). Каждуюиз них можно редактировать, добавлять новые, отмечать как «Выполнено», удалять. Список можно фильтровать как «Все заметки», «Только невыполненные», «Выполненные». 

Заметки хранить в базе. Схему таблички для хранения нужно придумать самому (можно ограничиться одной таблицей).

**Бизнес-требование:** кроме фильтрации должна быть возможность сортировки заметокпо дате создания (например, поле `createdDateв` БД). Тип поля – `DATE` или `DATETIME`, или `TIMESTAMP`.

***

### Уставнока

```bash
$> git clone https://github.com/vladmeh/javaRushTestTask.git
$> cd javaRushTestTask
$> rm -rf .git
```

#### Требования к установке для проверки

*   [IntelliJ IDEA](https://www.jetbrains.com/idea/download/);
*   [JDK 6](http://www.oracle.com/technetwork/java/javase/downloads/index.html) или более поздняя [версия](http://www.oracle.com/technetwork/java/javase/downloads/index.html);
*   [Apache Maven 3.5.0](https://maven.apache.org/download.cgi);
*   [Tomcat 8](https://tomcat.apache.org/download-80.cgi)
*   [MySQL 5.5 и выше](https://dev.mysql.com/downloads/installer/);
*   [Git v.2.13](https://git-scm.com/book/ru/v2/%D0%92%D0%B2%D0%B5%D0%B4%D0%B5%D0%BD%D0%B8%D0%B5-%D0%A3%D1%81%D1%82%D0%B0%D0%BD%D0%BE%D0%B2%D0%BA%D0%B0-Git);


#### Подключение базы данных

* База данных - `test`
* Логин и пароль - `root`
* Порт - `3306`
* Скрипт базы находиться в файле `./data/test_book.sql`

```bash
mysql> source data/script.sql
mysql> source data/import.sql
```

#### Сборка и запуск проекта
```bash
$> cd javaRushTestTask
$> mvn package && java -jar target/javaRushTestTask-0.0.1-SNAPSHOT.jar
```

В браузере набираем `http://localhost:8080`. Восхищаемся шедевром!!!

[По-шаговая разработка проекта](https://github.com/vladmeh/javaRushTestTask/blob/master/STEP-BY-STEP.md)

### В процессе разработки пользовался материалом из источников:

*   Spring 4 для профессионалов (4-е издание)
*   https://docs.spring.io/
*   https://www.jetbrains.com/
*   https://www.petrikainulainen.net/spring-data-jpa-tutorial
*   http://www.natpryce.com/articles/000714.html
*   http://www.thymeleaf.org/doc/tutorials/2.1/usingthymeleaf.html
*   https://getbootstrap.com/docs/4.0/getting-started/introduction
*   http://www.baeldung.com/

