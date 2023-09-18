CREATE TYPE equipment_type AS ENUM ('WORKWEAR', 'INSTRUMENTS', 'EQUIPMENT');
CREATE TYPE waybill_type AS ENUM ('DELIVERY', 'EXPENSE');

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
    id           SERIAL PRIMARY KEY,
    equip_count  INT,
    equip_id INT REFERENCES equipment (id),
    waybill_id   INT REFERENCES waybill (id)
);