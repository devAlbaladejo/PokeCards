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
	strong character varying NOT NULL,
	CONSTRAINT pk_types PRIMARY KEY(id)
);

INSERT INTO Types (name,color) VALUES ('normal', '#A8A878');
INSERT INTO Types (name,color,strong) VALUES ('fire', '#F08030','17;12;6;5');
INSERT INTO Types (name,color,strong) VALUES ('water', '#6890F0','2;13;9');
INSERT INTO Types (name,color,strong) VALUES ('electric', '#F8D030','3;10');
INSERT INTO Types (name,color,strong) VALUES ('grass', '#78C850','3;13;9');
INSERT INTO Types (name,color,strong) VALUES ('ice', '#98D8D8','15;5;9;10');
INSERT INTO Types (name,color,strong) VALUES ('fighting', '#C03028','1');
INSERT INTO Types (name,color,strong) VALUES ('poison', '#A040A0','18;5');
INSERT INTO Types (name,color,strong) VALUES ('ground', '#E0C068','17;4;2;13;8');
INSERT INTO Types (name,color,strong) VALUES ('flying', '#A890F0','12;7;5');
INSERT INTO Types (name,color,strong) VALUES ('psychic', '#F85888','7;8');
INSERT INTO Types (name,color,strong) VALUES ('bug', '#A8B820','5;11;16');
INSERT INTO Types (name,color,strong) VALUES ('rock', '#B8A038','12;2;6;10');
INSERT INTO Types (name,color,strong) VALUES ('ghost', '#705898','14;10');
INSERT INTO Types (name,color,strong) VALUES ('dragon', '#7038F8','15');
INSERT INTO Types (name,color,strong) VALUES ('dark', '#705848','14;11');
INSERT INTO Types (name,color,strong) VALUES ('steel', '#B8B8D0','18;6;13');
INSERT INTO Types (name,color,strong) VALUES ('fairy', '#F0B6BC','15;7;16');

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
	generation character varying NOT NULL,
	description character varying NOT NULL,
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
	CONSTRAINT pk_users PRIMARY KEY(id),
	CONSTRAINT uk_username UNIQUE (username),
	CONSTRAINT uk_email UNIQUE (email)
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

-- Exchange Offers
CREATE SEQUENCE public.seq_exchange_offers
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
    CYCLE;

CREATE TABLE Exchange_offers(
	id integer DEFAULT nextval('seq_exchange_offers'::regclass) NOT NULL,
	user_id_offer integer not null,
	card_id_offer integer not null,
	desired_card_1 integer not null,
	desired_card_2 integer,
	desired_card_3 integer,
	active integer NOT NULL DEFAULT 1,
	CONSTRAINT pk_exchange_offers PRIMARY KEY(id),
	CONSTRAINT fk_exchange_offers_users FOREIGN KEY (user_id_offer) REFERENCES Users(id),
	CONSTRAINT fk_exchange_offers_cards FOREIGN KEY (card_id_offer) REFERENCES Cards(id),
	CONSTRAINT fk_exchange_offers_desired_card_1 FOREIGN KEY (desired_card_1) REFERENCES Cards(id),
	CONSTRAINT fk_exchange_offers_desired_card_2 FOREIGN KEY (desired_card_2) REFERENCES Cards(id),
	CONSTRAINT fk_exchange_offers_desired_card_3 FOREIGN KEY (desired_card_3) REFERENCES Cards(id)
);

-- Exchanges
CREATE SEQUENCE public.seq_exchanges
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
    CYCLE;

CREATE TABLE Exchanges (
    id integer DEFAULT nextval('public.seq_exchanges'::regclass) NOT NULL,
	exchange_offer_id integer NOT NULL,
    user_id_demand integer NOT NULL,
    card_id_demand integer NOT NULL,
	CONSTRAINT pk_exchange PRIMARY KEY(id),
	CONSTRAINT fk_exchange_offer FOREIGN KEY (exchange_offer_id) REFERENCES exchange_offers(id),
    FOREIGN KEY (user_id_demand) REFERENCES Users(id),
    FOREIGN KEY (card_id_demand) REFERENCES Cards(id)
);
	