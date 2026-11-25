-- Migration script to allow NULL values for item_code, item_category, igst and add hsn_code in masters.item_master

ALTER TABLE masters.item_master ALTER COLUMN item_code DROP NOT NULL;
ALTER TABLE masters.item_master ALTER COLUMN item_category DROP NOT NULL;
ALTER TABLE masters.item_master ALTER COLUMN igst DROP NOT NULL;

ALTER TABLE masters.item_master ADD COLUMN IF NOT EXISTS hsn_code VARCHAR(50);
