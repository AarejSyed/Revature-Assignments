-- Get all fields and records from customer

SELECT *
FROM CUSTOMER;

-- Get all fields from customer, but only if they are from Arizona

SELECT *
FROM CUSTOMER
WHERE state = 'AZ';

-- Get all invoices older than 6 months

SELECT *
FROM INVOICE
WHERE invoice_date < NOW() - INTERVAL '6 months';

-- Update all customer phone numbers to NULL if they don’t follow this format: ‘+1 555 555-5555’

UPDATE CUSTOMER
SET phone = NULL
WHERE phone !~* '^\+1 \d{3} \d{3}-\d{4}$';

-- Get all tracks that are longer than 180000 milliseconds

SELECT *
FROM TRACK
WHERE milliseconds > 180000;

-- Update all customers not in the USA so that their country=USA and address, city, & state are NULL

UPDATE CUSTOMER
SET country = 'USA',
    address = NULL,
    city = NULL,
    state = NULL
WHERE country IS DISTINCT FROM 'USA';

-- Given a customer_id, return their total spending across all invoices using a function

CREATE OR REPLACE FUNCTION get_total_spending(p_customer_id INT)
RETURNS NUMERIC (10, 2) AS $$
BEGIN
    RETURN (
        SELECT COALESCE(SUM(total), 0)
        FROM INVOICE
        WHERE customer_id = p_customer_id
    );
END;
$$ LANGUAGE plpgsql;

-- Given an employee_id + new_manager_id, create a stored procedure to update an Employee’s ReportsTo field.
--  - Prevent an employee reporting to themselves, reporting to a non-existence employee, or creating a circular management relationship

CREATE OR REPLACE FUNCTION creates_circular_management_relationship(p_employee_id INT, new_manager_id INT)
RETURNS BOOLEAN AS $$
BEGIN
    RETURN EXISTS (
        WITH RECURSIVE management_chain AS (
            SELECT employee_id, reports_to
            FROM EMPLOYEE
            WHERE employee_id = new_manager_id

            UNION ALL

            SELECT E.employee_id, E.reports_to
            FROM EMPLOYEE E
            INNER JOIN management_chain mc ON E.employee_id = mc.reports_to
        )

        SELECT 1
        FROM management_chain
        WHERE employee_id = p_employee_id
    );
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE set_employee_reports_to(p_employee_id INT, new_manager_id INT)
LANGUAGE plpgsql
AS $$
BEGIN
    IF p_employee_id = new_manager_id OR
        NOT EXISTS (SELECT 1 FROM EMPLOYEE WHERE employee_id = new_manager_id) OR
        creates_circular_management_relationship(p_employee_id, new_manager_id)
    THEN
        RETURN;
    END IF;

    UPDATE EMPLOYEE
    SET reports_to = new_manager_id
    WHERE employee_id = p_employee_id;
END;
$$;

-- Create a new schema: pets
--  - Create two related tables: Customer + Pets
--  - Demonstrate populating records into these tables

CREATE SCHEMA IF NOT EXISTS pets;

CREATE TABLE IF NOT EXISTS pets.customer (
    customer_id INT PRIMARY KEY,
    email TEXT NOT NULL UNIQUE,
    given_name TEXT,
    surname TEXT
);

CREATE TABLE IF NOT EXISTS pets.pet (
    pet_id INT PRIMARY KEY,
    owner_id INT,
    name TEXT,
    species TEXT,
    age INT,
    FOREIGN KEY (owner_id)
        REFERENCES pets.CUSTOMER(customer_id)
        ON DELETE SET NULL
);

INSERT INTO pets.CUSTOMER(customer_id, email, given_name, surname)
VALUES
    (1, 'sipho.mchunu@example.za', 'Sipho', 'Mchunu'),
    (2, 'aarej.syed@example.com', 'Aarej', 'Syed'),
    (3, 'anonymous.cat.owner@example.pk', NULL, NULL),
    (4, 'blackbeard@example.biz', 'Edward', 'Teach');

INSERT INTO pets.PET(pet_id, owner_id, name, species, age)
VALUES
    (1, 1, 'Juluka', 'Bos taurus', NULL),
    (2, 2, 'Alexander', 'Diplodocus carnegii', 153000000),
    (3, 3, 'Sir Fuzzyface', 'Felis catus', 8),
    (4, 4, 'Polly', 'Ara ararauna', 15),
    (5, NULL, 'The Ancient One', NULL, NULL);
