CREATE TABLE IF NOT EXISTS masters.purchase_indent (
    id BIGSERIAL PRIMARY KEY,
    indent_no VARCHAR(100) NOT NULL,
    indent_date DATE NOT NULL,
    department VARCHAR(150) NOT NULL,
    requested_by VARCHAR(150) NOT NULL,
    required_date DATE NOT NULL,
    priority VARCHAR(40) NOT NULL DEFAULT 'Normal',
    remarks TEXT,
    status VARCHAR(40) NOT NULL DEFAULT 'Draft',
    company_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_purchase_indent_company_number UNIQUE (company_id, indent_no)
);

CREATE INDEX IF NOT EXISTS idx_purchase_indent_company_created
    ON masters.purchase_indent (company_id, created_at DESC);

CREATE TABLE IF NOT EXISTS masters.purchase_indent_item (
    id BIGSERIAL PRIMARY KEY,
    purchase_indent_id BIGINT NOT NULL
        REFERENCES masters.purchase_indent(id) ON DELETE CASCADE,
    item_id BIGINT NOT NULL,
    item_name VARCHAR(250) NOT NULL,
    specification TEXT,
    quantity NUMERIC(18, 4) NOT NULL CHECK (quantity > 0),
    uom_id BIGINT NOT NULL,
    uom_name VARCHAR(100) NOT NULL,
    required_date DATE NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_purchase_indent_item_parent
    ON masters.purchase_indent_item (purchase_indent_id, id);
