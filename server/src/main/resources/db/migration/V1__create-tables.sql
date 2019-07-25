CREATE TABLE currencies(
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE transactions(
  id SERIAL PRIMARY KEY,
  created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
  user_id BIGINT NOT NULL,
  currency_id BIGINT REFERENCES currencies(id) NOT NULL,
  transaction_type VARCHAR(20) NOT NULL,
  amount NUMERIC(13, 3)
);