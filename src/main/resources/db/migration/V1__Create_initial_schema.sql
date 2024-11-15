CREATE SCHEMA IF NOT EXISTS myweatherdb;

CREATE TABLE cities (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(50) NOT NULL UNIQUE,
  latitude VARCHAR(10) NOT NULL,
  longitude VARCHAR(10) NOT NULL
);

CREATE TABLE city_weather (
  city_id BIGINT PRIMARY KEY,
  main VARCHAR(20) NOT NULL,
  wind_speed VARCHAR(10) NOT NULL,
  temperature VARCHAR(10) NOT NULL,
  feels_like VARCHAR(10) NOT NULL,
  temperature_min VARCHAR(10) NOT NULL,
  temperature_max VARCHAR(10) NOT NULL,
  pressure VARCHAR(10) NOT NULL,
  humidity VARCHAR(10) NOT NULL,
  ground_level VARCHAR(10) NOT NULL,
  sea_level VARCHAR(10) NOT NULL,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

  FOREIGN KEY (city_id) REFERENCES cities(id) ON DELETE CASCADE
);

CREATE TABLE users (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(40) NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE weather_profiles (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(40) NOT NULL,
  user_id BIGINT NOT NULL,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE weather_profiles_cities (
  id BIGSERIAL PRIMARY KEY,
  profile_id BIGINT NOT NULL,
  city_id BIGINT NOT NULL,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (profile_id) REFERENCES weather_profiles(id) ON DELETE CASCADE,
  FOREIGN KEY (city_id) REFERENCES cities(id) ON DELETE CASCADE

);
