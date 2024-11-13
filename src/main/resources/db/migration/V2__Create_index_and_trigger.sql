-- create index for faster lookups
CREATE INDEX index_email ON users(email);
CREATE INDEX index_city ON cities(name);


-- create trigger function for updated_at timestamps
CREATE OR REPLACE FUNCTION update_timestamps_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- create trigger for users table
CREATE TRIGGER set_updated_at
BEFORE UPDATE ON users
FOR EACH ROW
EXECUTE FUNCTION update_timestamps_column();

-- create trigger for weather_profiles
CREATE TRIGGER set_updated_at
BEFORE UPDATE ON weather_profiles
FOR EACH ROW
EXECUTE FUNCTION update_timestamps_column();

-- create trigger for city_weather
CREATE TRIGGER set_updated_at
BEFORE UPDATE ON city_weather
FOR EACH ROW
EXECUTE FUNCTION update_timestamps_column();
