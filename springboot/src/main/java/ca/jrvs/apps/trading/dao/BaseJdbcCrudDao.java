package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Entity;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public abstract class BaseJdbcCrudDao<E extends Entity<ID>, ID>
    implements CrudRepository<E, ID> {

  abstract JdbcTemplate getJdbcTemplate();

  abstract SimpleJdbcInsert getSimpleJdbcInsert();

  abstract String getTableName();

  abstract String getIdColumnName();

  abstract Class<E> getEntityClass();

  private final BeanPropertyRowMapper<E> rowMapper = new BeanPropertyRowMapper<>(getEntityClass());
  private static final Logger logger = LoggerFactory.getLogger(BaseJdbcCrudDao.class);

  @Override
  public <S extends E> Iterable<S> saveAll(Iterable<S> entities) {
    for (S s : entities) {
      save(s);
    }
    return entities;
  }

  /**
   * Save an entity and update auto-generated integer ID
   *
   * @param entity to be saved
   * @return saved entity
   */
  @Override
  public <S extends E> S save(S entity) {
    if (existsById(entity.getId())) {
      updateOne(entity);
    } else {
      addOne(entity);
    }
    return entity;
  }

  abstract <S extends E> void updateOne(S entity);

  @SuppressWarnings("unchecked")
  private <S extends E> void addOne(S entity) {
    SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);
    // if ID will be auto generated
    if (entity.getId() == null) {
      Number newId = getSimpleJdbcInsert().executeAndReturnKey(parameterSource);
      entity.setId((ID) newId);
    } else {
      int rowsAffected = getSimpleJdbcInsert().execute(parameterSource);
      if (rowsAffected != 1) {
        throw new IncorrectResultSizeDataAccessException("Failed to insert", 1, rowsAffected);
      }
    }
  }

  @Override
  public boolean existsById(ID id) {
    String countSql =
        "SELECT COUNT(*) FROM " + getTableName() + " WHERE " + getIdColumnName() + " =?";
    Long result = getJdbcTemplate().queryForObject(countSql, Long.class, id);
    if (result == null) {
      throw new DataRetrievalFailureException("Count result is null");
    }
    return result > 0;
  }

  @Override
  public Optional<E> findById(ID id) {
    String selectSql = "SELECT * FROM " + getTableName() + " WHERE " + getIdColumnName() + " =?";
    try {
      return Optional.ofNullable(getJdbcTemplate()
          .queryForObject(selectSql, rowMapper, id));
    } catch (IncorrectResultSizeDataAccessException e) {
      logger.debug("Can't find entity id: " + id, e);
      return Optional.empty();
    }
  }

  @Override
  public List<E> findAll() {
    String selectSql = "SELECT * FROM " + getTableName();
    return getJdbcTemplate().query(selectSql, rowMapper);
  }

  @Override
  public Iterable<E> findAllById(Iterable<ID> ids) {
    throw new UnsupportedOperationException();
  }

  @Override
  public long count() {
    String countSql = "SELECT COUNT(*) FROM " + getTableName();
    Long result = getJdbcTemplate().queryForObject(countSql, Long.class);
    if (result == null) {
      throw new DataRetrievalFailureException("Count result is null");
    }
    return result;
  }

  @Override
  public void deleteById(ID id) {
    String deleteSql = "DELETE FROM " + getTableName() + " WHERE " + getIdColumnName() + " =?";
    int rowsAffected = getJdbcTemplate().update(deleteSql, id);
    logger.info("DELETE " + rowsAffected);
  }

  @Override
  public void deleteAll() {
    String deleteSql = "DELETE FROM " + getTableName();
    int rowsAffected = getJdbcTemplate().update(deleteSql);
    logger.info("DELETE " + rowsAffected);
  }

  @Override
  public void delete(E entity) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void deleteAll(Iterable<? extends E> entities) {
    throw new UnsupportedOperationException();
  }
}
