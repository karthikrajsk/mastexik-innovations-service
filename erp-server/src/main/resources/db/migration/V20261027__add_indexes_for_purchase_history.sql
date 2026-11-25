-- Migration: Add index on item_id and purchase order foreign key for fast purchase history lookup
CREATE INDEX IF NOT EXISTS idx_purchase_order_item_item_id
    ON masters.purchase_order_item (item_id);

CREATE INDEX IF NOT EXISTS idx_purchase_order_item_fabric_master
    ON masters.purchase_order_item (fabric_master_id)
    WHERE fabric_master_id IS NOT NULL;

CREATE INDEX IF NOT EXISTS idx_purchase_orders_date_company
    ON masters.purchase_orders (company_id, po_date DESC, id DESC);
