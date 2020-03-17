-- Group hosts by hardware info
SELECT 
    cpu_number, 
    id AS host_id,
    total_mem
FROM 
    host_info
ORDER BY
    cpu_number ASC,
    total_mem DESC
;

-- Average memory usage 
SELECT 
    u.host_id,
    i.hostname,
    u.timestamp, 
    AVG(((i.total_mem - u.memory_free) / i.total_mem::float) * 100) OVER ( 
        PARTITION BY (
            DATE_TRUNC('hour', u.timestamp) + 
            DATE_PART('min', u.timestamp)::int / 5 * interval '5 min'
        )
    ) AS avg_used_mem_perc 
FROM 
    host_usage u INNER JOIN host_info i ON u.host_id = i.id
ORDER BY
    u.host_id 
; 

-- Detect node failure
/*
CREATE TEMP TABLE test 
( 
    host_id             SMALLINT NOT NULL,
    "timestamp"         TIMESTAMP NOT NULL,
    is_failed           VARCHAR NOT NULL,
    PRIMARY KEY("timestamp", host_id)
);

INSERT INTO test VALUES  
    (1, '2020-03-11 10:23:01', 'ok'),
    (1, '2020-03-11 10:24:01', 'failed'),
    (1, '2020-03-11 10:25:01', 'failed'),
    (1, '2020-03-11 10:26:01', 'failed'),
    (1, '2020-03-11 10:27:01', 'ok')
;
*/

WITH tmp AS (
    SELECT
        host_id,
        timestamp,
        SUM(
            CASE 
                WHEN is_failed = 'failed' THEN 1 ELSE 0 
            END
        ) OVER (
            PARTITION BY host_id 
            ORDER BY timestamp 
            ROWS BETWEEN CURRENT ROW AND 2 FOLLOWING
        ) AS failed_times 
    FROM
        test
) 
SELECT
    host_id,
    timestamp,
    failed_times 
FROM
    tmp
WHERE
    failed_times >= 3
;
