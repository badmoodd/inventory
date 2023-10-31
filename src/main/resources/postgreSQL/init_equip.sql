CREATE TYPE waybill_type AS ENUM ('DELIVERY', 'EXPENSE');
CREATE CAST (VARCHAR AS waybill_type) WITH INOUT AS IMPLICIT;

CREATE TABLE storage
(
    id           INT PRIMARY KEY,
    name         VARCHAR,
    phone_number VARCHAR
);

CREATE TABLE equipment
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR
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
    PRIMARY KEY (equip_id, waybill_id)
);

INSERT INTO storage (id, name, phone_number)
VALUES (1, 'Storage A', '123-456-7890'),
       (2, 'Storage B', '987-654-3210'),
       (3, 'Main Warehouse', '123-456-7890'),
       (4, 'Eastside Storage', '987-654-3210'),
       (5, 'Central Depot', '555-123-4567'),
       (6, 'West Coast Warehouse', '111-222-3333');

INSERT INTO equipment (name)
VALUES ('Work Wear'),
       ('Instruments'),
       ('Equipment'),
       ('Safety Gear'),
       ('Precision Tools'),
       ('Heavy Machinery'),
       ('Electronics');

INSERT INTO waybill (name, employee_name, employee_position, date, storage_id)
VALUES ('DELIVERY', 'John Doe', 'Manager', '2023-10-28 10:00:00', 1),
       ('EXPENSE', 'Jane Smith', 'Supervisor', '2023-10-29 15:30:00', 2),
       ('DELIVERY', 'Alice Johnson', 'Logistics Coordinator', '2023-10-28 10:00:00', 3),
       ('EXPENSE', 'Bob Thompson', 'Warehouse Manager', '2023-10-29 15:30:00', 4),
       ('DELIVERY', 'Eva Rodriguez', 'Shipping Supervisor', '2023-10-30 12:45:00', 5),
       ('EXPENSE', 'Chris Smith', 'Inventory Analyst', '2023-10-31 08:15:00', 6);

INSERT INTO equipment_card (equip_count, equip_id, waybill_id)
VALUES (10, 2, 3), -- 10 Work Wear items for Waybill 1
       (5, 2, 2),  -- 5 Instruments for Waybill 1
       (20, 3, 3), -- 20 Equipment items for Waybill 2
       (15, 4, 4), -- 15 Safety Gear items for Waybill 1
       (8, 2, 5),  -- 8 Precision Tools for Waybill 1
       (30, 3, 6), -- 30 Heavy Machinery items for Waybill 2
       (12, 4, 2);
