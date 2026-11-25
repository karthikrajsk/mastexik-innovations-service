-- Migration script for Shift Master and Shift Timing Details child table

CREATE TABLE IF NOT EXISTS shift_master (
    id BIGSERIAL PRIMARY KEY,
    shift_code VARCHAR(30) UNIQUE,
    shift_name VARCHAR(150) NOT NULL,
    shift_type VARCHAR(20) NOT NULL DEFAULT 'Fixed',
    description VARCHAR(500),
    active_flag BOOLEAN NOT NULL DEFAULT TRUE,
    total_working_hours NUMERIC(6, 2) DEFAULT 0.00,
    company_id BIGINT,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS shift_timing_details (
    id BIGSERIAL PRIMARY KEY,
    shift_master_id BIGINT NOT NULL,
    slot_number INT NOT NULL DEFAULT 1,
    shift_in_time VARCHAR(10) NOT NULL,
    shift_out_time VARCHAR(10) NOT NULL,
    is_next_day BOOLEAN NOT NULL DEFAULT FALSE,
    break_duration INT NOT NULL DEFAULT 0,
    break_start_time VARCHAR(10),
    break_end_time VARCHAR(10),
    total_break_hours NUMERIC(6, 2) NOT NULL DEFAULT 0.00,
    working_hours NUMERIC(6, 2) NOT NULL DEFAULT 0.00,
    CONSTRAINT fk_shift_timing_master FOREIGN KEY (shift_master_id) 
        REFERENCES shift_master(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_shift_master_code ON shift_master(shift_code);
CREATE INDEX IF NOT EXISTS idx_shift_timing_master_id ON shift_timing_details(shift_master_id);

-- Seed initial standard shifts
INSERT INTO shift_master (id, shift_code, shift_name, shift_type, description, active_flag, total_working_hours)
VALUES 
(1, 'SH-MORN-A', 'Morning Weaving Shift A', 'Rotation', 'Primary morning production shift for Looms 01 - 24', true, 7.50),
(2, 'SH-AFTN-B', 'Afternoon Weaving Shift B', 'Rotation', 'Afternoon production and beam inspection shift', true, 7.50),
(3, 'SH-NGHT-C', 'Night Weaving Shift C', 'Rotation', 'Overnight continuous spinning and weaving operation', true, 7.50),
(4, 'GEN-ADMIN', 'General Administrative & QC Shift', 'Fixed', 'Standard office, quality lab, and warehouse maintenance hours', true, 7.50)
ON CONFLICT (shift_code) DO NOTHING;

INSERT INTO shift_timing_details (shift_master_id, slot_number, shift_in_time, shift_out_time, is_next_day, break_duration, working_hours)
VALUES
(1, 1, '06:00', '14:00', false, 30, 7.50),
(2, 1, '14:00', '22:00', false, 30, 7.50),
(3, 1, '22:00', '06:00', true, 30, 7.50),
(4, 1, '09:00', '17:30', false, 60, 7.50)
ON CONFLICT DO NOTHING;
