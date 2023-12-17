BEGIN;
INSERT INTO passagers(id, surname, firstname, email_address)
VALUES(NEXTVAL('passagers_sequence_in_database'), 'g1nom', 'g1prenom','mail@mail.com');
COMMIT;