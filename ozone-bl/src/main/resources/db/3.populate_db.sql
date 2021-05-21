insert into CITY ( ID, NAME, LAT, LNG, ZOOM_LEVEL, ACTIVE) values ( 'NA', 'Nacional', '41.2167897592938', '20.0382038596802', 7, 'Y');
insert into CITY ( ID, NAME, LAT, LNG, ZOOM_LEVEL, ACTIVE) values ( 'TR', 'Tirane', '41.3276161656775', '19.8188206199341', 13, 'Y');
insert into CITY ( ID, NAME, LAT, LNG, ZOOM_LEVEL, ACTIVE) values ( 'VL', 'Vlore', '40.4186256469254', '19.5342060569458', 11, 'N');
insert into CITY ( ID, NAME, LAT, LNG, ZOOM_LEVEL, ACTIVE) values ( 'DR', 'Durres', '41.3193655049631', '19.4615932944946', 13, 'N');
insert into CITY ( ID, NAME, LAT, LNG, ZOOM_LEVEL, ACTIVE) values ( 'SH', 'Shkoder', '42.05182133500273', '19.514078760498023', 12, 'N');
insert into CITY ( ID, NAME, LAT, LNG, ZOOM_LEVEL, ACTIVE) values ( 'BC', 'Bajram Curri', '42.34712636624339', '20.08742775952146', 10, 'N');
insert into CITY ( ID, NAME, LAT, LNG, ZOOM_LEVEL, ACTIVE) values ( 'LE', 'Lezhe', '41.77976068823365', '19.64248147045896', 11, 'N');
insert into CITY ( ID, NAME, LAT, LNG, ZOOM_LEVEL, ACTIVE) values ( 'PK', 'Peshkopi', '41.64257356945342', '20.393671656005836', 10, 'N');
insert into CITY ( ID, NAME, LAT, LNG, ZOOM_LEVEL, ACTIVE) values ( 'EL', 'Elbasan', '41.105608477825925', '20.0814196113281', 13, 'N');
insert into CITY ( ID, NAME, LAT, LNG, ZOOM_LEVEL, ACTIVE) values ( 'KJ', 'Kavaje', '41.18743386911861', '19.551672602050758', 13, 'N');
insert into CITY ( ID, NAME, LAT, LNG, ZOOM_LEVEL, ACTIVE) values ( 'LU', 'Lushje', '40.95285534220402', '19.625830316894508', 11, 'N');
insert into CITY ( ID, NAME, LAT, LNG, ZOOM_LEVEL, ACTIVE) values ( 'PG', 'Pogradec', '40.94352010576914', '20.64850297009275', 11, 'N');
insert into CITY ( ID, NAME, LAT, LNG, ZOOM_LEVEL, ACTIVE) values ( 'BR', 'Berat', '40.71187247938555', '19.947781229370094', 14, 'N');

insert into ROLE ( ID, DESCRIPTION) values ( 'ROLE_ADMIN', 'Administrator');
insert into ROLE ( ID, DESCRIPTION) values ( 'ROLE_READ', 'Read Only Role');
insert into ROLE ( ID, DESCRIPTION) values ( 'ROLE_WRITE', 'Data Entry Role');
insert into ROLE ( ID, DESCRIPTION) values ( 'ROLE_PUBLISH', 'Publisher Role');

insert into CONFIG ( CONFIG_KEY, CONFIG_VALUE) values ( 'MAX_BAD_LOGINS', 3);
insert into CONFIG ( CONFIG_KEY, CONFIG_VALUE) values ( 'PUBLICATION_DURATION', 3);
insert into CONFIG ( CONFIG_KEY, CONFIG_VALUE) values ( 'DEAL_DURATION', 3);
insert into CONFIG ( CONFIG_KEY, CONFIG_VALUE) values ( 'THEME', 'cupertino');
insert into CONFIG ( CONFIG_KEY, CONFIG_VALUE) values ( 'LANGUAGE', 'al');
insert into CONFIG ( CONFIG_KEY, CONFIG_VALUE) values ( 'BONUS_VALUE', 0);
insert into CONFIG ( CONFIG_KEY, CONFIG_VALUE) values ( 'INVITE_MESSAGE', 'Pershendetje, ....');

INSERT INTO PARTNER_CATEGORIES (ID, NAME_EN, NAME_AL, DESCRIPTION) VALUES (1, 'Restaurant & Bar', 'Restaurant & Bar', 'Restorante, Bare');
INSERT INTO PARTNER_CATEGORIES (ID, NAME_EN, NAME_AL, DESCRIPTION) VALUES (2, 'Hotel & Trip', 'Hotel & Udhetime', 'Hotele ne vende te ndryshme te Shqiperise');
INSERT INTO PARTNER_CATEGORIES (ID, NAME_EN, NAME_AL, DESCRIPTION) VALUES (3, 'Beauty Salon', 'Sallone Bukurie', 'Sallone bukurie, prerje flokesh, lyerje, manikyre pedikyre');
INSERT INTO PARTNER_CATEGORIES (ID, NAME_EN, NAME_AL, DESCRIPTION) VALUES (4, 'Body Care', 'Trajtim trupi', 'Masazhe, sherbime per trupin, anticelulite, depilime, etj');
INSERT INTO PARTNER_CATEGORIES (ID, NAME_EN, NAME_AL, DESCRIPTION) VALUES (5, 'Medical', 'Mjeksore', 'Sherbime shendetesore, dieta, ekografi, vizita okulistike, etj.');
INSERT INTO PARTNER_CATEGORIES (ID, NAME_EN, NAME_AL, DESCRIPTION) VALUES (6, 'Fitness & Dancing', 'Palestra & Kercime', 'Palestra body building, kurse aerobie, etj');
INSERT INTO PARTNER_CATEGORIES (ID, NAME_EN, NAME_AL, DESCRIPTION) VALUES (7, 'Car services ', 'Sherbime Makine', 'Lavazho makinash(larje tapicerie), Oficina mekanike, servise te ndryshem');
INSERT INTO PARTNER_CATEGORIES (ID, NAME_EN, NAME_AL, DESCRIPTION) VALUES (8, 'Learning ', 'Studime', 'Shkolla/Universitete private, Qendra gjuheve te huaja, kurse Italishte, Anglishte, Kurse makine, etj');
INSERT INTO PARTNER_CATEGORIES (ID, NAME_EN, NAME_AL, DESCRIPTION) VALUES (9, 'Utility services', 'Sherbime shtepijake', 'Sherbime instaurimi, elektriciste, hidraulike');
INSERT INTO PARTNER_CATEGORIES (ID, NAME_EN, NAME_AL, DESCRIPTION) VALUES (10, 'Games & Entertainment', 'Lojra & Argetime', 'Go kart, hedhje me parashyte, ekskursione malore, etj');
INSERT INTO PARTNER_CATEGORIES (ID, NAME_EN, NAME_AL, DESCRIPTION) VALUES (11, 'Others', 'Te tjera', 'Cdo lloj biznesi qe nuk klasifikohet ne nje nga kategorite e lartepermendura.');

INSERT INTO COUPON_STATUS (ID, DESCRIPTION) VALUES ('N', 'Not used');
INSERT INTO COUPON_STATUS (ID, DESCRIPTION) VALUES ('U', 'Used');
INSERT INTO COUPON_STATUS (ID, DESCRIPTION) VALUES ('E', 'Expired');
INSERT INTO COUPON_STATUS (ID, DESCRIPTION) VALUES ('R', 'Returned Back');

INSERT INTO USERS (USERNAME, NAME, SURNAME, PWD) values ('ermal', 'Ermal', 'Aliraj', '8148ea3810544592fc8ac75526f66f5401d8e5f0');
INSERT INTO USERS (USERNAME, NAME, SURNAME, PWD) values ('admin', 'admin', 'admin', 'd033e22ae348aeb5660fc2140aec35850c4da997');
insert into USER_ROLE ( USERNAME, ROLEID) values ( 'ermal', 'ROLE_ADMIN');
insert into USER_ROLE ( USERNAME, ROLEID) values ( 'admin', 'ROLE_ADMIN');

INSERT INTO partner (ID,NAME,CITY_ID,ADDRESS,TEL,CEL,EMAIL,WEB_SITE,LAT,LNG,ZOOM_LEVEL,CATEGORY_ID) VALUES (1,'OZone','TR','Rr. Gjon Muzaka, Nr.1','','+355 67 33 74 970','info@ozone.al','www.ozone.al',41.329142906798445,19.80983521973417,15,11);
INSERT INTO deal (ID,TITLE,PRICE,FULL_PRICE,NR_MIN_CUSTOMERS,NR_MAX_CUSTOMERS,DISCOUNT_DURATION,PARTNER_ID,CLIENT_FULLNAME,CLIENT_CEL,BROKER_FULLNAME,BROKER_CEL,PUBLISHED,MAIN_IMG,SYNTHESIS,CONDITIONS,DESCRIPTION,APPROVED_PUBLISHING,APPROVED_USER,APPROVED_DATE,INSERTED_DATE,LAST_UPDATE,LAST_UPDATE_USER) VALUES (1,'OZone, portali juaj per te zbuluar qytetin nga 50% deri ne 90% me lire. Regjistrohuni tani!!!',10,100,0,0,3,1,'OZone','+355 67 33 74 970','Ermal','+355 67 33 74 970','Y','defaultDealImg.png','<div><font face="Arial, Verdana" size="2">Me OZone do te perfitoni cdo sherbim, si restorante, udhetime, argetime, masazhe, estetike e parukeri e shume e shume te tjera, me ulje marramendese. Zbritjet fillojne nga 50% dhe shkojne deri ne 90%.</font></div><div><font face="Arial, Verdana" size="2"><br/></font></div><div><font face="Arial, Verdana" size="2">Nxitohu te regjistrohesh dhe te perfitosh nga ofertat e cmendura qe ne do te propozojme vetem per te regjistruarit tane.</font></div><div><font face="Arial, Verdana" size="2"><br/></font></div><div><font face="Arial, Verdana" size="2">Ftoni miqte tuaj te behen pjese e kesaj menyre te re te eksplorimit te qytetit, pasi gjerat e bukura, nuk behen kurre vetem.</font></div>','<div><font face="Arial, Verdana" size="2">Regjistrohuni per te perfituar zbritjet me te cmendrua qe do te gjeni ndonjehere.</font></div><div><font face="Arial, Verdana" size="2"><br/></font></div><div><font face="Arial, Verdana" size="2">Ftoni miqte tuaj te regjistroheni pasi te jeni regjistruar vete dhe keshtu do te perfitono nje bonus prej 300 Lekesh.</font></div><div><font face="Arial, Verdana" size="2"><br/></font></div><div><font face="Arial, Verdana" size="2">Blini oferten qe ju interesojn me ulje cmimi duke filluar nga 50% deri ne 90% dhe shijoni qytetin tuaj ashtu si kurre me pare.</font></div>',NULL,'Y',NULL,NULL,'2012-06-02 14:52:06','2012-06-02 15:14:57','ermal');
INSERT INTO publication (ID,START_DATE,END_DATE,TOT_PURCHASE,DEAL_ID,CONFIRMED,STATUS,ORDER_NR,CITY_ID,COUPONS_PREPARED) VALUES (8,'2012-06-01','2012-06-02',0,1,'N','W',0,'TR','N');

commit ;