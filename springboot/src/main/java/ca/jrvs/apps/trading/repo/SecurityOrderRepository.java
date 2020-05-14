package ca.jrvs.apps.trading.repo;

import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityOrderRepository extends JpaRepository<SecurityOrder, Integer> {

}
