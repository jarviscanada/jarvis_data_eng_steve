package ca.jrvs.apps.trading.repo;

import static ca.jrvs.apps.trading.TestUtil.NOT_TICKER;
import static ca.jrvs.apps.trading.TestUtil.getQuoteRbc;
import static ca.jrvs.apps.trading.TestUtil.getQuoteShop;

import ca.jrvs.apps.trading.model.domain.Quote;

public class QuoteRepositoryTest extends BaseRepositoryTest<QuoteRepository, Quote, String> {

  @Override
  Object modifyOneField(Quote quote) {
    Integer newAskSize = quote.getAskSize() + 1;
    quote.setAskSize(newAskSize);
    return newAskSize;
  }

  @Override
  Object getOneField(Quote quote) {
    return quote.getAskSize();
  }

  @Override
  Quote getTestEntity1() {
    return getQuoteShop();
  }

  @Override
  Quote getTestEntity2() {
    return getQuoteRbc();
  }

  @Override
  String getNonExistId() {
    return NOT_TICKER;
  }
}