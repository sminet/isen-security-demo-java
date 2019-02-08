CREATE SEQUENCE ARTICLES_SEQUENCE;
CREATE TABLE PUBLIC.ARTICLES (
	ID BIGINT DEFAULT (NEXT VALUE FOR PUBLIC.ARTICLES_SEQUENCE) NOT NULL AUTO_INCREMENT,
	TITLE VARCHAR(255),
	CONTENT CLOB,
	CONSTRAINT ARTICLES_CONSTRAINT_ID PRIMARY KEY (ID)
);
CREATE UNIQUE INDEX ARTICLES_PK ON PUBLIC.ARTICLES (ID);

CREATE SEQUENCE USERS_SEQUENCE;
CREATE TABLE PUBLIC.USERS (
	ID BIGINT DEFAULT (NEXT VALUE FOR PUBLIC.USERS_SEQUENCE) NOT NULL AUTO_INCREMENT,
	USERNAME VARCHAR(255),
	PASSWORD VARCHAR(255),
	CONSTRAINT USERS_CONSTRAINT_ID PRIMARY KEY (ID)
);
CREATE UNIQUE INDEX USERS_PK ON PUBLIC.USERS (ID);

CREATE SEQUENCE PRODUCTS_SEQUENCE;
CREATE TABLE PUBLIC.PRODUCTS (
	ID BIGINT DEFAULT (NEXT VALUE FOR PUBLIC.PRODUCTS_SEQUENCE) NOT NULL AUTO_INCREMENT,
	NAME VARCHAR(255),
	DESCRIPTION VARCHAR(255),
	PRICE BIGINT,
	CONSTRAINT PRODUCTS_CONSTRAINT_ID PRIMARY KEY (ID)
);
CREATE UNIQUE INDEX PRODUCTS_PK ON PUBLIC.PRODUCTS (ID);

-- Insert data
INSERT INTO PUBLIC.USERS (USERNAME, PASSWORD) VALUES 
	('admin', 'X7(93#3]5^1Zh]l'),
	('denis.fresnel', 'azerty'),
	('florian.auger', 'ytreza');

INSERT INTO PUBLIC.ARTICLES (CONTENT,TITLE) VALUES
	('Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus id sagittis lorem, id rhoncus nisi. Quisque non enim at risus egestas scelerisque a quis nunc. Integer tristique velit vitae urna euismod, sit amet varius libero venenatis. Integer tempus rutrum leo a consectetur. Nulla nec ante sit amet diam bibendum placerat eget et diam. Mauris eget malesuada justo, nec consectetur nulla. Morbi eget nunc a mi mollis lacinia. In nec commodo nunc.','Lorem ipsum dolor sit amet'),
	('Curabitur ac justo non lectus pharetra cursus. Ut ac diam egestas, egestas justo quis, ornare velit. Nulla sed justo ut ex efficitur fermentum. In risus magna, suscipit quis dolor a, fermentum malesuada nulla. Quisque rutrum tempus augue et pulvinar. Integer a velit dui. Aenean facilisis elementum massa sit amet finibus. Vivamus sit amet gravida tellus. Aenean sit amet urna libero. Sed gravida, lorem at pretium egestas, justo justo sodales erat, nec consequat lacus nunc eget turpis. Duis tempor mi sed aliquam maximus. Phasellus eget feugiat magna.','Curabitur ac justo non lectus pharetra cursus');

INSERT INTO PUBLIC.PRODUCTS (NAME, PRICE, DESCRIPTION) VALUES
	('TPhone X', 96000, 'The beautiful TPhone X'),
	('GPhone V', 16000, 'Simpler, better, cheaper');