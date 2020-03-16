# Linux Cluster Monitoring Agent

## Introduction
This is an internal tool for Linux cluster administration (LCA) team that records the hardware specifications of servers and monitor node resource usages (e.g. CPU/Memory) in realtime. The collected data will be stored in an RDBMS database. Users are able to use the data to generate some reports for future resource planning purposes (e.g. add/remove servers).

## Architecture and Design
<img src="../assets/arch.png" alt="drawing" width="800"/>

* A [PostgreSQL](https://www.postgresql.org/) instance is used to persist all the data. The server hosting the database needs the following two scripts:

  * [psql_docker.sh](./scripts/psql_docker.sh) acts as a switch to start/stop the psql instance.
  
  * [ddl.sql](./sql/ddl.sql) automates the database initialization.  

* The bash agent gathers server usage data, and then insert into the psql instance. The agent will be installed on every host/server/node. The agent consists of two bash scripts:

  * [host_info.sh](./scripts/host_info.sh) collects the host hardware info and insert it into the database. It will be run only once at the installation time.

  * [host_usage.sh](./scripts/host_usage.sh) collects the current host usage (CPU and Memory) and then insert into the database. It will be triggered by the crontab job every minute.
  
* [queries.sql](./sql/queries.sql) contains some pre-written queries that help cluster administrator to manage the cluster better and plan for future recourses.  
  
### Database

The database `host_agent` consists of two tables: `host_info` and `host_usage`. `host_info` holds the hardware specifications data of each node, assuming the information is constant which will be only inserted once (see [Improvements](#improvements-todo)). `host_usage` stores the resource usage data collected in real-time.

#### Schema of host_info 
| Field            | Type      | Description/Example                       |
|------------------|-----------|-------------------------------------------|
| id               | SERIAL    | Auto-incremented primary key              |
| hostname         | VARCHAR   | Must be unique, "Server-John-Doe"         |
| cpu_number       | SMALLINT  | Number of cpu cores                       |
| cpu_architecture | VARCHAR   | "x86_64", "armv7l"                        |
| cpu_model        | VARCHAR   | "Intel(R) Xeon(R) CPU @ 2.30GHz"          |
| L2_cache         | INTEGER   | L2 cache in KB                            |
| total_mem        | INTEGER   | Total RAM in MB                           |
| timestamp        | TIMESTAMP | "2020/02/29 12:00:00"                     |

#### Schema of host_usage
| Field            | Type      | Description/Example                       |
|------------------|-----------|-------------------------------------------|
| timestamp        | TIMESTAMP | "2020/02/29 12:00:00"                     |
| host_id          | SERIAL    | REFERENCES host_info(id)                  |
| memory_free      | INTEGER   | Free RAM in MB                            |
| cpu_idle         | SMALLINT  | % time CPU is idle                        |
| cpu_kernel       | SMALLINT  | % time CPU spends on kernel related tasks |
| disk_io          | INTEGER   | Number of inprogress IO                   |
| disk_available   | INTEGER   | Space available on `/dev/sda1` in MB      |

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
