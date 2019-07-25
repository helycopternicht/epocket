CREATE INDEX ix_users_name
  ON users (name);

CREATE INDEX ix_currency_name
  ON currencies (name);

CREATE INDEX ix_transactions_user_id
  ON transactions (user_id);

CREATE INDEX ix_transactions_currency_id
  ON transactions (currency_id);