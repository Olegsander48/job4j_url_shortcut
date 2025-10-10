CREATE TABLE links (
    id      serial PRIMARY KEY,
    url     text UNIQUE,
    code    text,
    total   int,
    site_id int REFERENCES sites (id)
);