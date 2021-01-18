
--     ____                        __
--    /  _/  ___   ___ ___   ____ / /_  ___
--   _/ /   / _ \ (_-</ -_) / __// __/ (_-<
--  /___/  /_//_//___/\__/ /_/   \__/ /___/
--


-- USERS
INSERT INTO users (user_id, cf, first_name, last_name, gender, place_of_birth, date_of_birth, date_of_death)
VALUES (0, 'PRNDRN00E14G964W', 'Adriano', 'Peron', 'M', 'Pozzuoli', TO_TIMESTAMP('14-05-2000', 'dd-mm-yyyy'), NULL); -- [1]
INSERT INTO users
VALUES (0, 'BNRMSM64E15F839R', 'Massimo', 'Benerecetti', 'M', 'Napoli', TO_TIMESTAMP('15-05-1964', 'dd-mm-yyyy'), NULL); -- [2]
INSERT INTO users
VALUES (0, 'BNTPRI94L20A944U', 'Piero', 'Bonatti', 'M', 'Bologna', TO_TIMESTAMP('20-07-1994', 'dd-mm-yyyy'), NULL); -- [3]
INSERT INTO users
VALUES (0, 'LSSDLC75T30H501Z', 'Alessandro', 'De Luca', 'M', 'Roma', TO_TIMESTAMP('30-12-1975', 'dd-mm-yyyy'), NULL); -- [4]
INSERT INTO users
VALUES (0, 'RSSGDU80A02G273R', 'Guido', 'Russo', 'M', 'Palermo', TO_TIMESTAMP('02-01-1980', 'dd-mm-yyyy'), NULL); -- [5]
INSERT INTO users
VALUES (0, 'SRGDRT79L19L245D', 'Sergio', 'Di Martino', 'M', 'Torre Annunziata', TO_TIMESTAMP('19-09-1979', 'dd-mm-yyyy'), NULL); -- [6]
INSERT INTO users
VALUES (0, 'TMBGLL80T06D612P', 'Guglielmo', 'Tamburrini', 'M', 'Firenze', TO_TIMESTAMP('06-12-1980', 'dd-mm-yyyy'), NULL); -- [7]
INSERT INTO users
VALUES (0, 'RCCDNL68S29E506J', 'Daniel', 'Riccio', 'M', 'Lecce', TO_TIMESTAMP('29-11-1968', 'dd-mm-yyyy'), NULL); -- [8]
INSERT INTO users
VALUES (0, 'LSALRT92L17L424H', 'Alberto', 'Aloisio', 'M', 'Trieste', TO_TIMESTAMP('17-07-1992', 'dd-mm-yyyy'), NULL); -- [9]
INSERT INTO users
VALUES (0, 'CTLGNN57T06C352F', 'Giovanni', 'Cutolo', 'M', 'Catanzaro', TO_TIMESTAMP('06-12-1957', 'dd-mm-yyyy'), NULL); -- [10]
INSERT INTO users
VALUES (0, 'BLZWTR01R22B519K', 'Walter', 'Balzano', 'M', 'Campobasso', TO_TIMESTAMP('22-10-2001', 'dd-mm-yyyy'), NULL); -- [11]
INSERT INTO users
VALUES (0, 'MRNNLL52P15D969K', 'Aniello', 'Murano', 'M', 'Genova', TO_TIMESTAMP('15-09-1952', 'dd-mm-yyyy'), NULL); -- [12]
INSERT INTO users
VALUES (0, 'CFFFNC66H53L219I', 'Francesca', 'Cioffi', 'F', 'Torino', TO_TIMESTAMP('13-06-1966', 'dd-mm-yyyy'), NULL); -- [13]
INSERT INTO users
VALUES (0, 'FSTPLA83R67A509V', 'Paola', 'Festa', 'F', 'Avellino', TO_TIMESTAMP('27-10-1983', 'dd-mm-yyyy'), NULL); -- [14]
INSERT INTO users
VALUES (0, 'SGRFNC85M07H703N', 'Francesco', 'Isgro', 'M', 'Salerno', TO_TIMESTAMP('07-08-1985', 'dd-mm-yyyy'), NULL); -- [15]
INSERT INTO users
VALUES (0, 'PRVRRT91E20E932L', 'Roberto', 'Prevete', 'M', 'Marcianise', TO_TIMESTAMP('20-05-1991', 'dd-mm-yyyy'), NULL); -- [16]
INSERT INTO users
VALUES (0, 'CPTLGU79P42L781Y', 'Luigia', 'Caputo', 'F', 'Verona', TO_TIMESTAMP('02-09-1979', 'dd-mm-yyyy'), NULL); -- [17]
INSERT INTO users
VALUES (0, 'TRMFNC69A17F284Q', 'Francesco', 'Tramontano', 'M', 'Molfetta', TO_TIMESTAMP('17-01-1969', 'dd-mm-yyyy'), NULL);
-- [18]


-- RELATIONSHIPS (View)
INSERT INTO relationships_all_v (user_id1, user_id2, type)
VALUES (1, 2, 'work'); -- [1]
INSERT INTO relationships_all_v (user_id1, user_id2, type)
VALUES (4, 17, 'family'); -- [2]
INSERT INTO relationships_all_v (user_id1, user_id2, type)
VALUES (7, 18, 'work');
-- [3]


-- LOCATIONS
INSERT INTO locations (location_id, name, city, category)
VALUES (0, 'San Paolo', 'Napoli', 'entertainment'); -- [1]
INSERT INTO locations
VALUES (0, 'Monte S.Angelo', 'Napoli', 'education'); -- [2]
INSERT INTO locations
VALUES (0, 'Centro Direzionale', 'Napoli', 'business'); -- [3]
INSERT INTO locations
VALUES (0, 'Stazione Termini', 'Roma', 'transportation'); -- [4]
INSERT INTO locations
VALUES (0, 'Piazza Affari', 'Milano', 'business');
-- [5]


-- CONTACTS (View)
INSERT INTO contacts_all_v (user_id1, user_id2, location_id, date_received)
VALUES (1, 5, 1, TO_TIMESTAMP('01-11-2020 17:30:14', 'dd-mm-yyyy hh24:mi:ss')); -- [1]
INSERT INTO contacts_all_v
VALUES (6, 2, 2, TO_TIMESTAMP('02-11-2020 09:30:14', 'dd-mm-yyyy hh24:mi:ss')); -- [2]
INSERT INTO contacts_all_v
VALUES (3, 7, 4, TO_TIMESTAMP('03-11-2020 20:30:14', 'dd-mm-yyyy hh24:mi:ss')); -- [3]
INSERT INTO contacts_all_v
VALUES (12, 14, 3, TO_TIMESTAMP('09-11-2020 12:27:00', 'dd-mm-yyyy hh24:mi:ss')); -- [4]
INSERT INTO contacts_all_v
VALUES (13, 15, 1, TO_TIMESTAMP('11-11-2020 10:30:14', 'dd-mm-yyyy hh24:mi:ss')); -- [5]
INSERT INTO contacts_all_v
VALUES (10, 13, 3, TO_TIMESTAMP('12-11-2020 14:30:14', 'dd-mm-yyyy hh24:mi:ss')); -- [6]
INSERT INTO contacts_all_v
VALUES (11, 8, 2, TO_TIMESTAMP('13-11-2020 14:50:14', 'dd-mm-yyyy hh24:mi:ss')); -- [7]
INSERT INTO contacts_all_v
VALUES (4, 9, 4, TO_TIMESTAMP('15-11-2020 08:50:14', 'dd-mm-yyyy hh24:mi:ss')); -- [8]
INSERT INTO contacts_all_v
VALUES (14, 2, 3, TO_TIMESTAMP('18-11-2020 19:50:14', 'dd-mm-yyyy hh24:mi:ss')); -- [9]
INSERT INTO contacts_all_v
VALUES (5, 2, 5, TO_TIMESTAMP('18-11-2020 11:21:14', 'dd-mm-yyyy hh24:mi:ss')); -- [10]
INSERT INTO contacts_all_v
VALUES (4, 11, 3, TO_TIMESTAMP('23-11-2020 10:11:14', 'dd-mm-yyyy hh24:mi:ss')); -- [11]
INSERT INTO contacts_all_v
VALUES (12, 10, 3, TO_TIMESTAMP('24-11-2020 17:01:33', 'dd-mm-yyyy hh24:mi:ss')); -- [12]
INSERT INTO contacts_all_v
VALUES (9, 8, 2, TO_TIMESTAMP('25-11-2020 21:10:00', 'dd-mm-yyyy hh24:mi:ss')); -- [13]
INSERT INTO contacts_all_v
VALUES (7, 1, 4, TO_TIMESTAMP('28-11-2020 20:13:27', 'dd-mm-yyyy hh24:mi:ss')); -- [14]
INSERT INTO contacts_all_v
VALUES (3, 6, 1, TO_TIMESTAMP('29-11-2020 15:31:07', 'dd-mm-yyyy hh24:mi:ss')); -- [15]
INSERT INTO contacts_all_v
VALUES (15, 13, 1, TO_TIMESTAMP('29-11-2020 12:22:58', 'dd-mm-yyyy hh24:mi:ss')); -- [16]
INSERT INTO contacts_all_v
VALUES (16, 5, 3, TO_TIMESTAMP('30-11-2020 16:02:38', 'dd-mm-yyyy hh24:mi:ss'));
-- [17]
