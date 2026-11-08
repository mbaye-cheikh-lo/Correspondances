-- Migration Flyway : à placer dans src/main/resources/db/migration/V2__donnees_50000.sql

-- 50 000 réservations générées avec GENERATE_SERIES (et les tables qui vont avec)
--
-- Pour changer le volume, modifiez seulement les nombres marqués  <-- à changer
-- (nombre de réservations, de voyageurs, de trains, de jours).
-- Limite pour éviter les doublons de siège : réservations <= circulations x 80 sièges.
-- Ici : 1 800 circulations x 80 = 144 000 places possibles, donc 50 000 passe sans problème.
--
-- À coller dans pgAdmin : clic droit sur la base "correspondances" > Query Tool > F5
-- Les tables doivent être vides avant (voir TRUNCATE dans le message d'explication).

-- ============================================================
-- 1. Tables écrites à la main (peu de lignes)
-- ============================================================
INSERT INTO service_calendar (calendar_id, calendar_type, start_date, end_date) VALUES
(1, 'SEMAINE',  '2026-01-01', '2027-12-31'),
(2, 'WEEK_END', '2026-01-01', '2027-12-31'),
(3, 'VACANCES', '2026-07-01', '2026-08-31');

INSERT INTO service_exception (exception_id, exception_date, exception_type, calendar_id) VALUES
(1, '2026-11-11', 'FERIE', 1),
(2, '2026-12-25', 'FERIE', 1),
(3, '2027-01-01', 'FERIE', 1);

INSERT INTO travel_class (class_id, class_name, description) VALUES
(1, '1ère classe', 'Place spacieuse avec prise électrique'),
(2, '2nde classe', 'Place standard'),
(3, 'Business',    'Espace affaires, repas inclus');

INSERT INTO traveler_profile (profile_id, profile_name) VALUES
(1, 'Adulte'),
(2, 'Jeune'),
(3, 'Senior'),
(4, 'Enfant'),
(5, 'Étudiant');

INSERT INTO fare_period (period_id, period_name) VALUES
(1, 'Heures pleines'),
(2, 'Heures creuses'),
(3, 'Week-end');

INSERT INTO distance_band (distance_band_id, min_distance_km, max_distance_km) VALUES
(1,   0.00,   50.00),
(2,  50.01,  150.00),
(3, 150.01,  400.00),
(4, 400.01, 1000.00);

-- ============================================================
-- 2. Trains : 20 trains
-- ============================================================
INSERT INTO train (train_id)
SELECT g
FROM generate_series(1, 20) AS g;                       -- <-- à changer : nombre de trains

-- ============================================================
-- 3. Circulations : chaque train circule chaque jour, du 1er novembre 2026 au 29 janvier 2027
--    20 trains x 90 jours = 1 800 circulations
-- ============================================================
INSERT INTO circulation (circulation_id, circulation_date, train_id, calendar_id)
SELECT ROW_NUMBER() OVER (ORDER BY d, t.train_id),
       d::date,
       t.train_id,
       CASE WHEN EXTRACT(DOW FROM d::date) IN (0, 6) THEN 2 ELSE 1 END
FROM generate_series(DATE '2026-11-01', DATE '2027-01-29', INTERVAL '1 day') AS d   -- <-- à changer : période
CROSS JOIN train t;

-- ============================================================
-- 4. Voitures : 4 par train (V1 à V4) = 80 voitures
-- ============================================================
INSERT INTO coach (coach_id, coach_number, train_id)
SELECT (t.train_id - 1) * 4 + n,
       'V' || n,
       t.train_id
FROM train t
CROSS JOIN generate_series(1, 4) AS n;

-- ============================================================
-- 5. Sièges : 20 par voiture = 80 par train = 1 600 sièges
--    V1 = 1ère classe (classe 1), V2 à V4 = 2nde classe (classe 2)
-- ============================================================
INSERT INTO seat (seat_id, seat_number, coach_id, class_id)
SELECT (c.coach_id - 1) * 20 + s,
       s::text,
       c.coach_id,
       CASE WHEN c.coach_number = 'V1' THEN 1 ELSE 2 END
FROM coach c
CROSS JOIN generate_series(1, 20) AS s;

-- ============================================================
-- 6. Voyageurs : 10 000 voyageurs
--    Un voyageur sur 10 n'a pas de profil (NULL)
-- ============================================================
INSERT INTO traveler (traveler_id, first_name, last_name, email, phone, profile_id)
SELECT g,
       'Prenom' || g,
       'Nom' || g,
       'voyageur' || g || '@mail.com',
       '06' || LPAD((g % 100000000)::text, 8, '0'),
       CASE WHEN g % 10 = 0 THEN NULL ELSE (g % 5) + 1 END
FROM generate_series(1, 10000) AS g;                    -- <-- à changer : nombre de voyageurs

-- ============================================================
-- 7. Tarifs : 3 périodes x 4 tranches x 3 classes x 5 profils = 180 tarifs
-- ============================================================
INSERT INTO fare (fare_id, price, period_id, distance_band_id, class_id, profile_id)
SELECT ROW_NUMBER() OVER (ORDER BY p.period_id, d.distance_band_id, c.class_id, pr.profile_id),
       ROUND((10 + d.distance_band_id * 20)
             * (1 + (c.class_id - 1) * 0.5)
             * (1 - (p.period_id - 1) * 0.1)
             * (1 - (pr.profile_id - 1) * 0.1), 2),
       p.period_id,
       d.distance_band_id,
       c.class_id,
       pr.profile_id
FROM fare_period p
CROSS JOIN distance_band d
CROSS JOIN travel_class c
CROSS JOIN traveler_profile pr;

-- ============================================================
-- 8. Réservations : 50 000
--    g va de 1 à 50 000. Les opérateurs :
--      %  = reste de la division   (7 % 3 = 1)  -> fait "tourner" les valeurs
--      /  = division entière       (7 / 3 = 2)  -> change de valeur tous les N pas
--
--    Pour ne jamais réserver deux fois le même siège sur la même circulation :
--      - circulation : ((g - 1) % 1800) + 1   -> on parcourt les 1 800 circulations
--      - n° de siège : (g - 1) / 1800          -> quand on a fait le tour, on passe au siège suivant
--    (g - 1) / 1800 va de 0 à 27 : chaque circulation reçoit au plus 28 réservations.
--
--    Date de réservation : entre 1 et 30 jours AVANT le jour du voyage.
--    Prix payé : le prix du tarif choisi.
-- ============================================================
INSERT INTO booking (booking_id, booking_date, status, price_paid, traveler_id, circulation_id, seat_id, fare_id)
SELECT g,
       (c.circulation_date - ((g % 30) + 1))::timestamp + (g % 24) * INTERVAL '1 hour',
       CASE WHEN g % 10 = 0 THEN 'CANCELLED'
            WHEN g % 10 = 5 THEN 'PENDING'
            ELSE 'CONFIRMED' END,
       f.price,
       ((g - 1) % 10000) + 1,                              -- <-- doit être <= nombre de voyageurs
       c.circulation_id,
       (c.train_id - 1) * 80 + ((g - 1) / 1800) + 1,
       f.fare_id
FROM generate_series(1, 50000) AS g                        -- <-- à changer : nombre de réservations
JOIN circulation c ON c.circulation_id = ((g - 1) % 1800) + 1
JOIN fare f ON f.period_id = ((g - 1) % 3) + 1
           AND f.distance_band_id = ((g - 1) % 4) + 1
           AND f.class_id = CASE WHEN (g - 1) / 1800 < 20 THEN 1 ELSE 2 END
           AND f.profile_id = ((g - 1) % 5) + 1;

-- ============================================================
-- 9. Remet les compteurs d'identifiants à jour
-- ============================================================
SELECT setval(pg_get_serial_sequence('service_calendar', 'calendar_id'),   (SELECT MAX(calendar_id)    FROM service_calendar));
SELECT setval(pg_get_serial_sequence('service_exception', 'exception_id'), (SELECT MAX(exception_id)   FROM service_exception));
SELECT setval(pg_get_serial_sequence('train', 'train_id'),                 (SELECT MAX(train_id)       FROM train));
SELECT setval(pg_get_serial_sequence('circulation', 'circulation_id'),     (SELECT MAX(circulation_id) FROM circulation));
SELECT setval(pg_get_serial_sequence('travel_class', 'class_id'),          (SELECT MAX(class_id)       FROM travel_class));
SELECT setval(pg_get_serial_sequence('coach', 'coach_id'),                 (SELECT MAX(coach_id)       FROM coach));
SELECT setval(pg_get_serial_sequence('seat', 'seat_id'),                   (SELECT MAX(seat_id)        FROM seat));
SELECT setval(pg_get_serial_sequence('traveler_profile', 'profile_id'),    (SELECT MAX(profile_id)     FROM traveler_profile));
SELECT setval(pg_get_serial_sequence('traveler', 'traveler_id'),           (SELECT MAX(traveler_id)    FROM traveler));
SELECT setval(pg_get_serial_sequence('fare_period', 'period_id'),          (SELECT MAX(period_id)      FROM fare_period));
SELECT setval(pg_get_serial_sequence('distance_band', 'distance_band_id'), (SELECT MAX(distance_band_id) FROM distance_band));
SELECT setval(pg_get_serial_sequence('fare', 'fare_id'),                   (SELECT MAX(fare_id)        FROM fare));
SELECT setval(pg_get_serial_sequence('booking', 'booking_id'),             (SELECT MAX(booking_id)     FROM booking));
