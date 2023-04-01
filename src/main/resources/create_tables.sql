CREATE TABLE IF NOT EXISTS users
(
    id             SERIAL PRIMARY KEY,
    username       VARCHAR(256) NOT NULL UNIQUE,
    );
