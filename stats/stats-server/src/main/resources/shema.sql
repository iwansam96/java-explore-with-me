CREATE TABLE IF NOT EXISTS events (
    event_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    app VARCHAR(128) NOT NULL,
    uri VARCHAR(1024) NOT NULL,
    ip VARCHAR(64) NOT NULL,
    event_created timestamp
);