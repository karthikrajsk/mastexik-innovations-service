-- Drop NOT NULL constraints from legacy redundant columns in masters.employees
ALTER TABLE masters.employees ALTER COLUMN pf DROP NOT NULL;
ALTER TABLE masters.employees ALTER COLUMN salary DROP NOT NULL;
ALTER TABLE masters.employees ALTER COLUMN tax DROP NOT NULL;
ALTER TABLE masters.employees ALTER COLUMN department DROP NOT NULL;
ALTER TABLE masters.employees ALTER COLUMN designation DROP NOT NULL;

-- Ensure mobile_number is NOT NULL (as requested)
-- First, fill any nulls with empty string just in case to avoid failure if there's existing data
UPDATE masters.employees SET mobile_number = '' WHERE mobile_number IS NULL;
ALTER TABLE masters.employees ALTER COLUMN mobile_number SET NOT NULL;

-- Also ensure employee_name is NOT NULL (though it likely already is)
ALTER TABLE masters.employees ALTER COLUMN employee_name SET NOT NULL;
