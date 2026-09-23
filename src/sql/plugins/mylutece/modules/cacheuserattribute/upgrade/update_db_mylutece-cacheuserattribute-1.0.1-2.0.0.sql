-- liquibase formatted sql
-- changeset mylutece-cacheuserattribute:update_db_mylutece-cacheuserattribute-1.0.1-2.0.0.sql
-- preconditions onFail:MARK_RAN onError:WARN
UPDATE core_admin_right SET icon_url = 'ti ti-database' WHERE id_right = 'MYLUTECE_CACHEUSERATTRIBUTE_MANAGEMENT';
