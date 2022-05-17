
--
-- Structure for table mylutece_cacheuserattribute_attribute
--

DROP TABLE IF EXISTS mylutece_cacheuserattribute_attribute;
CREATE TABLE mylutece_cacheuserattribute_attribute (
id_cache_user_attribute int AUTO_INCREMENT,
id_user varchar(50) default '' NOT NULL,
id_attribute int default '0' NOT NULL,
content varchar(50) default '',
create_date date ,
PRIMARY KEY (id_cache_user_attribute)
);
