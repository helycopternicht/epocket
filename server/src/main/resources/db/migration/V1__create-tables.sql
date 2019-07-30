CREATE TABLE currencies
(
  id   BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  name VARCHAR(255) NOT NULL
);
CREATE TABLE transactions
(
  id               BIGINT AUTO_INCREMENT PRIMARY KEY,
  created_date     TIMESTAMP   NOT NULL DEFAULT now(),
  user_id          BIGINT      NOT NULL,
  currency_id      BIGINT REFERENCES currencies (id),
  transaction_type VARCHAR(20) NOT NULL,
  amount           NUMERIC(13, 3)
);