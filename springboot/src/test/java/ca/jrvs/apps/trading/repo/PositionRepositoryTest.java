package ca.jrvs.apps.trading.repo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import ca.jrvs.apps.trading.model.domain.Id.PositionId;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Test findById(), findByAccountId(), findByTicker() of PositionRepository and find, update, delete
 * functionalities of SecurityOrderRepository
 */
public class PositionRepositoryTest extends SecurityOrderRepositoryTest {

  private static final Logger logger = LoggerFactory.getLogger(PositionRepositoryTest.class);
  private long defaultCount;
  @Autowired
  private PositionRepository positionRepository;

  @Override
  public void setUp() {
    super.setUp();
    defaultCount = positionRepository.count();
  }

  @Test
  public void findById() {
    addOne();
    assertEquals(defaultCount + 1, positionRepository.count());

    PositionId id1 = new PositionId(savedAccount1.getId(), savedQuote1.getTicker());
    Optional<Position> position1 = positionRepository.findById(id1);
    assertTrue(position1.isPresent());
    assertEquals(savedQuote1.getTicker(), position1.get().getTicker());
    assertEquals(savedEntity1.getSize(), position1.get().getPosition());

    PositionId id2 = new PositionId(savedAccount2.getId(), savedQuote2.getTicker());
    Optional<Position> position2 = positionRepository.findById(id2);
    assertTrue(position2.isPresent());
    assertEquals(savedQuote2.getTicker(), position2.get().getTicker());
    assertEquals(savedEntity2.getSize(), position2.get().getPosition());
  }

  @Test
  public void findByAccountId() {
    addOne();
    List<Position> positions = positionRepository.findByAccountId(savedAccount2.getId());
    positions.forEach(p -> logger.info(p.toString()));
    assertEquals(2, positions.size());
  }

  @Test
  public void findByTicker() {
    addOne();
    List<Position> positions = positionRepository.findByTicker(savedQuote1.getTicker());
    positions.forEach(p -> logger.info(p.toString()));
    assertEquals(2, positions.size());
  }

  private void addOne() {
    SecurityOrder order = new SecurityOrder(
        "FILLED", 50, 90d, "", savedQuote1, savedAccount2
    );
    repo.save(order);
  }
}