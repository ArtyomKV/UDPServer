CREATE TABLE IF NOT EXISTS device
(
    device_id BIGSERIAL PRIMARY KEY,
    mac_address BYTEA NOT NULL
);

CREATE TABLE IF NOT EXISTS geo_data
(
    geo_id    BIGSERIAL PRIMARY KEY,
    device_id BIGSERIAL REFERENCES device (device_id),
    package_number INTEGER NOT NULL,
    location_time BIGINT NOT NULL,
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL
);

create sequence if not exists device_seq start 1;
alter table device alter COLUMN device_id SET DEFAULT nextval('device_seq');

create sequence if not exists geo_data_seq start 1;
alter table geo_data alter COLUMN geo_id SET DEFAULT nextval('geo_data_seq');