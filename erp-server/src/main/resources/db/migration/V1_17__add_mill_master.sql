CREATE TABLE IF NOT EXISTS masters.mill (
    id BIGSERIAL PRIMARY KEY,
    mill_name VARCHAR(100) NOT NULL,
    mill_type_id BIGINT,
    contact_person VARCHAR(100),
    contact_number VARCHAR(20),
    active_flag BOOLEAN NOT NULL DEFAULT TRUE,
    address_id BIGINT,
    CONSTRAINT fk_mill_address FOREIGN KEY (address_id) REFERENCES masters.address(id) ON DELETE SET NULL,
    CONSTRAINT fk_mill_type FOREIGN KEY (mill_type_id) REFERENCES masters.sub_category(id) ON DELETE SET NULL
);

-- Register "Mill Master" in the menu master table
INSERT INTO masters.menu_masters (screen_name, menu_url) 
VALUES ('Mill Master', '/masters/mill')
ON CONFLICT DO NOTHING;
