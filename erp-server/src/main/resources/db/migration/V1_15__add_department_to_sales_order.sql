ALTER TABLE masters.sales_order ADD COLUMN department VARCHAR(50);

-- Insert default domestic configs for Weaving, Brushing, Garments for existing companies
INSERT INTO masters.number_format_configurations (screen_name, tab_name, prefix, financial_year, sequence_number, separator, format_preview, active_flag, company_id)
SELECT screen_name, 'domestic-weaving', CONCAT(prefix, 'WEV'), financial_year, sequence_number, separator, CONCAT(format_preview, 'WEV'), active_flag, company_id
FROM masters.number_format_configurations
WHERE screen_name = 'Sales Order Management' AND tab_name = 'domestic'
ON CONFLICT DO NOTHING;

INSERT INTO masters.number_format_configurations (screen_name, tab_name, prefix, financial_year, sequence_number, separator, format_preview, active_flag, company_id)
SELECT screen_name, 'domestic-brushing', CONCAT(prefix, 'BRU'), financial_year, sequence_number, separator, CONCAT(format_preview, 'BRU'), active_flag, company_id
FROM masters.number_format_configurations
WHERE screen_name = 'Sales Order Management' AND tab_name = 'domestic'
ON CONFLICT DO NOTHING;

INSERT INTO masters.number_format_configurations (screen_name, tab_name, prefix, financial_year, sequence_number, separator, format_preview, active_flag, company_id)
SELECT screen_name, 'domestic-garments', CONCAT(prefix, 'GAR'), financial_year, sequence_number, separator, CONCAT(format_preview, 'GAR'), active_flag, company_id
FROM masters.number_format_configurations
WHERE screen_name = 'Sales Order Management' AND tab_name = 'domestic'
ON CONFLICT DO NOTHING;

-- Similarly for export
INSERT INTO masters.number_format_configurations (screen_name, tab_name, prefix, financial_year, sequence_number, separator, format_preview, active_flag, company_id)
SELECT screen_name, 'export-weaving', CONCAT(prefix, 'WEV'), financial_year, sequence_number, separator, CONCAT(format_preview, 'WEV'), active_flag, company_id
FROM masters.number_format_configurations
WHERE screen_name = 'Sales Order Management' AND tab_name = 'export'
ON CONFLICT DO NOTHING;

INSERT INTO masters.number_format_configurations (screen_name, tab_name, prefix, financial_year, sequence_number, separator, format_preview, active_flag, company_id)
SELECT screen_name, 'export-brushing', CONCAT(prefix, 'BRU'), financial_year, sequence_number, separator, CONCAT(format_preview, 'BRU'), active_flag, company_id
FROM masters.number_format_configurations
WHERE screen_name = 'Sales Order Management' AND tab_name = 'export'
ON CONFLICT DO NOTHING;

INSERT INTO masters.number_format_configurations (screen_name, tab_name, prefix, financial_year, sequence_number, separator, format_preview, active_flag, company_id)
SELECT screen_name, 'export-garments', CONCAT(prefix, 'GAR'), financial_year, sequence_number, separator, CONCAT(format_preview, 'GAR'), active_flag, company_id
FROM masters.number_format_configurations
WHERE screen_name = 'Sales Order Management' AND tab_name = 'export'
ON CONFLICT DO NOTHING;
