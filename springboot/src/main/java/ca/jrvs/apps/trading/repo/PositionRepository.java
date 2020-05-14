package ca.jrvs.apps.trading.repo;

import ca.jrvs.apps.trading.model.domain.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

}
