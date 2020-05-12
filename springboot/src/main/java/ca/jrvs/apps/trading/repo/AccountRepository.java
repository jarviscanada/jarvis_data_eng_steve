package ca.jrvs.apps.trading.repo;

import ca.jrvs.apps.trading.model.domain.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {

}
