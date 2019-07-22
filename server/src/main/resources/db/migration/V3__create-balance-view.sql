CREATE VIEW balance AS
SELECT
  user_id,
  currency_id,
  sum(CASE
        WHEN transaction_type = 'DEPOSIT'
          THEN amount
        WHEN transaction_type = 'WITHDRAW'
          THEN amount * -1
    END) as balance
FROM transactions
GROUP BY user_id,
         currency_id;