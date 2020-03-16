#! /bin/bash

# assign CLI arguments to variables
if [ "$#" -ne 5 ]; then
    >&2 echo "error: illegal number of parameters, expect 5"
    exit 1
fi

psql_host=$1
psql_port=$2 
db_name=$3
psql_user=$4
export PGPASSWORD=$5

# parse server CPU and memory usage data using bash scripts
timestamp=$(date '+%Y/%m/%d %H:%M:%S')
vmstat_out=`vmstat --unit M`
memory_free=$(echo "$vmstat_out" | awk 'NR == 3 {print $4}')
cpu_idle=$(echo "$vmstat_out" | awk 'NR == 3 {print $15}')
cpu_kernel=$(echo "$vmstat_out" | awk 'NR == 3 {print $14}')
disk_io=$(vmstat -d | awk '$1 ~ /^ *sda/ {print $11}')
disk_available=$(df -m | awk '$1 ~ /^ *\/dev\/sda1/ {print $4}')

# construct the INSERT statement
statement="INSERT INTO PUBLIC.host_usage VALUES ('$timestamp',\
(SELECT id FROM PUBLIC.host_info WHERE hostname='$(hostname -f)'),\
$memory_free, $cpu_idle, $cpu_kernel, $disk_io, $disk_available);"

# execute the INSERT statement
psql -h $psql_host -p $psql_port -U $psql_user -w $db_name -c "$statement" 
if [ "$?" -ne 0 ]; then
    exit 1
fi

tput setaf 2
echo "Success!"
exit 0
