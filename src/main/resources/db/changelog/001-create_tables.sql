CREATE TABLE IF NOT EXISTS payments (
                          id BIGSERIAL PRIMARY KEY,
                          payment_id BIGINT NOT NULL,
                          amount BIGINT NOT NULL,
                          currency VARCHAR(20) NOT NULL,
                          payment_date TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS products (
                          id BIGSERIAL PRIMARY KEY,
                          file_id VARCHAR(255) NOT NULL,
                          name VARCHAR(255) NOT NULL,
                          description VARCHAR(255) NOT NULL,
                          price BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
                       id BIGSERIAL PRIMARY KEY,
                       chat_id VARCHAR(255) NOT NULL
);