ALTER TABLE `order` DROP FOREIGN KEY fk_order_customer;

ALTER TABLE customer
    MODIFY COLUMN id VARCHAR(36) PRIMARY KEY;

ALTER TABLE `order`
    MODIFY COLUMN customer_id VARCHAR(36);

ALTER TABLE `order`
    ADD CONSTRAINT fk_order_customer
        FOREIGN KEY (customer_id) REFERENCES customer(id) ON DELETE SET NULL;