CREATE TYPE equipment_type AS ENUM ('WORKWEAR', 'INSTRUMENTS', 'EQUIPMENT');
CREATE CAST (VARCHAR AS equipment_type) WITH INOUT AS IMPLICIT;

CREATE TYPE waybill_type AS ENUM ('DELIVERY', 'EXPENSE');
CREATE CAST (VARCHAR AS waybill_type) WITH INOUT AS IMPLICIT;

CREATE TABLE storage
(
    id           INT PRIMARY KEY,
    name         VARCHAR,
    phone_number VARCHAR(12)
);

CREATE TABLE equipment
(
    id   SERIAL PRIMARY KEY,
    name equipment_type
);

CREATE TABLE waybill
(
    id                SERIAL PRIMARY KEY,
    name              waybill_type,
    employee_name     VARCHAR,
    employee_position VARCHAR,
    date              TIMESTAMP,
    storage_id        INT REFERENCES storage (id)
);

CREATE TABLE equipment_card
(
    equip_count INT,
    equip_id    INT REFERENCES equipment (id),
    waybill_id  INT REFERENCES waybill (id),
    PRIMARY KEY(equip_id, waybill_id)
);