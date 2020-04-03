package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.utils.DataAccessObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderDAO extends DataAccessObject<Order> {

  final Logger logger = LoggerFactory.getLogger(OrderDAO.class);

  private static final String GET_BY_ID =
      "SELECT c.first_name, c.last_name, c.email, o.order_id, o.creation_date, "
          + "o.total_due, o.status, s.first_name, s.last_name, s.email, ol.quantity, "
          + "p.code, p.name, p.size, p.variety, p.price FROM orders o "
          + "JOIN customer c on o.customer_id = c.customer_id "
          + "JOIN salesperson s on o.salesperson_id = s.salesperson_id "
          + "JOIN order_item ol on ol.order_id = o.order_id "
          + "JOIN product p on ol.product_id = p.product_id "
          + "where o.order_id = ?";

  private static final String GET_FOR_CUST = "SELECT * FROM get_orders_by_customer(?)";

  public OrderDAO(Connection connection) {
    super(connection);
  }

  @Override
  public Order findById(long id) {
    Order order = new Order();
    try (PreparedStatement statement = this.connection.prepareStatement(GET_BY_ID)) {
      statement.setLong(1, id);
      ResultSet resultSet = statement.executeQuery();
      List<OrderLine> orderLines = new ArrayList<>();
      boolean isFirstOrderLine = true;
      while (resultSet.next()) {
        if (isFirstOrderLine) {
          setOrder(order, resultSet);
          isFirstOrderLine = false;
        }
        orderLines.add(getOrderLine(resultSet));
      }
      order.setOrderLines(orderLines);
    } catch (SQLException e) {
      this.logger.error(e.getMessage(), e);
      throw new RuntimeException(e);
    }
    return order;
  }

  @Override
  public List<Order> findAll() {
    return null;
  }

  @Override
  public Order update(Order dto) {
    return null;
  }

  @Override
  public Order create(Order dto) {
    return null;
  }

  @Override
  public void delete(long id) {

  }

  public List<Order> getOrdersForCustomer(long customerId) {
    List<Order> orders = new ArrayList<>();
    try (PreparedStatement statement = this.connection.prepareStatement(GET_FOR_CUST)) {
      statement.setLong(1, customerId);
      ResultSet resultSet = statement.executeQuery();
      long orderId = 0;
      Order order = null;
      while (resultSet.next()) {
        long localOrderId = resultSet.getLong(4);
        if (orderId != localOrderId) {
          order = new Order();
          orders.add(order);
          order.setId(localOrderId);
          orderId = localOrderId;
          setOrder(order, resultSet);
          List<OrderLine> orderLines = new ArrayList<>();
          order.setOrderLines(orderLines);
        }
        if (order != null) {
          order.getOrderLines().add(getOrderLine(resultSet));
        }
      }
    } catch (SQLException e) {
      logger.error(e.getMessage(), e);
      throw new RuntimeException(e);
    }
    return orders;
  }

  private void setOrder(Order order, ResultSet rs) throws SQLException {
    order.setCustomerFirstName(rs.getString(1));
    order.setCustomerLastName(rs.getString(2));
    order.setCustomerEmail(rs.getString(3));
    order.setCreationDate(new Date(rs.getDate(5).getTime()));
    order.setTotalDue(rs.getBigDecimal(6));
    order.setStatus(rs.getString(7));
    order.setSalespersonFirstName(rs.getString(8));
    order.setSalespersonLastName(rs.getString(9));
    order.setSalespersonEmail(rs.getString(10));
  }

  private OrderLine getOrderLine(ResultSet rs) throws SQLException {
    OrderLine ol = new OrderLine();
    ol.setQuantity(rs.getInt(11));
    ol.setProductCode(rs.getString(12));
    ol.setProductName(rs.getString(13));
    ol.setProductSize(rs.getInt(14));
    ol.setProductVariety(rs.getString(15));
    ol.setProductPrice(rs.getBigDecimal(16));
    return ol;
  }

}
