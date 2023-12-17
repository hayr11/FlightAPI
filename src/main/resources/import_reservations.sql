BEGIN;
INSERT INTO reservations(id, flight_id, passenger_id)
VALUES(NEXTVAL('reservations_sequence_in_database'), 1, 1);
COMMIT;