
--
-- Data for table core_admin_right
--
DELETE FROM core_admin_right WHERE id_right = 'MYLUTECE_CACHEUSERATTRIBUTE_MANAGEMENT';
INSERT INTO core_admin_right (id_right,name,level_right,admin_url,description,is_updatable,plugin_name,id_feature_group,icon_url,documentation_url, id_order ) VALUES 
('MYLUTECE_CACHEUSERATTRIBUTE_MANAGEMENT','mylutece-cacheuserattribute.adminFeature.ManageCacheUserAttribute.name',1,'jsp/admin/plugins/mylutece-cacheuserattribute/ManageCacheUserAttributes.jsp','mylutece-cacheuserattribute.adminFeature.ManageCacheUserAttribute.description',0,'mylutece-cacheuserattribute',NULL,NULL,NULL,4);


--
-- Data for table core_user_right
--
DELETE FROM core_user_right WHERE id_right = 'MYLUTECE_CACHEUSERATTRIBUTE_MANAGEMENT';
INSERT INTO core_user_right (id_right,id_user) VALUES ('MYLUTECE_CACHEUSERATTRIBUTE_MANAGEMENT',1);

