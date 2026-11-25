ALTER TABLE masters.agency_contract
    ADD COLUMN IF NOT EXISTS piece_length NUMERIC(18, 4),
    ADD COLUMN IF NOT EXISTS packing VARCHAR(20),
    ADD COLUMN IF NOT EXISTS is_approved SMALLINT NOT NULL DEFAULT 0;

INSERT INTO masters.codes_details (
    hdr_id,
    code,
    value,
    sort_order,
    is_active,
    created_at,
    updated_at
)
SELECT
    header.id,
    'AGENCY_CONTRACT',
    'Agency Contract',
    COALESCE((
        SELECT MAX(detail.sort_order) + 1
        FROM masters.codes_details detail
        WHERE detail.hdr_id = header.id
    ), 1),
    TRUE,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
FROM masters.codes_hdr header
WHERE header.code_group = 'Complete Status'
  AND NOT EXISTS (
      SELECT 1
      FROM masters.codes_details detail
      WHERE detail.hdr_id = header.id
        AND detail.code = 'AGENCY_CONTRACT'
  );
