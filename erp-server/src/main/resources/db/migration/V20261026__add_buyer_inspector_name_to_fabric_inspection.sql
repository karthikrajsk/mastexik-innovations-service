ALTER TABLE masters.fabric_inspection
    ADD COLUMN IF NOT EXISTS buyer_inspector_name VARCHAR(255);
