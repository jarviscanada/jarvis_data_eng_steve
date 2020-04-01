package ca.jrvs.apps.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
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
      OrderDAO orderDAO = new OrderDAO(connection);
      Order order = orderDAO.findById(1000);
      logger.info(order.toString());

      List<Order> orders = orderDAO.getOrdersForCustomer(789);
      orders.forEach(x -> logger.info(x.toString()));
    } catch (SQLException e) {
      logger.error(e.getMessage(), e);
    }
  }

}
