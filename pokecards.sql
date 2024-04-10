-- Datatable
CREATE DATABASE pokecards;

-- Types

CREATE SEQUENCE seq_types
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CYCLE;

CREATE TABLE Types(
	id integer DEFAULT nextval('seq_types'::regclass) NOT NULL,
	name character varying NOT NULL,
	color character varying NOT NULL,
	CONSTRAINT pk_types PRIMARY KEY(id)
);

INSERT INTO Types (name,color) VALUES ('normal', '#A8A878');
INSERT INTO Types (name,color) VALUES ('fire', '#F08030');
INSERT INTO Types (name,color) VALUES ('water', '#6890F0');
INSERT INTO Types (name,color) VALUES ('electric', '#F8D030');
INSERT INTO Types (name,color) VALUES ('grass', '#78C850');
INSERT INTO Types (name,color) VALUES ('ice', '#98D8D8');
INSERT INTO Types (name,color) VALUES ('fighting', '#C03028');
INSERT INTO Types (name,color) VALUES ('poison', '#A040A0');
INSERT INTO Types (name,color) VALUES ('ground', '#E0C068');
INSERT INTO Types (name,color) VALUES ('flying', '#A890F0');
INSERT INTO Types (name,color) VALUES ('psychic', '#F85888');
INSERT INTO Types (name,color) VALUES ('bug', '#A8B820');
INSERT INTO Types (name,color) VALUES ('rock', '#B8A038');
INSERT INTO Types (name,color) VALUES ('ghost', '#705898');
INSERT INTO Types (name,color) VALUES ('dragon', '#7038F8');
INSERT INTO Types (name,color) VALUES ('dark', '#705848');
INSERT INTO Types (name,color) VALUES ('steel', '#B8B8D0');
INSERT INTO Types (name,color) VALUES ('fairy', '#F0B6BC');

-- Rarity

CREATE SEQUENCE seq_rarity
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CYCLE;

CREATE TABLE Rarities(
	id integer DEFAULT nextval('seq_rarity'::regclass) NOT NULL,
	name character varying NOT NULL,
	color character varying NOT NULL,
	CONSTRAINT pk_rarities PRIMARY KEY(id)
);

INSERT INTO Rarities (name,color) VALUES ('common', '#d3d3d3');
INSERT INTO Rarities (name,color) VALUES ('normal', '#3cab95');
INSERT INTO Rarities (name,color) VALUES ('rare', '#DC7633');
INSERT INTO Rarities (name,color) VALUES ('epic', '#A569BD');
INSERT INTO Rarities (name,color) VALUES ('legendary', '#17202A');

-- Cards

CREATE TABLE Cards(
	id integer NOT NULL,
	name character varying NOT NULL,
	image character varying NOT NULL,
	primary_type integer NOT NULL,
	secondary_type integer DEFAULT NULL,
	height integer NOT NULL,
	weight integer NOT NULL,
	hp integer NOT NULL,
	attack integer NOT NULL,
	defense integer NOT NULL,
	special_attack integer NOT NULL,
	special_defense integer NOT NULL,
	speed integer NOT NULL,
	rarity integer NOT NULL,
	CONSTRAINT pk_cards PRIMARY KEY(id),
	CONSTRAINT fk_primary_type_types FOREIGN KEY (primary_type) REFERENCES Types(id),
	CONSTRAINT fk_secondary_type_types FOREIGN KEY (secondary_type) REFERENCES Types(id),
	CONSTRAINT fk_rarity_rarities FOREIGN KEY (rarity) REFERENCES Rarities(id)
)

-- Users
CREATE SEQUENCE seq_users
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CYCLE;
	
CREATE TABLE Users(
	id integer DEFAULT nextval('seq_users'::regclass) NOT NULL,
	username character varying NOT NULL,
	email character varying NOT NULL,
	password character varying NOT NULL,
	points integer NOT NULL,
	CONSTRAINT pk_users PRIMARY KEY(id)
);

-- User Cards

CREATE TABLE UserCards(
	user_id integer NOT NULL,
	card_id integer NOT NULL,
	amount integer NOT NULL,
	CONSTRAINT pk_usercards PRIMARY KEY(user_id, card_id),
	CONSTRAINT fk_usercards_users FOREIGN KEY(user_id) REFERENCES Users(id),
	CONSTRAINT fk_usercards_cards FOREIGN KEY(card_id) REFERENCES Cards(id)
)