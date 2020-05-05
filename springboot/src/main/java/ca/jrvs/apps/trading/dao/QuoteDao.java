package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class QuoteDao implements CrudRepository<Quote, String> {

  @Override
  public <S extends Quote> S save(S s) {
    return null;
  }

  @Override
  public <S extends Quote> Iterable<S> saveAll(Iterable<S> iterable) {
    return null;
  }

  @Override
  public Optional<Quote> findById(String s) {
    return Optional.empty();
  }

  @Override
  public boolean existsById(String s) {
    return false;
  }

  @Override
  public Iterable<Quote> findAll() {
    return null;
  }

  @Override
  public Iterable<Quote> findAllById(Iterable<String> iterable) {
    return null;
  }

  @Override
  public long count() {
    return 0;
  }

  @Override
  public void deleteById(String s) {

  }

  @Override
  public void delete(Quote quote) {

  }

  @Override
  public void deleteAll(Iterable<? extends Quote> iterable) {

  }

  @Override
  public void deleteAll() {

  }
}
