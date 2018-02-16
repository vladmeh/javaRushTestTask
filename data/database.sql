#DROP DATABASE IF EXISTS test;
CREATE DATABASE IF NOT EXISTS test CHARACTER SET utf8 COLLATE utf8_general_ci;

USE test;

DROP TABLE IF EXISTS book;
CREATE TABLE book
(
  id INT AUTO_INCREMENT
    PRIMARY KEY ,
  title VARCHAR(100) NULL,
  description VARCHAR(255) NULL,
  autor VARCHAR(100) NULL,
  isbn VARCHAR(20) NULL,
  print_year INT NULL,
  read_already TINYINT NULL,
  CONSTRAINT id_UNIQUE
  UNIQUE (id)
)
  ENGINE = innoDB
  DEFAULT CHARACTER SET = utf8
;

INSERT INTO book (title, description, autor, isbn, print_year, read_already) VALUES
  ('Spring 4 для профессионалов',
   'Руководство по Spring Framework 4, соответствующее отраслевым стандартам',
   'Крис Шеффер, Кларенс Хо, Роб Харроп',
   '978-5-8459-1992-2',
   2017, FALSE );

INSERT INTO book (title, description, autor, isbn, print_year, read_already) VALUES
  ('Алгоритмы. Теория и практическое применение',
   'Численные алгоритмы, структурыданных, ьетодыработы с массивами, связанными списками и сетями',
   'Род Стивенс',
   '978-5-699-81729-0',
   2017, TRUE );

INSERT INTO book (title, description, autor, isbn, print_year, read_already) VALUES
  ('Философия Java',
   'Основные проблемы написания кода: в чем их природа и какой подход использует Java в их разрешении.',
   'Брюс Эккель',
   '978-5-496-01127-3',
   2016, TRUE );

INSERT INTO book (title, description, autor, isbn, print_year, read_already) VALUES
  ('Bash. Карманный справочник системного администратора',
   'Описание оболочки Bash',
   'Арнольд Роббинс',
   '978-5-9909445-4-1',
   2017, FALSE );

INSERT INTO book (title, description, autor, isbn, print_year, read_already) VALUES
  ('Приемы объектно-ориентированного проектирования. Паттерны проектирования',
   'Принципы использования паттернов проектирования.',
   'Эрих Гамма, Ричард Хелм, Ральф Джонсон, Джон Влиссидес',
   '978-5-459-01720-5',
   2016, FALSE );

INSERT INTO book (title, description, autor, isbn, print_year, read_already) VALUES
  ('Чистый код: создание, анализ и рефакторинг.',
   'Библиотека программиста. Как писать хороший код и как преобразовать плохой код в хороший.',
   'Роберт К. Мартин',
   '978-5-496-00487-9',
   2016, FALSE );

INSERT INTO book (title, description, autor, isbn, print_year, read_already) VALUES
  ('Язык программирования Go',
   'Знакомство с языком программирования Go.',
   'Алан А. А. Донован, Брайан У. Керниган',
   '978-5-8459-2051-5',
   2016, TRUE );

INSERT INTO book (title, description, autor, isbn, print_year, read_already) VALUES
  ('ES6 и не только',
   'ES6 повествует о тонкостях языка Ecmascript 6 (ES6) - последней версии стандарта JavaScript.',
   'Кайл Симпсон',
   '978-5-8459-2051-5',
   2017, FALSE );

INSERT INTO book (title, description, autor, isbn, print_year, read_already) VALUES
  ('PHP 7',
   'Рассмотрены основы языка PHP и его рабочего окружения в Windows, Mac OS X и Linux.',
   'Дмитрий Котеров, Игорь Симдянов',
   '978-5-9775-3725-4',
   2017, TRUE );

INSERT INTO book (title, description, autor, isbn, print_year, read_already) VALUES
  ('Zend Framework 2.0. Разработка веб-приложений',
   'Zend Framework 2 представляет собой последнее обновление фреймворка Zend Framework.',
   'Кришна Шасанкар',
   '978-5-496-00837-2',
   2014, TRUE );

INSERT INTO book (title, description, autor, isbn, print_year, read_already) VALUES
  ('Разработка веб-приложений в Yii 2',
   'Yii-это высокопроизводительный фреймворк, используемый для быстрой разработки веб-приложений на РНР.',
   'Марк Сафронов',
   '978-5-97060-252-2',
   2015, TRUE );

INSERT INTO book (title, description, autor, isbn, print_year, read_already) VALUES
  ('Разработка Backbone.js приложений',
   'Backbone - это javascript-библиотека для тяжелых фронтэнд javascript-приложений, таких, например, как gmail или twitter.',
   'Эдди Османи',
   '978-5-496-00962-1',
   2014, TRUE );

INSERT INTO book (title, description, autor, isbn, print_year, read_already) VALUES
  ('Java. Том 1. Основы.',
   'Исчерпывающее руководство и практическое справочное пособие для опытных программистов, стремящихся писать на Java.',
   'Кей С. Хорстманн',
   '978-5-8459-2084-3',
   2017, TRUE );
