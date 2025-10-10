CREATE TABLE sites (
    id          serial PRIMARY KEY,
    domain_name text UNIQUE,
    login       text,
    password    text
);