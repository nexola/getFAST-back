CREATE TABLE IF NOT EXISTS tb_account
(
    id            UUID NOT NULL,
    creation_date TIMESTAMP,
    deleted       BOOLEAN,
    last_update   TIMESTAMP,
    name          VARCHAR(255),
    email         VARCHAR(255),
    telephone     VARCHAR(255),
    cnpj          VARCHAR(255) UNIQUE,
    cpf           VARCHAR(255),
    cep           VARCHAR(255),
    address       VARCHAR(255),
    number        VARCHAR(255),
    neighborhood  VARCHAR(255),
    city          VARCHAR(255),
    state         VARCHAR(255),
    complement    VARCHAR(255),
    token         VARCHAR(255),
    expiration    TIMESTAMP WITHOUT TIME ZONE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tb_user
(
    id            UUID NOT NULL,
    creation_date TIMESTAMP,
    deleted       BOOLEAN,
    last_update   TIMESTAMP,
    name          VARCHAR(255),
    email         VARCHAR(255),
    password      VARCHAR(255),
    cpf           VARCHAR(255),
    telephone     VARCHAR(255),
    token         VARCHAR(255),
    expiration    TIMESTAMP WITHOUT TIME ZONE,
    account_id    UUID,
    FOREIGN KEY (account_id) REFERENCES tb_account (id),
    PRIMARY KEY (id)
);