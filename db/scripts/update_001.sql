CREATE TABLE IF NOT EXISTS city (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100)
);

INSERT INTO city(name) VALUES ('Москва');
INSERT INTO city(name) VALUES ('СПб');
INSERT INTO city(name) VALUES ('Екб');

CREATE TABLE if not exists post (
   id SERIAL PRIMARY KEY,
   name TEXT,
   description TEXT,
   created TIMESTAMP,
   visible boolean,
   city_id INT REFERENCES city (id)
);

CREATE TABLE if not exists candidate (
   id SERIAL PRIMARY KEY,
   name TEXT,
   description TEXT,
   created TIMESTAMP,
   visible boolean,
   photo bytea
);

CREATE TABLE if not exists users (
    id    SERIAL PRIMARY KEY,
    name  TEXT,
    email VARCHAR,
    password TEXT,
    CONSTRAINT email_unique UNIQUE (email)
);