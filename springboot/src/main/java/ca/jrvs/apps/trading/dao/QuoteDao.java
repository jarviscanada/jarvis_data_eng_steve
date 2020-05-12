package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class QuoteDao extends BaseJdbcCrudDao<Quote, String> {

  private static final String TABLE_NAME = "quote";
  private static final String ID_COLUMN_NAME = "ticker";
  private static final Logger logger = LoggerFactory.getLogger(QuoteDao.class);

  private final JdbcTemplate jdbcTemplate;
  private final SimpleJdbcInsert jdbcInsert;

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
        quote.getId()
    };
  }

  @Override
  JdbcTemplate getJdbcTemplate() {
    return jdbcTemplate;
  }

  @Override
  SimpleJdbcInsert getSimpleJdbcInsert() {
    return jdbcInsert;
  }

  @Override
  String getTableName() {
    return TABLE_NAME;
  }

  @Override
  String getIdColumnName() {
    return ID_COLUMN_NAME;
  }

  @Override
  Class<Quote> getEntityClass() {
    return Quote.class;
  }


  @Override
  void updateOne(Quote quote) {
    String updateSql = "UPDATE quote SET last_price=?, bid_price=?, "
        + "bid_size=?, ask_price=?, ask_size=? WHERE ticker=?";
    int rowsUpdated = jdbcTemplate.update(updateSql, makeUpdateValues(quote));
    if (rowsUpdated != 1) {
      throw new IncorrectResultSizeDataAccessException("Failed to update.", 1, rowsUpdated);
    }
  }

  @Override
  public void delete(Quote quote) {
    deleteById(quote.getId());
  }

}
