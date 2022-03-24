INSERT INTO prd_product_type(id, type, vat_rate) SELECT nextval('prd_product_type_id_seq'), 'FOOD', 1 WHERE NOT EXISTS (SELECT * FROM prd_product_type WHERE type = 'FOOD');
INSERT INTO prd_product_type(id, type, vat_rate) SELECT nextval('prd_product_type_id_seq'), 'STATIONERY', 8 WHERE NOT EXISTS (SELECT * FROM prd_product_type WHERE type = 'STATIONERY');
INSERT INTO prd_product_type(id, type, vat_rate) SELECT nextval('prd_product_type_id_seq'), 'CLOTHING', 8 WHERE NOT EXISTS (SELECT * FROM prd_product_type WHERE type = 'CLOTHING');
INSERT INTO prd_product_type(id, type, vat_rate) SELECT nextval('prd_product_type_id_seq'), 'TECHNOLOGY', 18 WHERE NOT EXISTS (SELECT * FROM prd_product_type WHERE type = 'TECHNOLOGY');
INSERT INTO prd_product_type(id, type, vat_rate) SELECT nextval('prd_product_type_id_seq'), 'CLEANING', 18 WHERE NOT EXISTS (SELECT * FROM prd_product_type WHERE type = 'CLEANING');
INSERT INTO prd_product_type(id, type, vat_rate) SELECT nextval('prd_product_type_id_seq'), 'OTHERS', 18 WHERE NOT EXISTS (SELECT * FROM prd_product_type WHERE type = 'OTHERS');
