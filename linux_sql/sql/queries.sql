-- Group hosts by hardware info
SELECT cpu_number, id AS host_id, total_mem
FROM host_info
ORDER BY
    cpu_number ASC,
    total_mem DESC
;

-- Average memory usage 
SELECT u.host_id, i.hostname, u.timestamp, 
    AVG(((i.total_mem - u.memory_free) / i.total_mem::float) * 100) OVER ( 
        PARTITION BY (
            DATE_TRUNC('hour', u.timestamp) + 
            DATE_PART('min', u.timestamp)::int / 5 * interval '5 min'
        )
    ) AS avg_used_mem_perc 
FROM host_usage u 
    INNER JOIN host_info i ON u.host_id=i.id
ORDER BY u.host_id 
; 
