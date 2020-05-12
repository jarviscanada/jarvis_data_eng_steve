package ca.jrvs.apps.trading.repo;

import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityOrderRepository extends CrudRepository<SecurityOrder, Integer> {

}
