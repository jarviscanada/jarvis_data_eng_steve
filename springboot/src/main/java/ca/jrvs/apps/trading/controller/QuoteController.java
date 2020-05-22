package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.dto.IexQuote;
import ca.jrvs.apps.trading.service.QuoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@Api(value = "quote", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
@RequestMapping("/quote")
public class QuoteController {

  private final QuoteService service;

  @Autowired
  public QuoteController(QuoteService service) {
    this.service = service;
  }

  @ApiOperation(value = "Show IexQuote", notes = "Show IexQuote for a given ticker/symbol")
  @GetMapping(path = "/iex/ticker/{ticker}")
  @ResponseStatus(HttpStatus.OK)
  public IexQuote getQuote(@PathVariable String ticker) {
    try {
      return service.findIexQuoteByTicker(ticker);
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }

  @ApiOperation(
      value = "Update quote table using IEX data",
      notes = "Update all quotes in the quote table. Use IEX trading API as market data source."
  )
  @PutMapping(path = "/updateAll")
  @ResponseStatus(HttpStatus.OK)
  public List<Quote> updateMarketData() {
    try {
      return service.updateMarketData();
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }

  @ApiOperation(
      value = "Update a given quote in the quote table",
      notes = "Manually update a quote in the quote table using IEX market data."
  )
  @PutMapping(path = "/update")
  @ResponseStatus(HttpStatus.OK)
  public Quote putQuote(@RequestBody Quote quote) {
    try {
      return service.saveQuote(quote);
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }

  @ApiOperation(
      value = "Add a new ticker to the dailyList (quote table)",
      notes = "Add a new ticker/symbol to the quote table, so trader can trade this security."
  )
  @PostMapping(path = "/tickerId/{tickerId}")
  @ResponseStatus(HttpStatus.CREATED)
  public Quote createQuote(String tickerId) {
    try {
      return service.saveQuote(tickerId);
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }

  @ApiOperation(
      value = "Show the dailyList", notes = "Show dailyList for this trading system."
  )
  @GetMapping(path = "/dailyList")
  @ResponseStatus(HttpStatus.OK)
  public List<Quote> getDailyList() {
    try {
      return service.findAllQuotes();
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }
}
