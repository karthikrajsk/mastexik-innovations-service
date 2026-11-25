-- Migration script for Employee Property Assignment
CREATE TABLE IF NOT EXISTS masters.employee_property_assignment (
    id BIGSERIAL PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    employee_code VARCHAR(50),
    employee_name VARCHAR(150),
    department VARCHAR(100),
    designation VARCHAR(100),
    item_id BIGINT NULL,
    item_code VARCHAR(50),
    item_name VARCHAR(150) NOT NULL,
    property_category VARCHAR(100),
    serial_number VARCHAR(100),
    quantity DECIMAL(10, 2) NOT NULL DEFAULT 1.00,
    uom VARCHAR(50) DEFAULT 'NOS',
    assigned_date DATE NOT NULL,
    expected_return_date DATE NULL,
    condition_at_issue VARCHAR(50) DEFAULT 'GOOD',
    assigned_by VARCHAR(100),
    status VARCHAR(50) NOT NULL DEFAULT 'ASSIGNED', -- 'ASSIGNED', 'RETURNED', 'DAMAGED', 'LOST'
    returned_date DATE NULL,
    condition_at_return VARCHAR(50) NULL,
    received_by VARCHAR(100) NULL,
    damage_deduction_amount DECIMAL(10, 2) NULL DEFAULT 0.00,
    remarks TEXT NULL,
    company_id BIGINT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_emp_prop_employee_id ON masters.employee_property_assignment(employee_id);
CREATE INDEX IF NOT EXISTS idx_emp_prop_item_id ON masters.employee_property_assignment(item_id);
CREATE INDEX IF NOT EXISTS idx_emp_prop_status ON masters.employee_property_assignment(status);
CREATE INDEX IF NOT EXISTS idx_emp_prop_company_id ON masters.employee_property_assignment(company_id);
