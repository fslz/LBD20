--   ______         __    __
--  /_  __/ ___ _  / /   / / ___   ___
--   / /   / _ `/ / _ \ / / / -_) (_-<
--  /_/    \_,_/ /_.__//_/  \__/ /___/
--


CREATE TABLE users
(
    user_id       INTEGER      NOT NULL,
    first_name    VARCHAR2(25) NOT NULL,
    last_name     VARCHAR2(25) NOT NULL,
    gender        CHAR(1)      NOT NULL,
    date_of_birth DATE         NOT NULL,
    date_of_death DATE,

    CONSTRAINT users_pk PRIMARY KEY (user_id),
    CONSTRAINT users_ck1 CHECK (date_of_death > date_of_birth),
    CONSTRAINT users_ck2 CHECK (gender IN ('M', 'F'))
);


CREATE TABLE relationships
(
    relationship_id INTEGER     NOT NULL,
    type            VARCHAR2(9) NOT NULL,

    CONSTRAINT relationships_pk PRIMARY KEY (relationship_id),
    CONSTRAINT relationships_ck CHECK (type IN ('family', 'work'))
);


CREATE TABLE membership
(
    relationship_id INTEGER NOT NULL,
    user_id         INTEGER NOT NULL,

    CONSTRAINT membership_pk PRIMARY KEY (relationship_id, user_id)
);


CREATE TABLE contacts
(
    contact_id  INTEGER NOT NULL,
    location_id INTEGER NOT NULL,

    CONSTRAINT contacts_pk PRIMARY KEY (contact_id)
);


CREATE TABLE participants
(
    contact_id    INTEGER NOT NULL,
    user_id       INTEGER NOT NULL,
    date_received DATE    NOT NULL,

    CONSTRAINT participants_pk PRIMARY KEY (contact_id, user_id),
    CONSTRAINT participants_uk UNIQUE (user_id, date_received)
);


CREATE TABLE locations
(
    location_id INTEGER      NOT NULL,
    name        VARCHAR2(25) NOT NULL,
    city        VARCHAR2(25) NOT NULL,
    category    VARCHAR(14)  NOT NULL,

    CONSTRAINT locations_pk PRIMARY KEY (location_id),
    CONSTRAINT locations_uk UNIQUE (name),
    CONSTRAINT locations_ck CHECK (category IN
                                   ('recreational', 'entertainment', 'religious', 'education', 'healthcare',
                                    'transportation', 'business'))
);

/*
 CATEGORIES / CONTEXT:
 recreational: hotel, restaurants, bar, public meeting
 entertainment: movie theatre, theatre, stadium, concert area, ski resort.
 religious: churches, monasteries, sacred places
 education: university, school, library
 healthcare: hospital, private clinic
 transportation: airport, train station, bus station, metro station
 business: barber shop, private stores
*/


CREATE TABLE swabs
(
    swab_id     INTEGER,
    user_id     INTEGER NOT NULL,
    date_result DATE    NOT NULL,
    positivity  CHAR(1) NOT NULL,

    CONSTRAINT swabs_pk PRIMARY KEY (swab_id),
    CONSTRAINT swabs_ck1 CHECK ( positivity IN ('y', 'n') ),
    CONSTRAINT swabs_uk UNIQUE (user_id, date_result)
);


CREATE TABLE serologicals
(
    serological_id INTEGER,
    user_id        INTEGER NOT NULL,
    date_result    DATE    NOT NULL,
    igm            CHAR(1) NOT NULL,
    igg            CHAR(1) NOT NULL,

    CONSTRAINT serologicals_pk PRIMARY KEY (serological_id),
    CONSTRAINT serologicals_uk UNIQUE (user_id, date_result),
    CONSTRAINT serologicals_ck1 CHECK ( igm IN ('positive', 'negative') ),
    CONSTRAINT serologicals_ck2 CHECK ( igg IN ('positive', 'negative') )
);


CREATE TABLE health_checks
(
    health_check_id      INTEGER NOT NULL,
    user_id              INTEGER NOT NULL,
    date_of_check        DATE    NOT NULL,
    fever                CHAR(1) NOT NULL,
    respiratory_disorder CHAR(1) NOT NULL,
    smell_taste_disorder CHAR(1) NOT NULL,

    CONSTRAINT health_checks_ck1 CHECK (fever IN ('y', 'n')),
    CONSTRAINT health_checks_ck2 CHECK (respiratory_disorder IN ('y', 'n')),
    CONSTRAINT health_checks_ck3 CHECK (smell_taste_disorder IN ('y', 'n')),
    CONSTRAINT health_checks_uk UNIQUE (user_id, date_of_check)
);


--     ___    __  __                  ______         __    __
--    / _ |  / / / /_ ___   ____     /_  __/ ___ _  / /   / / ___   ___
--   / __ | / / / __// -_) / __/      / /   / _ `/ / _ \ / / / -_) (_-<
--  /_/ |_|/_/  \__/ \__/ /_/        /_/    \_,_/ /_.__//_/  \__/ /___/
--


ALTER TABLE membership
    ADD
        (
        CONSTRAINT membership_relationships_fk FOREIGN KEY (relationship_id) REFERENCES relationships (relationship_id) ON DELETE CASCADE,
        CONSTRAINT membership_users_fk FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
        );

ALTER TABLE contacts
    ADD
        (
        CONSTRAINT contacts_locations_fk FOREIGN KEY (location_id) REFERENCES locations (location_id) ON DELETE CASCADE
        );

ALTER TABLE participants
    ADD
        (
        CONSTRAINT participants_contacts_fk FOREIGN KEY (contact_id) REFERENCES contacts (contact_id) ON DELETE CASCADE,
        CONSTRAINT participants_users_fk FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
        );

ALTER TABLE swabs
    ADD (
        CONSTRAINT swabs_users_fk FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
        );

ALTER TABLE health_checks
    ADD (
        CONSTRAINT hc_users_fk FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
        );

ALTER TABLE serologicals
    ADD (
        CONSTRAINT serological_users_fk FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
        );


--     ____
--    / __/ ___  ___ _ __ __ ___   ___  ____ ___   ___
--   _\ \  / -_)/ _ `// // // -_) / _ \/ __// -_) (_-<
--  /___/  \__/ \_, / \_,_/ \__/ /_//_/\__/ \__/ /___/
--               /_/


CREATE SEQUENCE users_seq;
CREATE SEQUENCE relationships_seq;
CREATE SEQUENCE contacts_seq;
CREATE SEQUENCE locations_seq;
CREATE SEQUENCE swabs_seq;
CREATE SEQUENCE serologicals_seq;
CREATE SEQUENCE health_checks_seq;


--    _   __   _
--   | | / /  (_) ___  _    __  ___
--   | |/ /  / / / -_)| |/|/ / (_-<
--   |___/  /_/  \__/ |__,__/ /___/
--


-- all contacts between users (user_id1, user_id2, location_id, date_received) [used when inserting a new contact]
CREATE OR REPLACE VIEW contacts_all_v AS
SELECT p1.user_id       AS user_id1,
       p2.user_id       AS user_id2,
       c.location_id    AS location_id,
       p1.date_received AS date_received
FROM contacts c
         JOIN participants p1 ON c.contact_id = p1.contact_id
         JOIN participants p2 ON p1.contact_id = p2.contact_id
WHERE p1.user_id <> p2.user_id
  AND p1.user_id < p2.user_id;


-- all relationships (user_id1, user_id2, relationship type)
CREATE OR REPLACE VIEW relationships_all_v AS
SELECT m1.user_id AS user_id1, m2.user_id AS user_id2, r.type
FROM relationships r
         JOIN membership m1 ON r.relationship_id = m1.relationship_id
         JOIN membership m2 ON m1.relationship_id = m2.relationship_id;


--   ______         _
--  /_  __/  ____  (_)  ___ _  ___ _ ___   ____  ___
--   / /    / __/ / /  / _ `/ / _ `// -_) / __/ (_-<
--  /_/    /_/   /_/   \_, /  \_, / \__/ /_/   /___/
--                    /___/  /___/


CREATE OR REPLACE TRIGGER users_tr
    BEFORE INSERT
    ON users
    FOR EACH ROW
BEGIN
    :new.user_id := users_seq.nextval;
END;


CREATE OR REPLACE TRIGGER locations_tr
    BEFORE INSERT
    ON locations
    FOR EACH ROW
BEGIN
    :new.location_id := locations_seq.nextval;
END;


-- used when inserting a new contact between two users
CREATE OR REPLACE TRIGGER contacts_all_v_tr
    INSTEAD OF INSERT
    ON contacts_all_v
    FOR EACH ROW
DECLARE
    l_contact_pk INTEGER;
BEGIN

    FOR user_row IN (SELECT u.date_of_birth, u.date_of_death
                     FROM users u
                     WHERE u.user_id = :new.user_id1
                        OR u.user_id = :new.user_id2)
        LOOP
            -- check, for both users, if the date of contact is between the date_of_birth and date_of_death (excluding extremes)
            IF (:new.date_received < user_row.date_of_birth) THEN
                RAISE_APPLICATION_ERROR(-20001, 'date received < date of birth');
            ELSIF (user_row.date_of_death IS NOT NULL) THEN
                IF (:new.date_received > user_row.date_of_death) THEN
                    RAISE_APPLICATION_ERROR(-20001, 'date received > date of death');
                END IF;
            END IF;
        END LOOP;

    l_contact_pk := contacts_seq.nextval;
    INSERT INTO contacts VALUES (l_contact_pk, :new.location_id);
    INSERT INTO participants VALUES (l_contact_pk, :new.user_id1, :new.date_received);
    INSERT INTO participants VALUES (l_contact_pk, :new.user_id2, :new.date_received);
END;


-- checks if user_id1 and user_id2 don't share already the same type of relationship
CREATE OR REPLACE TRIGGER relationships_all_v_tr
    INSTEAD OF INSERT
    ON relationships_all_v
    FOR EACH ROW
DECLARE
    l_relationship_pk     INTEGER;
    l_shared_relationship INTEGER;
BEGIN
    -- counts the number of relationships of the same type between user_id1 and user_id2
    SELECT COUNT(*)
    INTO l_shared_relationship
    FROM relationships r
             JOIN membership m1 ON r.relationship_id = m1.relationship_id
             JOIN membership m2 ON m1.relationship_id = m2.relationship_id
    WHERE m1.user_id = :new.user_id1
      AND m2.user_id = :new.user_id2
      AND r.type = :new.type;
    IF (l_shared_relationship > 0) THEN
        RAISE_APPLICATION_ERROR(-20001, 'a relationship of this type between these two users already exists');
    ELSE
        l_relationship_pk := relationships_seq.nextval;
        INSERT INTO relationships VALUES (l_relationship_pk, :new.type);
        INSERT INTO membership VALUES (l_relationship_pk, :new.user_id1);
        INSERT INTO membership VALUES (l_relationship_pk, :new.user_id2);
    END IF;
END;


CREATE OR REPLACE TRIGGER swabs_tr
    BEFORE INSERT
    ON swabs
    FOR EACH ROW
DECLARE
    l_date_of_birth DATE;
    l_date_of_death DATE;
BEGIN
    SELECT date_of_birth, date_of_death
    INTO l_date_of_birth, l_date_of_death
    FROM users
    WHERE user_id = :new.user_id;
    IF (:new.date_result < l_date_of_birth) THEN
        RAISE_APPLICATION_ERROR(-20001, 'date result < date of birth');
    ELSIF (l_date_of_death IS NOT NULL) THEN
        IF (:new.date_result < l_date_of_death) THEN
            RAISE_APPLICATION_ERROR(-20001, 'date result > date of death');
        END IF;
    END IF;
    :new.swab_id := swabs_seq.nextval;
END;


CREATE OR REPLACE TRIGGER serologicals_tr
    BEFORE INSERT
    ON serologicals
    FOR EACH ROW
DECLARE
    l_date_of_birth  DATE;
    l_date_of_death  DATE;
BEGIN
    SELECT date_of_birth, date_of_death
    INTO l_date_of_birth, l_date_of_death
    FROM users
    WHERE user_id = :new.user_id;
    IF (:new.date_result < l_date_of_birth) THEN
        RAISE_APPLICATION_ERROR(-20001, 'date result < date of birth');
    ELSIF (l_date_of_death IS NOT NULL) THEN
        IF (:new.date_result < l_date_of_death) THEN
            RAISE_APPLICATION_ERROR(-20001, 'date result > date of death');
        END IF;
    END IF;
    :new.serological_id := serologicals_seq.nextval;
END;


CREATE OR REPLACE TRIGGER health_checks_tr
    BEFORE INSERT
    ON health_checks
    FOR EACH ROW
DECLARE
    l_date_of_birth   DATE;
    l_date_of_death   DATE;
BEGIN
    SELECT date_of_birth, date_of_death
    INTO l_date_of_birth, l_date_of_death
    FROM users
    WHERE user_id = :new.user_id;
    IF (:new.date_of_check < l_date_of_birth) THEN
        RAISE_APPLICATION_ERROR(-20001, 'date of check < date of birth');
    ELSIF (l_date_of_death IS NOT NULL) THEN
        IF (:new.date_of_check < l_date_of_death) THEN
            RAISE_APPLICATION_ERROR(-20001, 'date of check > date of death');
        END IF;
    END IF;
    :new.health_check_id := health_checks_seq.nextval;
END;


--     ____                   __    _
--    / __/ __ __  ___  ____ / /_  (_) ___   ___   ___
--   / _/  / // / / _ \/ __// __/ / / / _ \ / _ \ (_-<
--  /_/    \_,_/ /_//_/\__/ \__/ /_/  \___//_//_//___/
--


--     ___                           __
--    / _ \  ____ ___  ____ ___  ___/ / __ __  ____ ___   ___
--   / ___/ / __// _ \/ __// -_)/ _  / / // / / __// -_) (_-<
--  /_/    /_/   \___/\__/ \__/ \_,_/  \_,_/ /_/   \__/ /___/
--


--     ___
--    / _ \  ____ ___    ___   ___
--   / // / / __// _ \  / _ \ (_-<
--  /____/ /_/   \___/ / .__//___/
--                    /_/


DROP SEQUENCE users_seq;
DROP SEQUENCE relationships_seq;
DROP SEQUENCE locations_seq;
DROP SEQUENCE contacts_seq;
DROP SEQUENCE swabs_seq;
DROP SEQUENCE serologicals_seq;
DROP SEQUENCE health_checks_seq;
DROP TABLE membership CASCADE CONSTRAINTS;
DROP TABLE relationships CASCADE CONSTRAINTS;
DROP TABLE participants CASCADE CONSTRAINTS;
DROP TABLE contacts CASCADE CONSTRAINTS;
DROP TABLE locations CASCADE CONSTRAINTS;
DROP TABLE swabs CASCADE CONSTRAINTS;
DROP TABLE health_checks CASCADE CONSTRAINTS;
DROP TABLE users CASCADE CONSTRAINTS;
DROP TABLE serologicals CASCADE CONSTRAINTS;
DROP VIEW contacts_all_v;
DROP VIEW relationships_all_v;