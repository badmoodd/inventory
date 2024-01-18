CREATE TYPE waybill_type AS ENUM ('DELIVERY', 'EXPENSE');
CREATE CAST (VARCHAR AS waybill_type) WITH INOUT AS IMPLICIT;

CREATE TABLE storages
(
    id           INT PRIMARY KEY,
    name         VARCHAR,
    phone_number VARCHAR
);

CREATE TABLE equipments
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR
);

CREATE TABLE waybills
(
    id                SERIAL PRIMARY KEY,
    name              waybill_type,
    employee_name     VARCHAR,
    employee_position VARCHAR,
    date              TIMESTAMP,
    storage_id        INT REFERENCES storages (id) ON DELETE CASCADE -- It means when storage is deleted all the related waybills will also be deleted.
);

CREATE TABLE equipment_cards
(
    equip_count INT,
    equip_id    INT REFERENCES equipments (id) ON DELETE CASCADE,
    waybill_id  INT REFERENCES waybills (id) ON DELETE CASCADE,
    PRIMARY KEY (equip_id, waybill_id)
);

CREATE VIEW equipment_view AS
SELECT
--     Rename columns
waybills.date   as waybills_date,
waybills.name   AS waybill_name,
equipments.name AS equipment_name,
equipment_cards.equip_count,
waybills.employee_name,
waybills.employee_position,
storages.id     as storage_id
FROM waybills
         JOIN equipment_cards ON waybills.id = equipment_cards.waybill_id
         JOIN equipments ON equipment_cards.equip_id = equipments.id
         JOIN storages ON waybills.storage_id = storages.id;

-- LOGGING
CREATE TABLE log_table
(
    id         SERIAL PRIMARY KEY,
    table_name VARCHAR   NOT NULL,
    action     VARCHAR   NOT NULL,
    row_id     VARCHAR,
    timestamp  TIMESTAMP NOT NULL
);

CREATE OR REPLACE FUNCTION logger()
    RETURNS TRIGGER AS
$$
BEGIN
    IF TG_OP = 'INSERT' THEN
        INSERT INTO log_table (table_name, action, row_id, timestamp)
        VALUES (TG_TABLE_NAME, 'INSERT', NEW.id::VARCHAR, NOW());
    ELSIF TG_OP = 'UPDATE' THEN
        INSERT INTO log_table (table_name, action, row_id, timestamp)
        VALUES (TG_TABLE_NAME, 'UPDATE', NEW.id::VARCHAR, NOW());
    ELSIF TG_OP = 'DELETE' THEN
        INSERT INTO log_table (table_name, action, row_id, timestamp)
        VALUES (TG_TABLE_NAME, 'DELETE', OLD.id::VARCHAR, NOW());
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER waybill_trigger
    AFTER INSERT OR UPDATE OR DELETE
    ON "waybills"
    FOR EACH ROW
EXECUTE FUNCTION logger();


CREATE TRIGGER storage_trigger
    AFTER INSERT OR UPDATE OR DELETE
    ON "storages"
    FOR EACH ROW
EXECUTE FUNCTION logger();

CREATE OR REPLACE FUNCTION calculate_remaining_inventory(
    equipment_name_param VARCHAR,
    storage_id_param INT,
    date_param DATE
)
    RETURNS TABLE
            (
                equipment_name      VARCHAR,
                remaining_inventory BIGINT, -- Change the data type to BIGINT if necessary
                storage_name        VARCHAR
            )
AS
$$
BEGIN
    RETURN QUERY
        SELECT ev.equipment_name,
               SUM(CASE WHEN ev.waybill_name = 'DELIVERY' THEN ev.equip_count ELSE 0 END) -
               SUM(CASE WHEN ev.waybill_name = 'EXPENSE' THEN ev.equip_count ELSE 0 END) AS remaining_inventory,
               st.name
        FROM equipment_view ev
                 JOIN storages st ON ev.storage_id = st.id
        WHERE ev.equipment_name = equipment_name_param
          AND st.id = storage_id_param
          AND ev.waybills_date <= date_param
        GROUP BY ev.equipment_name, st.name;

END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER equipment_type_trigger
    AFTER INSERT OR UPDATE OR DELETE
    ON "equipments"
    FOR EACH ROW
EXECUTE PROCEDURE logger();

INSERT INTO storages (id, name, phone_number)
VALUES (1, 'Storage A', '123-456-7890'),
       (2, 'Storage B', '987-654-3210'),
       (3, 'Main Warehouse', '123-456-7890'),
       (4, 'Eastside Storage', '987-654-3210'),
       (5, 'Central Depot', '555-123-4567'),
       (6, 'West Coast Warehouse', '111-222-3333');

INSERT INTO equipments (name)
VALUES ('Work Wear'),
       ('Instruments'),
       ('Equipment'),
       ('Safety Gear'),
       ('Precision Tools'),
       ('Heavy Machinery'),
       ('Electronics');

INSERT INTO waybills (name, employee_name, employee_position, date, storage_id)
VALUES ('DELIVERY', 'John Doe', 'Manager', '2022-10-28 10:00:00', 1),
       ('EXPENSE', 'Jane Smith', 'Supervisor', '2022-10-27 15:30:00', 2),
       ('DELIVERY', 'Alice Johnson', 'Logistics Coordinator', '2022-10-28 10:00:00', 3),
       ('EXPENSE', 'Bob Thompson', 'Warehouse Manager', '2022-10-29 15:30:00', 4),
       ('DELIVERY', 'Eva Rodriguez', 'Shipping Supervisor', '2022-10-30 12:45:00', 5),
       ('EXPENSE', 'Chris Smith', 'Inventory Analyst', '2022-10-31 08:15:00', 6),
       ('DELIVERY', 'Egor Vasilkov', 'Shipping Supervisor', '2021-07-25 12:45:00', 1),
       ('EXPENSE', 'Warehouse Manager', 'Shipping Supervisor', '2022-01-10 12:45:00', 1),
       ('DELIVERY', 'Jane Smith', 'Shipping Supervisor', '2024-10-30 12:45:00', 1),
       ('DELIVERY', 'Jane Smith', 'Shipping Supervisor', '2024-10-30 12:45:00', 5);

INSERT INTO equipment_cards (equip_count, equip_id, waybill_id)
VALUES (10, 1, 1), -- 10 Work Wear items for Waybill 1
       (5, 2, 2),  -- 5 Instruments for Waybill 1
       (20, 3, 3), -- 20 Equipment items for Waybill 2
       (15, 4, 4), -- 15 Safety Gear items for Waybill 1
       (8, 5, 5),  -- 8 Precision Tools for Waybill 1
       (30, 6, 6), -- 30 Heavy Machinery items for Waybill 2
       (5, 1, 7),  -- 30 Heavy Machinery items for Waybill 2
       (2, 1, 8),  -- 30 Heavy Machinery items for Waybill 2
       (3, 1, 9),  -- 30 Heavy Machinery items for Waybill 2
       (10, 1, 10); -- 30 Heavy Machinery items for Waybill 2