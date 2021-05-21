
CREATE SCHEMA IF NOT EXISTS SYSIBM;
CREATE TABLE IF NOT EXISTS SYSIBM.SYSDUMMY1();

CREATE SEQUENCE seq_deal START WITH 1 INCREMENT BY 1 NO CYCLE CACHE 50;
CREATE SEQUENCE seq_deal_feedback START WITH 1 INCREMENT BY 1 NO CYCLE CACHE 50;
CREATE SEQUENCE seq_credit START WITH 1 INCREMENT BY 1 NO CYCLE CACHE 50;
CREATE SEQUENCE seq_purchase START WITH 1 INCREMENT BY 1 NO CYCLE CACHE 50;
CREATE SEQUENCE seq_customer START WITH 1 INCREMENT BY 1 NO CYCLE CACHE 50;
CREATE SEQUENCE seq_order START WITH 1 INCREMENT BY 1 NO CYCLE CACHE 50;
CREATE SEQUENCE seq_partner START WITH 1 INCREMENT BY 1 NO CYCLE CACHE 50;
CREATE SEQUENCE seq_payment START WITH 1 INCREMENT BY 1 NO CYCLE CACHE 50;
CREATE SEQUENCE seq_publication START WITH 1 INCREMENT BY 1 NO CYCLE CACHE 50;

CREATE TABLE USERS (
  USERNAME VARCHAR(50) NOT NULL,
  NAME VARCHAR(45),
  SURNAME VARCHAR(50),
  EMAIL VARCHAR(45),
  PWD VARCHAR(50),
  ENABLED CHAR(1) NULL DEFAULT 'Y',
  LOCKED CHAR(1) NULL DEFAULT 'N',
  FAILED_LOGIN_COUNT INT NULL DEFAULT 0,
  LAST_IP VARCHAR(45) NULL COMMENT 'Last IP user connected with',
  PRIMARY KEY (USERNAME) )
;

CREATE TABLE CITY (
  ID CHAR(2) NOT NULL,
  NAME VARCHAR(50) NOT NULL,
  LAT DOUBLE NULL,
  LNG DOUBLE NULL,
  ZOOM_LEVEL INT NULL,
  ACTIVE CHAR(1) NULL DEFAULT 'N',
  PRIMARY KEY (ID) )
;

CREATE TABLE PARTNER_CATEGORIES (
  ID INT NOT NULL,
  NAME_EN VARCHAR(45) NULL,
  NAME_AL VARCHAR(45) NULL,
  DESCRIPTION VARCHAR(200) NULL,
  PRIMARY KEY (ID) )
;

CREATE TABLE PARTNER (
  ID INT NOT NULL AUTO_INCREMENT,
  NAME VARCHAR(50),
  CITY_ID CHAR(2) NOT NULL,
  ADDRESS VARCHAR(200),
  TEL VARCHAR(20),
  CEL VARCHAR(50),
  EMAIL VARCHAR(50),
  PWD VARCHAR(50),
  WEB_SITE VARCHAR(200) NULL,
  LAT DOUBLE NULL,
  LNG DOUBLE NULL,
  ZOOM_LEVEL INT NULL,
  CATEGORY_ID INT NOT NULL DEFAULT 11,
  PRIMARY KEY (ID),
  CONSTRAINT fk_PARTNER_CITY1
    FOREIGN KEY (CITY_ID )
    REFERENCES  CITY (ID )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_PARTNER_PARTNER_CATEGORIES1
    FOREIGN KEY (CATEGORY_ID )
    REFERENCES  PARTNER_CATEGORIES (ID )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
  )
;

CREATE TABLE DEAL (
  ID INT NOT NULL AUTO_INCREMENT,
  TITLE VARCHAR(200),
  PRICE INT,
  FULL_PRICE INT,
  NR_MIN_CUSTOMERS INT,
  NR_MAX_CUSTOMERS INT,
  DISCOUNT_DURATION INT NULL DEFAULT 6 COMMENT 'Discount duration for the customers whom purchased the deal',
  PARTNER_ID INT NULL,
  CLIENT_FULLNAME VARCHAR(50) NULL,
  CLIENT_CEL VARCHAR(20) NULL,
  BROKER_FULLNAME VARCHAR(50) NULL,
  BROKER_CEL VARCHAR(20) NULL,
  PUBLISHED CHAR(1) NULL DEFAULT 'N' COMMENT 'Y - has at least 1 publication, N otherwise.',
  CONTRACT_DATE TIMESTAMP NULL,
  MAIN_IMG VARCHAR(45) NULL,
  SYNTHESIS VARCHAR(1000) NULL,
  CONDITIONS VARCHAR(1000) NULL,
  DESCRIPTION VARCHAR(4000) NULL,
  APPROVED_PUBLISHING CHAR(1) NULL DEFAULT 'N',
  APPROVED_USER VARCHAR(45) NULL,
  APPROVED_DATE TIMESTAMP NULL,
  COUPON_IMMEDIATELY CHAR(1) NULL DEFAULT 'N',
  TITLE_NEWSLETTER VARCHAR(500) NULL,
  CONTRACT_COMMENT VARCHAR(500) NULL,
  INSERTED_DATE TIMESTAMP NULL,
  LAST_UPDATE TIMESTAMP NULL,
  LAST_UPDATE_USER VARCHAR(45) NULL,
  TOT_PURCHASE INT NULL,
  PRIMARY KEY (ID),
  CONSTRAINT DEAL_PARTNER_FK
    FOREIGN KEY (PARTNER_ID )
    REFERENCES  PARTNER (ID ),
  CONSTRAINT fk_APPROVE_USER
    FOREIGN KEY (APPROVED_USER )
    REFERENCES  USERS (USERNAME )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_LAST_UPDATE_USER
    FOREIGN KEY (LAST_UPDATE_USER )
    REFERENCES  USERS (USERNAME )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;

CREATE TABLE DEAL_CITY (
  DEAL_ID INT NOT NULL,
  CITY_ID CHAR(2) NOT NULL,
  PRIMARY KEY (DEAL_ID, CITY_ID),
  CONSTRAINT fk_DEAL_CITY__DEAL
    FOREIGN KEY (DEAL_ID)
    REFERENCES DEAL (ID)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_DEAL_CITY__CITY
    FOREIGN KEY (CITY_ID)
    REFERENCES CITY (ID)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE TABLE DEAL_CHOICE (
  DEAL_ID INT NOT NULL,
  CHOICE_NR VARCHAR(200) NOT NULL,
  DEAL_TITLE VARCHAR(500) NULL,
  TITLE_CHOICE VARCHAR(500) NOT NULL,
  PRICE INT NOT NULL,
  FULL_PRICE INT NOT NULL,
  COMMISSION INT NOT NULL,
  NR_MIN_CUSTOMERS INT NOT NULL,
  NR_MAX_CUSTOMERS INT NOT NULL,
  TOT_PURCHASE INT NULL,
  PRIMARY KEY (DEAL_ID, CHOICE_NR),
  CONSTRAINT fk_DEAL_CHOICE__DEAL
    FOREIGN KEY (DEAL_ID)
    REFERENCES DEAL (ID)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);


CREATE TABLE PUBLICATION (
  ID INT NOT NULL AUTO_INCREMENT,
  START_DATE DATE,
  END_DATE DATE,
  TOT_PURCHASE INT,
  DEAL_ID INT NOT NULL,
  CONFIRMED CHAR(1) NULL DEFAULT 'N',
  STATUS ENUM('W','A','C') NULL DEFAULT 'W' COMMENT 'W - Waiting; A - Active; C - Closed',
  ORDER_NR INT NULL,
  CITY_ID CHAR(2) NOT NULL,
  COUPONS_PREPARED CHAR(1) NULL DEFAULT 'N',
  PRIMARY KEY (ID),
  CONSTRAINT PUBLICATION_DEAL_FK
    FOREIGN KEY (DEAL_ID )
    REFERENCES  DEAL (ID ),
  CONSTRAINT fk_PUBLICATION_CITY1
    FOREIGN KEY (CITY_ID )
    REFERENCES  CITY (ID )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;

CREATE TABLE CUSTOMER (
  ID INT NOT NULL AUTO_INCREMENT,
  NAME VARCHAR(50),
  SURNAME VARCHAR(50),
  BIRTHDATE TIMESTAMP,
  EMAIL VARCHAR(50),
  PWD VARCHAR(50),
  TEL VARCHAR(50),
  ACTIVE CHAR(1) NULL DEFAULT 'N',
  ADDRESS VARCHAR(100),
  SEX ENUM('M','F') NULL COMMENT 'M=Male, F=Female',
  INVITED_BY INT NULL,
  REG_DATE TIMESTAMP,
  FB_ID INT NULL,
  CITY_ID INT NULL,
  PRIMARY KEY (ID),
  CONSTRAINT fk_CUSTOMER_CUSTOMER1
    FOREIGN KEY (INVITED_BY )
    REFERENCES  CUSTOMER (ID )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_CUSTOMER_CITY
    FOREIGN KEY (CITY_ID )
    REFERENCES  CITY (ID )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
;

CREATE TABLE INVITE (
  CUSTOMER_ID INT NOT NULL AUTO_INCREMENT,
  OBJECT_NAME VARCHAR(200),
  EMAIL_INVITED VARCHAR(50) NOT NULL,
  CONFIRMED CHAR(1),
  PRIMARY KEY (CUSTOMER_ID, EMAIL_INVITED),
  CONSTRAINT INVITE_CUSTOMER_FK
    FOREIGN KEY (CUSTOMER_ID )
    REFERENCES  CUSTOMER (ID ))
;

CREATE TABLE DISCOUNT_CARD (
  ID CHAR(7) NOT NULL,
  PERC_DISCOUNT INT,
  USED_BY VARCHAR(50),
  USED_DATE TIMESTAMP,
  PRIMARY KEY (ID) )
;

CREATE TABLE CREDIT (
  ID INT NOT NULL AUTO_INCREMENT,
  ASSIGNED_DATE TIMESTAMP,
  VALID_DATE TIMESTAMP,
  USED_DATE TIMESTAMP,
  VALUE INT,
  CUSTOMER_ID INT NOT NULL,
  TYPE ENUM('B','R','D') NULL COMMENT 'B - Benefit; R - Reimbursement; D - Difference from a previous credit',
  ABOUT VARCHAR(500) NOT NULL,
  ABOUT_USE VARCHAR(500) NULL,
  PRIMARY KEY (ID),
  CONSTRAINT BONUS_CUSTOMER_FK
    FOREIGN KEY (CUSTOMER_ID )
    REFERENCES  CUSTOMER (ID ))
;

CREATE TABLE ROLE (
  ID VARCHAR(20) NOT NULL,
  DESCRIPTION VARCHAR(100),
  PRIMARY KEY (ID) )
;

CREATE TABLE USER_ROLE (
  USERNAME VARCHAR(50) NOT NULL,
  ROLEID VARCHAR(20) NOT NULL,
  PRIMARY KEY (ROLEID, USERNAME),
  CONSTRAINT USER_ROLE__ROLE_FK
    FOREIGN KEY (ROLEID )
    REFERENCES  ROLE (ID ),
  CONSTRAINT USER_ROLE__USER_FK
    FOREIGN KEY (USERNAME )
    REFERENCES  USERS (USERNAME ))
;

CREATE TABLE PAYMENT (
  ID INT NOT NULL AUTO_INCREMENT,
  PAY_TYPE VARCHAR(10) NULL COMMENT '1=CASH; 2=CREDIT_CARD; 3=PAYPAL; 4=AMERICAN_EXPRESS; 5=BKT;',
  AMOUNT INT NULL,
  OP_DATE TIMESTAMP NULL,
  PRIMARY KEY (ID) )
;

CREATE TABLE PAY_EASYPAY (
  PAYMENT_ID INT NOT NULL,
  EP_TXN_ID VARCHAR(17) NULL,
  EP_INVOICE_ID VARCHAR(45) NULL,
  MERCHANT_USERNAME VARCHAR(45) NULL,
  RESPONSE_CODE INT(3) NULL,
  TRANSACTION_STATUS VARCHAR(45) NULL,
  ORIGINAL_RESPONSE VARCHAR(500) NULL,
  AMOUNT INT(11) NULL,
  DATE TIMESTAMP NULL,
  FEE DECIMAL(18,2) NULL,
  PRIMARY KEY (PAYMENT_ID),
  CONSTRAINT fk_EASYPAY_PAYMENT
    FOREIGN KEY (PAYMENT_ID )
    REFERENCES PAYMENT (ID )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE TABLE PAYBANK (
  PAYMENT_ID INT NOT NULL,
  BANK_NAME VARCHAR(45) NULL,
  REF_NR VARCHAR(45) NULL,
  PB_FULL_NAME VARCHAR(45) NULL,
  PB_DATE TIMESTAMP NULL,
  PB_NOTE VARCHAR(500) NULL,
  PRIMARY KEY (PAYMENT_ID),
  CONSTRAINT fk_BANK_PAYMENT1
    FOREIGN KEY (PAYMENT_ID )
    REFERENCES PAYMENT (ID )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;

CREATE TABLE EMAILS_ALL (
  EMAIL VARCHAR(45) NOT NULL,
  accept_newsletter CHAR(1) NOT NULL
);

 CREATE TABLE EMAILS_CITY (
  EMAIL VARCHAR(45) NOT NULL,
  CITY_ID CHAR(2) NOT NULL,
  PRIMARY KEY (EMAIL, CITY_ID),
  CONSTRAINT fk_CITY_has_EMAILS_ALL_CITY1
    FOREIGN KEY (CITY_ID )
    REFERENCES CITY (ID )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_CITY_has_EMAILS_ALL_EMAILS_ALL1
    FOREIGN KEY (EMAIL )
    REFERENCES EMAILS_ALL (email )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;

 CREATE TABLE DEAL_FEEDBACK (
  ID INT NOT NULL AUTO_INCREMENT,
  DEAL_ID INT NOT NULL,
  CUSTOMER_ID INT NOT NULL,
  BODY VARCHAR(500) NULL,
  OP_DATE TIMESTAMP NULL,
  APPROVED CHAR(1) NOT NULL DEFAULT 'N',
  RATE INT NULL DEFAULT 1,
  PRIMARY KEY (ID),
  CONSTRAINT fk_DEAL_FEEDBACK_DEAL1
    FOREIGN KEY (DEAL_ID )
    REFERENCES DEAL (ID )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_DEAL_FEEDBACK_CUSTOMER1
    FOREIGN KEY (CUSTOMER_ID )
    REFERENCES CUSTOMER (ID )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ;

CREATE TABLE PURCHASE (
  ID INT NOT NULL AUTO_INCREMENT,
  DEAL_ID INT NOT NULL,
  DEAL_CHOICE INT NOT NULL,
  CUSTOMER_ID INT NOT NULL,
  ORDER_ID INT,
  QUANTITY INT,
  PURCHASE_DATE TIMESTAMP,
  AMOUNT INT NULL,
  TOT_AMOUNT INT NULL,
  MONEY_SPENT INT,
  CREDIT_SPENT INT,
  PAYMENT_ID INT NOT NULL,
  DISCOUNT_ID CHAR(7) NULL,
  CONFIRMED CHAR(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (ID),
  UNIQUE INDEX PAYMENT_ID_UNIQUE (PAYMENT_ID ASC),
  CONSTRAINT PURCHASE_DEAL_FK
    FOREIGN KEY (DEAL_ID )
    REFERENCES  DEAL_CHOICE (DEAL_ID),
  CONSTRAINT PURCHASE_CUSTOMER_FK
    FOREIGN KEY (CUSTOMER_ID )
    REFERENCES  CUSTOMER (ID ),
  CONSTRAINT PURCHASE_DISCOUNT_CARD_FK
    FOREIGN KEY (DISCOUNT_ID )
    REFERENCES  DISCOUNT_CARD (ID )
    ON DELETE SET NULL,
  CONSTRAINT fk_PURCHASE_PAYMENT1
    FOREIGN KEY (PAYMENT_ID )
    REFERENCES  PAYMENT (ID )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;

CREATE TABLE PURCHASE_CREDIT (
  PURCHASE_ID INT NOT NULL,
  CREDIT_ID INT NOT NULL,
  PRIMARY KEY (PURCHASE_ID, CREDIT_ID),
  CONSTRAINT PURCHASE_BONUS_BONUS_FK
    FOREIGN KEY (CREDIT_ID )
    REFERENCES  CREDIT (ID ),
  CONSTRAINT PURCHASE_BONUS_PURCHASE_FK
    FOREIGN KEY (PURCHASE_ID )
    REFERENCES  PURCHASE (ID ))
;

CREATE TABLE AUDIT_TRAIL (
  ID INT NOT NULL AUTO_INCREMENT,
  USERNAME VARCHAR(50) NOT NULL,
  OP_NAME VARCHAR(50),
  OP_DESCRIPTION VARCHAR(500),
  OP_TIME TIMESTAMP,
  ROLES VARCHAR(100) NULL COMMENT 'Roles of the user when the operation was executed',
  PRIMARY KEY (ID),
  CONSTRAINT AUDIT_TRAIL_USERS_FK
    FOREIGN KEY (USERNAME )
    REFERENCES  USERS (USERNAME ))
;

CREATE TABLE PAYCASH (
  PAYMENT_ID INT NOT NULL,
  SELLER_FULL_NAME VARCHAR(45) NULL,
  BUYER_FULL_NAME VARCHAR(45) NULL,
  BUYER_TEL VARCHAR(45) NULL,
  NOTE VARCHAR(500) NULL,
  PRIMARY KEY (PAYMENT_ID),
  CONSTRAINT fk_CASHPAY_PAYMENT1
    FOREIGN KEY (PAYMENT_ID )
    REFERENCES  PAYMENT (ID )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;

CREATE TABLE PAYPAL (
  PAYMENT_ID INT NOT NULL,
  ACCOUNT VARCHAR(45) NULL,
  PRIMARY KEY (PAYMENT_ID),
  CONSTRAINT fk_PAYPALPAY_PAYMENT1
    FOREIGN KEY (PAYMENT_ID )
    REFERENCES  PAYMENT (ID )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;

CREATE TABLE COUPON_STATUS (
  ID ENUM('N','U','E','R') NOT NULL DEFAULT 'N' COMMENT 'N for Not used; U for Used; E for Expired; R for Returned',
  DESCRIPTION VARCHAR(45) NULL,
  PRIMARY KEY (ID) )
;

CREATE TABLE COUPON (
  CODE CHAR(10) NOT NULL,
  VALID_FROM DATE NULL,
  VALID_TO DATE NULL,
  PDF_DATA BLOB NULL,
  HTML_LINK VARCHAR(100) NULL,
  PURCHASE_ID INT NOT NULL,
  COUPON_STATUS ENUM('N','U','E','R') NOT NULL DEFAULT 'N' COMMENT 'N for Not used; U for Used; E for Expired; R for Returned',
  LST_STATUS_CHANGE_DATE TIMESTAMP NULL COMMENT 'The date of last status change. Example: if today the COUPON will be used the STATUS will set to U and dhe LST_STATUS_CHANGE_DATE to TODAY',
  PRIMARY KEY (CODE),
  CONSTRAINT fk_COUPON_PURCHASE1
    FOREIGN KEY (PURCHASE_ID )
    REFERENCES  PURCHASE (ID )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_COUPON_COUPON_STATUS1
    FOREIGN KEY (COUPON_STATUS )
    REFERENCES  COUPON_STATUS (ID )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;

CREATE TABLE UPLOAD_FILE (
  DEAL_ID INT NOT NULL,
  IMG_NAME VARCHAR(100) NOT NULL,
  PRIMARY KEY (DEAL_ID, IMG_NAME),
  CONSTRAINT fk_UPLOAD_FILE_DEAL1
    FOREIGN KEY (DEAL_ID )
    REFERENCES  DEAL (ID )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;

CREATE TABLE NEWSLETTER (
  CUSTOMER_ID INT NOT NULL,
  CITY_ID CHAR(2) NOT NULL,
  PRIMARY KEY (CUSTOMER_ID, CITY_ID),
  CONSTRAINT fk_CUSTOMER_has_CITY_CUSTOMER1
    FOREIGN KEY (CUSTOMER_ID )
    REFERENCES  CUSTOMER (ID )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_CUSTOMER_has_CITY_CITY1
    FOREIGN KEY (CITY_ID )
    REFERENCES  CITY (ID )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
;

CREATE TABLE CONFIG (
  CONFIG_KEY VARCHAR(45) NOT NULL,
  CONFIG_VALUE VARCHAR(45) NULL,
  PRIMARY KEY (CONFIG_KEY) )
;
