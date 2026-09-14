-- 1. Get all invoice ids with the customers first name, last name, and the invoice total

SELECT i.invoice_id, c.first_name, c.last_name, i.total
FROM INVOICE i
LEFT JOIN CUSTOMER c
    ON i.customer_id = c.customer_id;

-- 2. Print the invoice id, customer's first name, and invoice total. But only if the invoice is over $30.

SELECT i.invoice_id, c.first_name, i.total
FROM INVOICE i
LEFT JOIN CUSTOMER c
    ON i.customer_id = c.customer_id
WHERE i.total > 30;

-- 3. Get all the invoices for USA customers in the last 6 months. Use a CTE.

with usa_customers AS (
    SELECT customer_id
    FROM CUSTOMER
    WHERE country = 'USA'
)
SELECT i.*
FROM INVOICE i
INNER JOIN usa_customers
    ON i.customer_id = usa_customers.customer_id
WHERE i.invoice_date >= NOW() - INTERVAL '6 months';

-- Create a new table called record_logs
--  Fields: log_id, record_id, field_changed, last_update, old_value, new_value

-- Create a trigger that tracks changes to customer records and logs the changes in our new table
