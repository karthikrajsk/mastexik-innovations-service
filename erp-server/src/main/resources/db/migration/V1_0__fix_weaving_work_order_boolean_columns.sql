ALTER TABLE masters.weaving_work_order
  ADD COLUMN is_approved_temp BOOLEAN DEFAULT false,
  ADD COLUMN is_completed_temp BOOLEAN DEFAULT false;

UPDATE masters.weaving_work_order
SET is_approved_temp = (is_approved != 0),
    is_completed_temp = (is_completed != 0);

ALTER TABLE masters.weaving_work_order
  DROP COLUMN is_approved,
  DROP COLUMN is_completed;

ALTER TABLE masters.weaving_work_order
  RENAME COLUMN is_approved_temp TO is_approved,
  RENAME COLUMN is_completed_temp TO is_completed;
