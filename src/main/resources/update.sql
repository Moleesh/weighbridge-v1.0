-- TABLE UPDATES;
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  SLNO                   	            INTEGER 		DEFAULT 1;
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  BAUDRATE               	            INTEGER 		DEFAULT 1200;
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  PORTNAME               	            VARCHAR(100) 	DEFAULT 'COM0;8;0;10;~~~;f';
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  TITLE1                 	            VARCHAR(100) 	DEFAULT 'BABULENS';
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  TITLE2                 	            VARCHAR(100) 	DEFAULT 'NAGERCOIL';
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  TITLE3                 	            VARCHAR(100) 	DEFAULT '';
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  FOOTER                 	            VARCHAR(100) 	DEFAULT 'FOOTER';
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  PRINTER                	            VARCHAR(100) 	DEFAULT '';
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  EXCLUDECUSTOMERS       	            BOOLEAN 		DEFAULT TRUE;
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  EXCLUDECHARGES         	            BOOLEAN 		DEFAULT FALSE;
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  COPIES                 	            INTEGER 		DEFAULT 1;
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  PRINTOPTIONFORWEIGHT   	            VARCHAR(100)	DEFAULT 'Standard';
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  REPORT                 	            VARCHAR(100)	DEFAULT 'Standard';
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  EXCLUDEDRIVER          	            BOOLEAN 		DEFAULT TRUE;
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  EXCLUDEREMARKS         	            BOOLEAN 		DEFAULT TRUE;
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  EXCLUDE_PLACE_AND_PHONE_NUMBER       BOOLEAN     	DEFAULT TRUE;
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  EXCLUDEBAGS            	            BOOLEAN 		DEFAULT TRUE;
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  EXCLUDEDCNO            	            BOOLEAN 		DEFAULT TRUE;
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  EXCLUDE_CREDIT         	            BOOLEAN 		DEFAULT TRUE;
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  AUTOCHARGES            	            BOOLEAN 		DEFAULT FALSE;
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  MATERIALSL             	            BOOLEAN 		DEFAULT FALSE;
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  ICEWATER               	            BOOLEAN 		DEFAULT FALSE;
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  ROUND_OFF              	            BOOLEAN 		DEFAULT FALSE;
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  TARE_TOKEN             	            BOOLEAN 		DEFAULT FALSE;
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  EXIT_PASS              	            BOOLEAN  		DEFAULT FALSE;
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  SMS                    	            BOOLEAN 		DEFAULT FALSE;
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  CAMERA                 	            BOOLEAN 		DEFAULT FALSE;
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  SMSBAUDRATE            	            INTEGER 		DEFAULT 9600;
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  SMSPORTNAME            	            VARCHAR(100)	DEFAULT 'COM0';
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  LINE1                  	            VARCHAR(100)	DEFAULT '';
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  LINE2                  	            VARCHAR(100)	DEFAULT '';
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  LINE3                  	            VARCHAR(100)	DEFAULT '';
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  LINE4                  	            VARCHAR(100)	DEFAULT '';
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  NAME_OF_WORK           	            VARCHAR(200)	DEFAULT '';
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  NAME_OF_CONTRACTOR     	            VARCHAR(100)	DEFAULT '';
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  DEPARTMENT_NAME        	            VARCHAR(100)	DEFAULT '';
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  SITE_AT                	            VARCHAR(100)	DEFAULT '';
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  AGREEMENT_NO           	            VARCHAR(100)	DEFAULT '';
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  ESTIMATE_NO            	            VARCHAR(100)	DEFAULT '';
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  TARENOSLNO             	            BOOLEAN 		DEFAULT FALSE;
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  BAGWEIGHT              	            DOUBLE 		    DEFAULT 0.0;
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  ROUND_OFF_DECIMALS     	            INTEGER 		DEFAULT 0;
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  NEED_LOGIN             	            BOOLEAN 		DEFAULT FALSE;
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  NEED_PRINT_COPY_DIALOG 	            BOOLEAN 		DEFAULT FALSE;
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  SHOW_STATUS            	            BOOLEAN 		DEFAULT FALSE;
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  TAKE_BACKUP            	            BOOLEAN 		DEFAULT TRUE;
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  TRIAL_LICENSE_PASSWORD 	            VARCHAR(20) 	DEFAULT '147085';
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  LICENSE_PASSWORD       	            VARCHAR(20) 	DEFAULT '147085aA';
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  UNLOCK_PASSWORD        	            VARCHAR(20) 	DEFAULT '147085';
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  CAMERA_PASSWORD        	            VARCHAR(20) 	DEFAULT '147085';
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  SMS_PASSWORD           	            VARCHAR(20) 	DEFAULT '147085';
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  MANUAL_ENTRY_PASSWORD  	            VARCHAR(20) 	DEFAULT '147085';
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  EDIT_ENABLE_PASSWORD   	            VARCHAR(20) 	DEFAULT '147085';
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  RESET_PASSWORD         	            VARCHAR(20) 	DEFAULT '147085';
ALTER TABLE SETTINGS ADD COLUMN IF NOT EXISTS  LOGIN_PASSWORD                       VARCHAR(20) 	DEFAULT '123';