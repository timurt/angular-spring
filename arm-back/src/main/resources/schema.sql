CREATE TABLE roles
(
  id         BIGSERIAL    NOT NULL
    CONSTRAINT roles_pkey
    PRIMARY KEY,
  created_at TIMESTAMP,
  deleted_at TIMESTAMP,
  is_deleted BOOLEAN DEFAULT FALSE,
  updated_at TIMESTAMP,
  key        VARCHAR(255) NOT NULL,
  name       VARCHAR(255)
);



CREATE TABLE users
(
  id         BIGSERIAL NOT NULL
    CONSTRAINT users_pkey
    PRIMARY KEY,
  created_at TIMESTAMP,
  deleted_at TIMESTAMP,
  is_deleted BOOLEAN DEFAULT FALSE,
  updated_at TIMESTAMP,
  login      VARCHAR(255),
  password   VARCHAR(255),
  name       VARCHAR(255),
  role_id    BIGINT
);

CREATE UNIQUE INDEX users_login_uindex
  ON users (login);


CREATE TABLE restaurants
(
  id              BIGSERIAL        NOT NULL
    CONSTRAINT apartments_pkey
    PRIMARY KEY,
  created_at      TIMESTAMP,
  deleted_at      TIMESTAMP,
  is_deleted      BOOLEAN,
  updated_at      TIMESTAMP,
  description     TEXT             NOT NULL,
  average_check   DOUBLE PRECISION NOT NULL,
  has_wifi        BOOLEAN,
  latitude        DOUBLE PRECISION NOT NULL,
  longitude       DOUBLE PRECISION NOT NULL,
  name            VARCHAR(255)     NOT NULL,
  number_of_seats INTEGER          NOT NULL,
  owner_id        BIGINT
    CONSTRAINT fkg8d7snk339ige8b674xovxr81
    REFERENCES users
);

