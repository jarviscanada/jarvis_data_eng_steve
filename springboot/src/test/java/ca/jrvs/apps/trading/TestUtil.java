package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.QuoteBuilder;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.domain.TraderBuilder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestUtil {

  public final static String NOT_TICKER = "FB2";
  public final static Integer NOT_ID = 10;

  public static Quote getQuoteShop() {
    return new QuoteBuilder()
        .setTicker("SHOP")
        .setLastPrice(1000.1d)
        .setBidPrice(1000.2d)
        .setBidSize(10)
        .setAskPrice(1000d)
        .setAskSize(10)
        .createQuote();
  }

  public static Quote getQuoteRbc() {
    return new QuoteBuilder()
        .setTicker("RY")
        .setLastPrice(100.1d)
        .setBidPrice(100.2d)
        .setBidSize(100)
        .setAskPrice(100d)
        .setAskSize(100)
        .createQuote();
  }

  public static List<String> getSomeTickers() {
    List<String> tickers = new ArrayList<>();
    tickers.add("FB");
    tickers.add("GS");
    tickers.add("RY");
    return tickers;
  }

  public static Trader getTraderDavid() {
    return new TraderBuilder()
        .setFirstName("David")
        .setLastName("Tepper")
        .setDob(LocalDate.of(1957, 9, 11))
        .setCountry("US")
        .setEmail("dt@foobar.com")
        .createTrader();
  }

  public static Trader getTraderYasuo() {
    return new TraderBuilder()
        .setFirstName("Yasuo")
        .setLastName("Hamanaka")
        .setDob(LocalDate.of(1950, 1, 1))
        .setCountry("Japan")
        .setEmail("yh@foobar.com")
        .createTrader();
  }

}
