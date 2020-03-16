--create a `host_agent` database if not exist
--assume the database is created
--CREATE DATABASE host_agent;

--switch to `host_agent`
\connect host_agent 

--create `host_info` table if not exist
CREATE TABLE PUBLIC.host_info 
( 
    id                  SERIAL PRIMARY KEY,
    hostname            VARCHAR NOT NULL UNIQUE,
    cpu_number          SMALLINT NOT NULL,
    cpu_architecture    VARCHAR NOT NULL,
    cpu_model           VARCHAR NOT NULL,
    cpu_mhz             REAL NOT NULL,
    L2_cache            INTEGER NOT NULL,
    total_mem           INTEGER NOT NULL,
    "timestamp"         TIMESTAMP NOT NULL
);

--create `host_usage` table if not exist
CREATE TABLE PUBLIC.host_usage 
( 
    "timestamp"     TIMESTAMP NOT NULL, 
    host_id         SERIAL NOT NULL REFERENCES PUBLIC.host_info(id), 
    memory_free     INTEGER NOT NULL,
    cpu_idle        SMALLINT NOT NULL,
    cpu_kernel      SMALLINT NOT NULL,
    disk_io         INTEGER NOT NULL,
    disk_available  INTEGER NOT NULL,
    PRIMARY KEY("timestamp", host_id)
); 
