# Users schema

# --- !Ups
CREATE TABLE User (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    name varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    telefone varchar(255) NOT NULL,
    created DATE,
    modified DATE,
    PRIMARY KEY (id),
    CONSTRAINT UC_USER UNIQUE (email)
);

# --- !Downs

DROP TABLE User;