ALTER TABLE masters.purchase_orders
    ADD COLUMN IF NOT EXISTS fabric_master_id BIGINT;
