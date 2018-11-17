DROP TABLE IF EXISTS book;
CREATE TABLE book
(
  id int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  title varchar(100),
  description text,
  autor varchar(100),
  isbn varchar(20),
  print_year int(11),
  read_already tinyint(4),
  image_str varchar(255),
  image_data mediumblob
)
  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE UNIQUE INDEX id_UNIQUE ON book (id);