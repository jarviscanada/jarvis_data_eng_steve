# Linux Cluster Monitoring Agent

## Introduction
This is an internal tool for Linux Cluster Administration (LCA) team that records the hardware specifications of servers and monitor node resource usages (e.g. CPU/Memory) in realtime. The collected data will be stored in an RDBMS database. Users are able to use the data to generate some reports for future resource planning purposes (e.g. add/remove servers).

## Architecture and Design
<img src="../assets/arch.png" alt="drawing" width="800"/>

* A [PostgreSQL](https://www.postgresql.org/) instance is used to persist all the data.

* The bash agent gathers server usage data, and then insert into the psql instance. The agent will be installed on every host/server/node. The agent consists of two bash scripts:

  * `host_info.sh` collects the host hardware info and insert it into the database. It will be run only once at the installation time.

  * `host_usage.sh` collects the current host usage (CPU and Memory) and then insert into the database. It will be triggered by the crontab job every minute.

## Usage

### Initialize database and tables

#### Start psql docker container 
```sh
./scripts/psql_docker.sh start [db_passwd]
```

#### Stop psql docker container 
```sh
./scripts/psql_docker.sh stop
```

#### Create tables
```sh
psql -h localhost -U postgres -W -f sql/ddl.sql
```

### Record hardware specification
```sh
# Usage
./scripts/host_info.sh [psql_host] [psql_port] [db_name] [psql_user] [psql_passwd]
# Example
./scripts/host_info.sh localhost 5432 host_agent postgres mypassword
```

### Collect server usage data
```sh
# Usage
./scripts/host_usage.sh [psql_host] [psql_port] [db_name] [psql_user] [psql_passwd]
# Example
./scripts/host_usage.sh localhost 5432 host_agent postgres mypassword
```

### Real time monitoring
Use [crontab](https://crontab.guru/) to execute `host_usage.sh` periodically:
```sh
# Edit crontab jobs
crontab -e
# Add this line to crontab, the usage data will be collected every minute, adjust your schedule accordingly
* * * * * bash <path_to_project>/linux_sql/scripts/host_usage.sh [psql_host] [psql_port] [db_name] [psql_user] [psql_passwd] >> /tmp/host_usage.log
# Check your crontab jobs
crontab -l
```

## Improvements (TODO) 
1. Handle hardware update.
2. Database credentials management.
3. Fault tolerance. 
