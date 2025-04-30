CREATE TABLE films (
                       id BIGSERIAL PRIMARY KEY,
                       film_id INTEGER NOT NULL,
                       film_name VARCHAR(255),
                       year INTEGER,
                       rating DOUBLE PRECISION,
                       description TEXT
);