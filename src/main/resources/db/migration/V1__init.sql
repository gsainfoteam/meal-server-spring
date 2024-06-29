CREATE SEQUENCE IF NOT EXISTS meal_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE meal
(
    id        BIGINT NOT NULL,
    bldg_type INTEGER,
    lang_type INTEGER,
    date_type INTEGER,
    kind_type INTEGER,
    bldg      VARCHAR(255),
    date      VARCHAR(255),
    kind      VARCHAR(255),
    menu      VARCHAR(255),
    special   VARCHAR(255),
    CONSTRAINT pk_meal PRIMARY KEY (id)
);