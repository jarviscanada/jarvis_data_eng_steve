package ca.jrvs.apps.trading.repo;

import ca.jrvs.apps.trading.model.domain.Position;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends CrudRepository<Position, Integer> {

}
