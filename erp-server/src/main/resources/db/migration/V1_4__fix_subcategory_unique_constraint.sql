ALTER TABLE masters.sub_category
    DROP CONSTRAINT IF EXISTS sub_category_sub_category_name_key;

ALTER TABLE masters.sub_category
    ADD CONSTRAINT sub_category_category_sno_sub_category_name_unique
    UNIQUE (category_sno, sub_category_name);
