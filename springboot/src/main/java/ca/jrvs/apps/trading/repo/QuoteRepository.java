package ca.jrvs.apps.trading.repo;

import ca.jrvs.apps.trading.model.domain.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, String> {

}
