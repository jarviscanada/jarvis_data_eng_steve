package ca.jrvs.apps.trading.repo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Entity;
import java.util.List;
import java.util.Optional;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public abstract class BaseRepositoryTest<R extends JpaRepository<E, I>, E extends Entity<I>, I> {

  @Autowired
  R repo;

  E savedEntity1;
  E savedEntity2;
  I savedId1;
  I savedId2;

  private static final Logger logger = LoggerFactory.getLogger(BaseRepositoryTest.class);

  /**
   * @return value after modification
   */
  abstract Object modifyOneField(E e);

  abstract Object getOneField(E e);

  abstract E getTestEntity1();

  abstract E getTestEntity2();

  abstract I getNonExistId();

  @Before
  public void setUp() {
    savedEntity1 = repo.save(getTestEntity1());
    savedEntity2 = repo.save(getTestEntity2());
    savedId1 = savedEntity1.getId();
    savedId2 = savedEntity2.getId();
  }

  @After
  public void tearDown() {
    repo.deleteAll();
  }

  @Test
  public void find() {
    List<E> all = repo.findAll();
    assertEquals(2, all.size());

    Optional<E> entity1 = repo.findById(savedId1);
    assertTrue(entity1.isPresent());
    assertEquals(savedEntity1, entity1.get());
    logger.info(entity1.toString());

    Optional<E> entity2 = repo.findById(savedId2);
    assertTrue(entity2.isPresent());
    assertEquals(savedEntity2, entity2.get());
    logger.info(entity2.toString());

    Optional<E> entity3 = repo.findById(getNonExistId());
    assertTrue(entity3.isEmpty());
  }

  @Test
  public void delete() {
    assertEquals(2, repo.count());
    assertTrue(repo.existsById(savedId1));
    assertTrue(repo.existsById(savedId2));

    repo.deleteById(savedId1);
    assertEquals(1, repo.count());
    assertFalse(repo.existsById(savedId1));
    assertTrue(repo.existsById(savedId2));

    repo.delete(savedEntity2);
    assertEquals(0, repo.count());
    assertFalse(repo.existsById(savedId2));

    // do nothing
    repo.delete(savedEntity1);

    try {
      repo.deleteById(savedId2);
      fail();
    } catch (EmptyResultDataAccessException ignored) {
    }
  }

  @Test
  public void update() {
    List<E> all = repo.findAll();
    Object newValue = modifyOneField(all.get(0));
    repo.saveAll(all);

    Optional<E> e1 = repo.findById(all.get(0).getId());
    assertTrue(e1.isPresent());
    assertEquals(newValue, getOneField(e1.get()));

    Optional<E> e2 = repo.findById(all.get(1).getId());
    assertTrue(e2.isPresent());
    assertEquals(savedEntity2, e2.get());
  }
}
