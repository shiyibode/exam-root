DROP TABLE IF EXISTS t_user;
CREATE TABLE t_user (
  id BIGINT AUTO_INCREMENT,
  user_name VARCHAR(10),
  password VARCHAR(128),
  name VARCHAR(30),
  age INT,
  PRIMARY KEY (id)
) CHARSET = utf8 ENGINE = InnoDB;
CREATE UNIQUE INDEX idx_t_user_user_name ON t_user (user_name);