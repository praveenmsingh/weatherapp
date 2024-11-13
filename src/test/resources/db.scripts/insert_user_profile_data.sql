INSERT INTO myweatherdb.users (id, name, email) VALUES (1, 'john', 'john@doe.com');

INSERT INTO myweatherdb.cities (id, name, latitude, longitude) VALUES (1, 'Sydney', '-33.87', '151.21');
INSERT INTO myweatherdb.cities (id, name, latitude, longitude) VALUES (2, 'Melbourne', '-37.81', '144.96');
INSERT INTO myweatherdb.cities (id, name, latitude, longitude) VALUES (3, 'Brisbane', '-27.47', '153.03');
INSERT INTO myweatherdb.cities (id, name, latitude, longitude) VALUES (4, 'Canberra', '-35.28', '149.13');

INSERT INTO myweatherdb.weather_profiles (id, name, user_id) VALUES (1, 'Melb&Syd', 1);
INSERT INTO myweatherdb.weather_profiles (id, name, user_id) VALUES (2, 'Brissy&Melb', 1);

INSERT INTO myweatherdb.weather_profiles_cities(id, profile_id, city_id) VALUES (1, 1, 1);
INSERT INTO myweatherdb.weather_profiles_cities(id, profile_id, city_id) VALUES (2, 1, 2);
INSERT INTO myweatherdb.weather_profiles_cities(id, profile_id, city_id) VALUES (3, 2, 3);
INSERT INTO myweatherdb.weather_profiles_cities(id, profile_id, city_id) VALUES (4, 2, 2);

COMMIT;