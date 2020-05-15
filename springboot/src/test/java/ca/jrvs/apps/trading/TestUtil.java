package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.ArrayList;
import java.util.List;

public class TestUtil {

  public final static String NOT_ID = "FB2";

  public static Quote getQuoteShop() {
    return new Quote(
        "SHOP", 1000.1d, 1000.2d, 10, 1000d, 10
    );
  }

  public static Quote getQuoteRbc() {
    return new Quote(
        "RY", 100.1d, 100.2d, 100, 100d, 100
    );
  }

  public static List<String> getSomeTickers() {
    List<String> tickers = new ArrayList<>();
    tickers.add("FB");
    tickers.add("GS");
    tickers.add("RY");
    return tickers;
  }
}
