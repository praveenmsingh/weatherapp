--DELETE FROM myweatherdb.weather_profiles_cities CASCADE RESTART IDENTITY;
--DELETE FROM myweatherdb.weather_profiles CASCADE RESTART IDENTITY;
TRUNCATE TABLE myweatherdb.users RESTART IDENTITY CASCADE ;
TRUNCATE TABLE myweatherdb.cities RESTART IDENTITY CASCADE;

COMMIT;