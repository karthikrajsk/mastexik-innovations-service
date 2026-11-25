GRANT USAGE ON SCHEMA masters TO textipro_factory_admin;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON TABLE masters.shift_master, masters.shift_timing_details
    TO textipro_factory_admin;

GRANT USAGE, SELECT
    ON SEQUENCE masters.shift_master_id_seq, masters.shift_timing_details_id_seq
    TO textipro_factory_admin;
