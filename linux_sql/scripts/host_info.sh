#! /bin/bash

# assign CLI arguments to variables (e.g. `psql_host=$1`) 
psql_host=$1
psql_port=$2 
db_name=$3
psql_user=$4
export PGPASSWORD=$5

# parse host hardware specifications using bash cmds
lscpu_out=`lscpu`

cpu_number=$(echo "$lscpu_out" | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu_out"  | egrep "^Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(echo "$lscpu_out" | grep "Model name:" | sed -r 's/Model name:\s{1,}//g')
cpu_mhz=$(echo "$lscpu_out" | grep "CPU MHz:" | sed -r 's/CPU MHz:\s{1,}//g')
l2_cache=$(echo "$lscpu_out" | grep "L2 cache:" | sed -r 's/L2 cache:\s{1,}//g')

hostname=$(hostname -f)
timestamp=$(date '+%Y/%m/%d %H:%M:%S')
total_mem=$(free | awk 'NR == 2 {print $2}')

# construct the INSERT statement
statement="INSERT INTO PUBLIC.host_info VALUES\
(DEFAULT, '$hostname', $cpu_number, '$cpu_architecture', '$cpu_model', $cpu_mhz, ${l2_cache%?}, $total_mem, '$timestamp');"

# execute the INSERT statement through psql CLI tool
psql -h $psql_host -p $psql_port -U $psql_user -w $db_name -c "$statement" 
