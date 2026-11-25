CREATE SCHEMA IF NOT EXISTS masters;

DO $$
BEGIN
    IF to_regclass('masters.shift_master') IS NULL
       AND to_regclass('public.shift_master') IS NOT NULL THEN
        ALTER TABLE public.shift_master SET SCHEMA masters;
    END IF;

    IF to_regclass('masters.shift_timing_details') IS NULL
       AND to_regclass('public.shift_timing_details') IS NOT NULL THEN
        ALTER TABLE public.shift_timing_details SET SCHEMA masters;
    END IF;

    IF to_regclass('masters.shift_master_id_seq') IS NULL
       AND to_regclass('public.shift_master_id_seq') IS NOT NULL THEN
        ALTER SEQUENCE public.shift_master_id_seq SET SCHEMA masters;
    END IF;

    IF to_regclass('masters.shift_timing_details_id_seq') IS NULL
       AND to_regclass('public.shift_timing_details_id_seq') IS NOT NULL THEN
        ALTER SEQUENCE public.shift_timing_details_id_seq SET SCHEMA masters;
    END IF;
END
$$;

GRANT USAGE ON SCHEMA masters TO textipro_factory_admin;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON TABLE masters.shift_master, masters.shift_timing_details
    TO textipro_factory_admin;

GRANT USAGE, SELECT
    ON SEQUENCE masters.shift_master_id_seq, masters.shift_timing_details_id_seq
    TO textipro_factory_admin;
