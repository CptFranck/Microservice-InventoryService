ALTER TABLE customer
    CHANGE COLUMN name username VARCHAR(255) NOT NULL;

ALTER TABLE customer
    ADD COLUMN first_name VARCHAR(255) NOT NULL AFTER email,
    ADD COLUMN last_name VARCHAR(255) NOT NULL AFTER first_name;