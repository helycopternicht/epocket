CREATE TABLE users(
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE currencies(
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE transaction_types(
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE transactions(
  id SERIAL PRIMARY KEY,
  created_date TIMESTAMP NOT NULL DEFAULT now(),
  user_id BIGINT REFERENCES users(id) NOT NULL,
  currency_id BIGINT REFERENCES currencies(id) NOT NULL,
  transaction_type_id BIGINT REFERENCES transaction_types(id) NOT NULL,
  amount NUMERIC(13, 3)
);

CREATE TABLE monthly_balances(
  month DATE NOT NULL,
  user_id BIGINT REFERENCES users(id) NOT NULL ,
  currency_id BIGINT REFERENCES currencies(id) NOT NULL ,
  balance NUMERIC(13, 3)
);

