package ca.jrvs.apps.trading.repo;

import ca.jrvs.apps.trading.model.domain.Id.PositionId;
import ca.jrvs.apps.trading.model.domain.Position;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position, PositionId> {

  List<Position> findByAccountId(Integer accountId);

  List<Position> findByTicker(String ticker);

}
