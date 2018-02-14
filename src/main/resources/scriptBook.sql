CREATE TABLE book
(
  id           INT AUTO_INCREMENT
    PRIMARY KEY,
  title        VARCHAR(100) NULL,
  description  VARCHAR(255) NULL,
  autor        VARCHAR(100) NULL,
  isbn         VARCHAR(20)  NULL,
  print_year   INT          NULL,
  read_already TINYINT      NULL,
  image_str    VARCHAR(255) NULL,
  CONSTRAINT id_UNIQUE
  UNIQUE (id)
)
  ENGINE = InnoDB;


