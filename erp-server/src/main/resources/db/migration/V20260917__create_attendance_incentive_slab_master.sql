-- Migration script for Attendance Incentive Slab Master
CREATE TABLE IF NOT EXISTS masters.attendance_incentive_slab_master (
    id BIGSERIAL PRIMARY KEY,
    working_hours DECIMAL(4, 2) NOT NULL DEFAULT 12.00,
    month_type VARCHAR(20) NOT NULL, -- 'FEB', 'DAYS_30', 'DAYS_31'
    min_days DECIMAL(5, 2) NOT NULL,
    max_days DECIMAL(5, 2) NULL, -- NULL means "and above"
    revised_daily_salary DECIMAL(10, 2) DEFAULT 0.00,
    incentive_amount DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    effective_from DATE NULL,
    effective_to DATE NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_att_slab_hours_month ON masters.attendance_incentive_slab_master(working_hours, month_type, is_active);
