CREATE TABLE Product
(
    id           BIGSERIAL PRIMARY KEY,
    code         CHAR(10)       NOT NULL UNIQUE,
    name         VARCHAR(255)   NOT NULL,
    price_eur    NUMERIC(12, 2) NOT NULL CHECK (price_eur >= 0),
    price_usd    NUMERIC(12, 2) NOT NULL CHECK (price_usd >= 0),
    is_available BOOLEAN        NOT NULL DEFAULT TRUE
);