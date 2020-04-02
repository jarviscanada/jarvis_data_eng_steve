package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.utils.DataAccessObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerDAO extends DataAccessObject<Customer> {

  final Logger logger = LoggerFactory.getLogger(CustomerDAO.class);

  private static final String INSERT =
      "INSERT INTO customer (first_name, last_name, email, phone, address, city, state, zipcode) "
          + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

  private static final String GET_ONE =
      "SELECT customer_id, first_name, last_name, email, phone, address, city, state, zipcode "
          + "FROM customer WHERE customer_id = ?";

  private static final String UPDATE =
      "UPDATE customer SET first_name = ?, last_name=?, email = ?, phone = ?, address = ?, "
          + "city = ?, state = ?, zipcode = ? WHERE customer_id = ?";

  private static final String DELETE = "DELETE FROM customer WHERE customer_id = ?";

  private static final String GET_ALL_LMT =
      "SELECT customer_id, first_name, last_name, email, phone, address, city, state, zipcode "
          + "FROM customer ORDER BY last_name, first_name LIMIT ?";

  private static final String GET_ALL_PAGED =
      "SELECT customer_id, first_name, last_name, email, phone, address, city, state, zipcode "
          + "FROM customer ORDER BY last_name, first_name LIMIT ? OFFSET ?";

  public CustomerDAO(Connection connection) {
    super(connection);
  }

  @Override
  public Customer findById(long id) {
    Customer customer = new Customer();
    try (PreparedStatement statement = this.connection.prepareStatement(GET_ONE)) {
      statement.setLong(1, id);
      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        customer = getCustomer(rs);
      }
    } catch (SQLException e) {
      this.logger.error(e.getMessage(), e);
      throw new RuntimeException(e);
    }
    return customer;
  }

  @Override
  public List<Customer> findAll() {
    return null;
  }

  @Override
  public Customer update(Customer dto) {
    Customer customer;
    try {
      this.connection.setAutoCommit(false);
    } catch (SQLException e) {
      this.logger.error(e.getMessage(), e);
      throw new RuntimeException(e);
    }
    try (PreparedStatement statement = this.connection.prepareStatement(UPDATE)) {
      setStatement(statement, dto);
      statement.setLong(9, dto.getId());
      statement.execute();
      customer = this.findById(dto.getId());
    } catch (SQLException e) {
      try {
        this.connection.rollback();
      } catch (SQLException re) {
        this.logger.error(e.getMessage(), re);
        throw new RuntimeException(re);
      }
      this.logger.error(e.getMessage(), e);
      throw new RuntimeException(e);
    }
    return customer;
  }

  @Override
  public Customer create(Customer dto) {
    try (PreparedStatement statement = this.connection.prepareStatement(INSERT)) {
      setStatement(statement, dto);
      statement.execute();
      int id = this.getLastVal(CUSTOMER_SEQUENCE);
      return this.findById(id);
    } catch (SQLException e) {
      this.logger.error(e.getMessage(), e);
      throw new RuntimeException(e);
    }
  }

  @Override
  public void delete(long id) {
    try (PreparedStatement statement = this.connection.prepareStatement(DELETE)) {
      statement.setLong(1, id);
      statement.execute();
    } catch (SQLException e) {
      this.logger.error(e.getMessage(), e);
      throw new RuntimeException(e);
    }
  }

  public List<Customer> findAllSorted(int limit) {
    List<Customer> customers = new ArrayList<>();
    try (PreparedStatement statement = this.connection.prepareStatement(GET_ALL_LMT)) {
      statement.setInt(1, limit);
      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        customers.add(getCustomer(rs));
      }
    } catch (SQLException e) {
      this.logger.error(e.getMessage(), e);
      throw new RuntimeException(e);
    }
    return customers;
  }

  public List<Customer> findAllPaged(int limit, int pageNumber) {
    List<Customer> customers = new ArrayList<>();
    int offset = ((pageNumber - 1) * limit);
    try (PreparedStatement statement = this.connection.prepareStatement(GET_ALL_PAGED)) {
      // set default behavior
      if (limit < 1) {
        limit = 10;
      }
      statement.setInt(1, limit);
      statement.setInt(2, offset);
      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        customers.add(getCustomer(rs));
      }
    } catch (SQLException e) {
      this.logger.error(e.getMessage(), e);
      throw new RuntimeException(e);
    }
    return customers;
  }

  private Customer getCustomer(ResultSet rs) throws SQLException {
    Customer c = new Customer();
    c.setId(rs.getLong("customer_id"));
    c.setFirstName(rs.getString("first_name"));
    c.setLastName(rs.getString("last_name"));
    c.setEmail(rs.getString("email"));
    c.setPhone(rs.getString("phone"));
    c.setAddress(rs.getString("address"));
    c.setCity(rs.getString("city"));
    c.setState(rs.getString("state"));
    c.setZipCode(rs.getString("zipcode"));
    return c;
  }

  /**
   * Set the PreparedStatement except ID field by a given DTO
   */
  private void setStatement(PreparedStatement st, Customer c) throws SQLException {
    st.setString(1, c.getFirstName());
    st.setString(2, c.getLastName());
    st.setString(3, c.getEmail());
    st.setString(4, c.getPhone());
    st.setString(5, c.getAddress());
    st.setString(6, c.getCity());
    st.setString(7, c.getState());
    st.setString(8, c.getZipCode());
  }
}
