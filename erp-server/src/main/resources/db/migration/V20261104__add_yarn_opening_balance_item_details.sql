ALTER TABLE masters.yarn_item
    ADD COLUMN IF NOT EXISTS yarn_type_id BIGINT,
    ADD COLUMN IF NOT EXISTS cones_per_bag NUMERIC(38, 3),
    ADD COLUMN IF NOT EXISTS cones_weight NUMERIC(38, 3);
