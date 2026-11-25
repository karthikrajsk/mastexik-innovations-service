CREATE SCHEMA IF NOT EXISTS masters;

CREATE TABLE IF NOT EXISTS masters.shift_master (
    id BIGSERIAL PRIMARY KEY,
    shift_code VARCHAR(50) NOT NULL UNIQUE,
    shift_name VARCHAR(150) NOT NULL,
    shift_type VARCHAR(20) NOT NULL DEFAULT 'Fixed',
    description VARCHAR(500),
    active_flag BOOLEAN NOT NULL DEFAULT TRUE,
    total_working_hours NUMERIC(6, 2) DEFAULT 0.00,
    company_id BIGINT,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS masters.shift_timing_details (
    id BIGSERIAL PRIMARY KEY,
    shift_master_id BIGINT NOT NULL REFERENCES masters.shift_master(id) ON DELETE CASCADE,
    slot_number INTEGER NOT NULL DEFAULT 1,
    shift_in_time VARCHAR(10) NOT NULL,
    shift_out_time VARCHAR(10) NOT NULL,
    is_next_day BOOLEAN NOT NULL DEFAULT FALSE,
    break_duration INTEGER NOT NULL DEFAULT 0,
    break_start_time VARCHAR(10),
    break_end_time VARCHAR(10),
    total_break_hours NUMERIC(6, 2) NOT NULL DEFAULT 0.00,
    working_hours NUMERIC(6, 2) NOT NULL DEFAULT 0.00
);

CREATE INDEX IF NOT EXISTS idx_shift_timing_master_id
    ON masters.shift_timing_details(shift_master_id);
