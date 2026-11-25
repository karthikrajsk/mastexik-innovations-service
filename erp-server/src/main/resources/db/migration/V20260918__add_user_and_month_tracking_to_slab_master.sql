-- Migration script for adding month and user tracking to Attendance Incentive Slab Master
ALTER TABLE masters.attendance_incentive_slab_master
ADD COLUMN IF NOT EXISTS month VARCHAR(10) NULL,
ADD COLUMN IF NOT EXISTS created_by VARCHAR(100) NULL,
ADD COLUMN IF NOT EXISTS updated_by VARCHAR(100) NULL;
