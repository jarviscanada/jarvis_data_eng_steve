# Order of operations
These commands are for Linux/Mac, changes will need to made if you are running this in Microsoft Windows.

## Prerequisites
Docker is installed

## Actions

### Running/Stopping PostgreSQL
Use [psql_docker.sh](../../../linux_sql/scripts/psql_docker.sh), 
you may want to checkout the usage section of this [page](../../../linux_sql/README.md).

### Logging into Database
* `psql -h localhost -U postgres -d hplussport`

### Creating starter data
1. `psql -h localhost -U postgres -f database.sql`
2. `psql -h localhost -U postgres -d hplussport -f customer.sql`
3. `psql -h localhost -U postgres -d hplussport -f product.sql`
4. `psql -h localhost -U postgres -d hplussport -f salesperson.sql`
5. `psql -h localhost -U postgres -d hplussport -f orders.sql`
