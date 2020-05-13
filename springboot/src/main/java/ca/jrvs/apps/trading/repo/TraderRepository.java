package ca.jrvs.apps.trading.repo;

import ca.jrvs.apps.trading.model.domain.Trader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TraderRepository extends JpaRepository<Trader, Integer> {

}
