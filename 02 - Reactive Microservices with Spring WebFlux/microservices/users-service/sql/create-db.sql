DROP DATABASE IF EXISTS webservices;
CREATE DATABASE webservices;
\c webservices;

CREATE TABLE IF NOT EXISTS users(
  id bigserial primary key,
  name varchar(50),
  balance numeric
);

CREATE TABLE IF NOT EXISTS transactions(
    id bigserial primary key,
    user_id bigint,
    amount numeric,
    transaction_date timestamp,
    CONSTRAINT fk_user_id FOREIGN KEY(user_id) REFERENCES users(id)
                         ON DELETE CASCADE
);