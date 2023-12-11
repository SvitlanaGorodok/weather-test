DELETE FROM temperature;
DELETE FROM weather_data;
DELETE FROM users;

INSERT INTO weather_data (id, date, lat, lon, city, state, created, updated, version)
        VALUES(10,'1985-01-01', 36.1189, -86.6892, 'Nashville', 'Tennessee', now(), now(), 0);

INSERT INTO temperature (id, data, weather_data_id, created, updated, version)
        VALUES (11, 17.3, 10, now(), now(), 0),
        (12, 16.8, 10, now(), now(), 0);


