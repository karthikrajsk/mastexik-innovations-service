-- Create employment_details table
CREATE TABLE IF NOT EXISTS masters.employment_details (
    id BIGSERIAL PRIMARY KEY,
    department VARCHAR(255),
    designation VARCHAR(255),
    joining_date DATE,
    employee_type VARCHAR(255),
    status VARCHAR(255)
);

-- Create salary_details table
CREATE TABLE IF NOT EXISTS masters.salary_details (
    id BIGSERIAL PRIMARY KEY,
    salary_structure VARCHAR(255),
    salary_type VARCHAR(255),
    basic_salary DECIMAL(19, 2),
    hra DECIMAL(19, 2),
    conveyance_allowance DECIMAL(19, 2),
    medical_allowance DECIMAL(19, 2),
    special_allowance DECIMAL(19, 2),
    incentive DECIMAL(19, 2)
);

-- Create shift_details table
CREATE TABLE IF NOT EXISTS masters.shift_details (
    id BIGSERIAL PRIMARY KEY,
    shift_name VARCHAR(255),
    shift_code VARCHAR(255),
    shift_start_time TIME,
    shift_end_time TIME,
    break_start_time TIME,
    break_end_time TIME,
    total_working_hours DOUBLE PRECISION,
    weekly_off_day VARCHAR(255),
    overtime_allowed BOOLEAN,
    late_entry_allowed_minutes INTEGER,
    shift_effective_from DATE
);

-- Create bank_details table
CREATE TABLE IF NOT EXISTS masters.bank_details (
    id BIGSERIAL PRIMARY KEY,
    bank_name VARCHAR(255),
    account_number VARCHAR(255),
    ifsc_code VARCHAR(255),
    branch_name VARCHAR(255)
);

-- Add missing columns to masters.employees table
ALTER TABLE masters.employees 
    ADD COLUMN IF NOT EXISTS employee_code VARCHAR(255) UNIQUE,
    ADD COLUMN IF NOT EXISTS gender VARCHAR(255),
    ADD COLUMN IF NOT EXISTS dob DATE,
    ADD COLUMN IF NOT EXISTS father_husband_name VARCHAR(255),
    ADD COLUMN IF NOT EXISTS mobile_number VARCHAR(255),
    ADD COLUMN IF NOT EXISTS email VARCHAR(255),
    ADD COLUMN IF NOT EXISTS address VARCHAR(255),
    ADD COLUMN IF NOT EXISTS aadhaar_number VARCHAR(255),
    ADD COLUMN IF NOT EXISTS pan_number VARCHAR(255),
    ADD COLUMN IF NOT EXISTS marital_status VARCHAR(255),
    ADD COLUMN IF NOT EXISTS uan_number VARCHAR(255),
    ADD COLUMN IF NOT EXISTS pf_number VARCHAR(255),
    ADD COLUMN IF NOT EXISTS pf_joining_date DATE,
    ADD COLUMN IF NOT EXISTS esi_number VARCHAR(255),
    ADD COLUMN IF NOT EXISTS pf_applicable BOOLEAN DEFAULT false,
    ADD COLUMN IF NOT EXISTS esi_applicable BOOLEAN DEFAULT false,
    ADD COLUMN IF NOT EXISTS employment_details_id BIGINT REFERENCES masters.employment_details(id),
    ADD COLUMN IF NOT EXISTS salary_details_id BIGINT REFERENCES masters.salary_details(id),
    ADD COLUMN IF NOT EXISTS shift_details_id BIGINT REFERENCES masters.shift_details(id),
    ADD COLUMN IF NOT EXISTS bank_details_id BIGINT REFERENCES masters.bank_details(id);

-- Make mobile_number NOT NULL if needed (per entity definition)
-- However, we'll keep it nullable for existing data if any, then update later if required.
-- ALTER TABLE masters.employees ALTER COLUMN mobile_number SET NOT NULL;
