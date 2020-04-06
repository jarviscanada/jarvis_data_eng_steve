# Introduction
This application demonstrates how do developers deal with PostgreSQL CRUD (Create, Read, Update, Delete) 
operations at a high abstraction level using the DAO (Data Access Object) pattern. 
By implementing this project, I got familiar with the basic usage of JDBC (Java Database Connectivity). 
More importantly, I learnt several essential concepts for developing applications that interact with DBMS, 
including the repository pattern, DAO, database transaction, etc.

# Database

## Loading Data
### Running PostgreSQL
Use [psql_docker.sh](../../../linux_sql/scripts/psql_docker.sh) to launch/stop a psql instance.
Checkout the usage [here](../../../linux_sql/README.md).

### Logging into Database
`psql -h localhost -U postgres -d hplussport`

### Creating starter data
```sh
cd sql
psql -h localhost -U postgres -f database.sql
psql -h localhost -U postgres -d hplussport -f customer.sql
psql -h localhost -U postgres -d hplussport -f product.sql
psql -h localhost -U postgres -d hplussport -f salesperson.sql
psql -h localhost -U postgres -d hplussport -f orders.sql
```

## ER Diagram
<img src="../../assets/er.png" alt="drawing" width="600"/>

# Design Patterns (DAO vs. Repo)
The repository pattern can be considered as a special case of DAO, while it focuses only on single 
table access per class. A good example is [CustomerDAO](./src/main/java/ca/jrvs/apps/jdbc/CustomerDAO.java),
we can see that it follows the repository pattern because it only accesses the customer data. And
[OrderDAO](./src/main/java/ca/jrvs/apps/jdbc/OrderDAO.java) is another typical example of DAO (but NOT Repo.) 
that interacts with multiple tables by using `JOIN` operation. 
Both of repository and DAO patterns are useful depends on the use cases: when the data is highly normalized
or atomic transaction is needed, the latter approach is obviously preferred because we can get the most from
the relational database; while in cases when the horizontal scalability matters, repository pattern will fit better.
