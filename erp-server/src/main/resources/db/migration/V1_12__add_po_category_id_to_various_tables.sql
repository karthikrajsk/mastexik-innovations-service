ALTER TABLE masters.purchase_orders ADD COLUMN po_category_id BIGINT;
ALTER TABLE masters.yarn_inward_stock_details ADD COLUMN po_category_id BIGINT;
ALTER TABLE masters.piece_entry ADD COLUMN po_category_id BIGINT;
ALTER TABLE masters.item_inward_stock_details ADD COLUMN po_category_id BIGINT;
