ALTER TABLE masters.woven_fabric_master
    ALTER COLUMN fabric_category_id DROP NOT NULL,
    ALTER COLUMN fabric_name DROP NOT NULL;
