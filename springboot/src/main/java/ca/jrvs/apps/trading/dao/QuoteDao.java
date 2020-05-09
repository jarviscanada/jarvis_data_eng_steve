package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class QuoteDao implements CrudRepository<Quote, String> {

  private static final String TABLE_NAME = "quote";
  private static final String ID_COLUMN_NAME = "ticker";
  private static final Logger logger = LoggerFactory.getLogger(QuoteDao.class);

  private final JdbcTemplate jdbcTemplate;
  private final SimpleJdbcInsert jdbcInsert;
  private final BeanPropertyRowMapper<Quote> rowMapper = new BeanPropertyRowMapper<>(Quote.class);

  @Autowired
  public QuoteDao(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
    jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME);
  }

  /**
   * Helper method that makes sql update values objects
   *
   * @param quote to be updated
   * @return UPDATE_SQL values
   */
  private static Object[] makeUpdateValues(Quote quote) {
    return new Object[]{
        quote.getLastPrice(),
        quote.getBidPrice(),
        quote.getBidSize(),
        quote.getAskPrice(),
        quote.getAskSize(),
        quote.getID()
    };
  }

  /**
   * @throws DataAccessException for unexpected SQL execution result
   */
  @Override
  public <Q extends Quote> Q save(Q q) {
    if (existsById(q.getID())) {
      updateOne(q);
    } else {
      addOne(q);
    }
    return q;
  }

  private void addOne(Quote quote) {
    SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(quote);
    int rowsAffected = jdbcInsert.execute(parameterSource);
    if (rowsAffected != 1) {
      throw new IncorrectResultSizeDataAccessException("Failed to insert", 1, rowsAffected);
    }
  }

  private void updateOne(Quote quote) {
    String updateSql = "UPDATE quote SET last_price=?, bid_price=?, "
        + "bid_size=?, ask_price=?, ask_size=? WHERE ticker=?";
    int rowsUpdated = jdbcTemplate.update(updateSql, makeUpdateValues(quote));
    if (rowsUpdated != 1) {
      throw new IncorrectResultSizeDataAccessException("Failed to update.", 1, rowsUpdated);
    }
  }

  @Override
  public <Q extends Quote> Iterable<Q> saveAll(Iterable<Q> iterable) {
    for (Q q : iterable) {
      save(q);
    }
    return iterable;
  }

  @Override
  public Optional<Quote> findById(String id) {
    String selectSql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + " =?";
    try {
      return Optional.ofNullable(jdbcTemplate.queryForObject(selectSql, rowMapper, id));
    } catch (EmptyResultDataAccessException e) {
      logger.warn("Resource not found");
      return Optional.empty();
    }
  }

  @Override
  public List<Quote> findAll() {
    String selectSql = "SELECT * FROM " + TABLE_NAME;
    return jdbcTemplate.query(selectSql, rowMapper);
  }

  @Override
  public Iterable<Quote> findAllById(Iterable<String> iterable) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean existsById(String id) {
    String countSql = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + " =?";
    Long result = jdbcTemplate.queryForObject(countSql, Long.class, id);
    if (result == null) {
      throw new DataRetrievalFailureException("Count result is null");
    }
    return result > 0;
  }

  @Override
  public long count() {
    String countSql = "SELECT COUNT(*) FROM " + TABLE_NAME;
    Long result = jdbcTemplate.queryForObject(countSql, Long.class);
    if (result == null) {
      throw new DataRetrievalFailureException("Count result is null");
    }
    return result;
  }

  @Override
  public void deleteById(String id) {
    String deleteSql = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + " =?";
    int rowsAffected = jdbcTemplate.update(deleteSql, id);
    if (rowsAffected == 0) {
      logger.warn("Unmodified");
    } else if (rowsAffected > 1) {
      throw new IncorrectResultSizeDataAccessException(
          "Unexpected number of rows deleted", 1, rowsAffected);
    }
  }

  @Override
  public void delete(Quote quote) {
    deleteById(quote.getID());
  }

  @Override
  public void deleteAll(Iterable<? extends Quote> iterable) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void deleteAll() {
    String deleteSql = "DELETE FROM " + TABLE_NAME;
    int rowsAffected = jdbcTemplate.update(deleteSql);
    logger.info("DELETE " + rowsAffected);
  }
}
