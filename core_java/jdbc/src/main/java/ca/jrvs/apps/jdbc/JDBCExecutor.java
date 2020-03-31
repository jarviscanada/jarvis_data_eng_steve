package ca.jrvs.apps.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JDBCExecutor {

  private static Logger logger = LoggerFactory.getLogger(JDBCExecutor.class);

  public static void main(String[] args) {
    BasicConfigurator.configure();
    DatabaseConnectionManager dcm = new DatabaseConnectionManager(
        "localhost", "hplussport", "postgres", "password");
    try {
      Connection connection = dcm.getConnection();
      CustomerDAO customerDAO = new CustomerDAO(connection);
      Customer customer = customerDAO.findById(1000);
      logger.info(customer.getFirstName() + " " + customer.getLastName());
    } catch (SQLException e) {
      logger.error(e.getMessage(), e);
    }
  }

}
