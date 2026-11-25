-- Cleanup from failed V1_0 migration
-- Drop temp columns if they exist and the original columns still exist as smallint
DO $$
BEGIN
    -- Check if temp columns exist and old columns exist
    IF EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_schema = 'masters' AND table_name = 'weaving_work_order' AND column_name = 'is_approved_temp'
    ) THEN
        ALTER TABLE masters.weaving_work_order DROP COLUMN IF EXISTS is_approved_temp;
    END IF;
    
    IF EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_schema = 'masters' AND table_name = 'weaving_work_order' AND column_name = 'is_completed_temp'
    ) THEN
        ALTER TABLE masters.weaving_work_order DROP COLUMN IF EXISTS is_completed_temp;
    END IF;
END $$;

-- Now perform the actual migration if columns are still smallint
DO $$
BEGIN
    -- Check if is_approved is still smallint/numeric
    IF EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_schema = 'masters' AND table_name = 'weaving_work_order' 
        AND column_name = 'is_approved' AND data_type IN ('smallint', 'numeric', 'integer')
    ) THEN
        -- Create temp columns
        ALTER TABLE masters.weaving_work_order
            ADD COLUMN is_approved_temp BOOLEAN DEFAULT false,
            ADD COLUMN is_completed_temp BOOLEAN DEFAULT false;
        
        -- Copy and convert data
        UPDATE masters.weaving_work_order
        SET is_approved_temp = (is_approved != 0),
            is_completed_temp = (is_completed != 0);
        
        -- Drop old columns
        ALTER TABLE masters.weaving_work_order
            DROP COLUMN is_approved;
        ALTER TABLE masters.weaving_work_order
            DROP COLUMN is_completed;
        
        -- Rename temp columns
        ALTER TABLE masters.weaving_work_order
            RENAME COLUMN is_approved_temp TO is_approved;
        ALTER TABLE masters.weaving_work_order
            RENAME COLUMN is_completed_temp TO is_completed;
    END IF;
END $$;
