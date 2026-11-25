ALTER TABLE masters.shift_timing_details
    ADD COLUMN IF NOT EXISTS break_start_time VARCHAR(10),
    ADD COLUMN IF NOT EXISTS break_end_time VARCHAR(10),
    ADD COLUMN IF NOT EXISTS total_break_hours NUMERIC(6, 2) NOT NULL DEFAULT 0.00;
