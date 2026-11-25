-- SQL script to update masters.item_master table to allow NULL values for item_code, item_category, and igst

ALTER TABLE masters.item_master ALTER COLUMN item_code DROP NOT NULL;
ALTER TABLE masters.item_master ALTER COLUMN item_category DROP NOT NULL;
ALTER TABLE masters.item_master ALTER COLUMN igst DROP NOT NULL;

-- Ensure hsn_code column exists
ALTER TABLE masters.item_master ADD COLUMN IF NOT EXISTS hsn_code VARCHAR(50);
