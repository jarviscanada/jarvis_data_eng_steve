package ca.jrvs.apps.trading.repo;

import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityOrderRepository extends JpaRepository<SecurityOrder, Integer> {

  List<SecurityOrder> findByAccount(Account account);

  void deleteByAccount(Account account);

  List<SecurityOrder> findByQuote(Quote quote);

}
