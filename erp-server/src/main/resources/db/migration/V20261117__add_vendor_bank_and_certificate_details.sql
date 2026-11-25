ALTER TABLE masters.vendor
    ADD COLUMN IF NOT EXISTS gst_document_media_detail_sno BIGINT,
    ADD COLUMN IF NOT EXISTS gst_document_media_url VARCHAR(2048),
    ADD COLUMN IF NOT EXISTS gst_document_file_name VARCHAR(512);

CREATE TABLE IF NOT EXISTS masters.vendor_bank_details (
    id BIGSERIAL PRIMARY KEY,
    vendor_id BIGINT NOT NULL REFERENCES masters.vendor(id) ON DELETE CASCADE,
    bank_name VARCHAR(255),
    account_no VARCHAR(255),
    ifsc_code VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS masters.vendor_certificates (
    id BIGSERIAL PRIMARY KEY,
    vendor_id BIGINT NOT NULL REFERENCES masters.vendor(id) ON DELETE CASCADE,
    certificate_no VARCHAR(255),
    expiry_date DATE,
    certificate_type VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS masters.vendor_certificate_files (
    certificate_id BIGINT NOT NULL REFERENCES masters.vendor_certificates(id) ON DELETE CASCADE,
    media_detail_sno BIGINT,
    media_url VARCHAR(2048),
    file_name VARCHAR(512)
);

CREATE INDEX IF NOT EXISTS idx_vendor_bank_details_vendor_id
    ON masters.vendor_bank_details(vendor_id);

CREATE INDEX IF NOT EXISTS idx_vendor_certificates_vendor_id
    ON masters.vendor_certificates(vendor_id);

CREATE INDEX IF NOT EXISTS idx_vendor_certificate_files_certificate_id
    ON masters.vendor_certificate_files(certificate_id);
