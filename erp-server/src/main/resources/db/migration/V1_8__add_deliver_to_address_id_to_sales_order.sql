ALTER TABLE masters.sales_order
    ADD COLUMN IF NOT EXISTS deliver_to_address_id BIGINT;
