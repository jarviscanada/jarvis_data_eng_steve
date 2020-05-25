package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.view.PortfolioView;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import ca.jrvs.apps.trading.model.view.TraderAccountsView;
import ca.jrvs.apps.trading.service.DashboardService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashBoardController {

  private final DashboardService service;

  @Autowired
  public DashBoardController(DashboardService service) {
    this.service = service;
  }

  @ApiOperation(value = "Show account profile by account ID")
  @ApiResponses(value = {@ApiResponse(code = 404, message = "accountId not found")})
  @ResponseStatus(HttpStatus.OK)
  @GetMapping(
      path = "/profile/accountId/{accountId}",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}
  )
  public TraderAccountView getAccount(@PathVariable Integer accountId) {
    try {
      return service.viewTraderAccountByAccountId(accountId);
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }

  @ApiOperation(value = "Show trader profile by trader ID")
  @ApiResponses(value = {@ApiResponse(code = 404, message = "traderId not found")})
  @ResponseStatus(HttpStatus.OK)
  @GetMapping(
      path = "/profile/traderId/{traderId}",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}
  )
  public TraderAccountsView getAccounts(@PathVariable Integer traderId) {
    try {
      return service.viewTraderAccountByTraderId(traderId);
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }

  @ApiOperation(value = "Show portfolio by account ID")
  @ApiResponses(value = {@ApiResponse(code = 404, message = "accountId is not found")})
  @GetMapping(path = "/portfolio/accountId/{accountId}")
  @ResponseStatus(HttpStatus.OK)
  public PortfolioView getPortfolioView(@PathVariable Integer accountId) {
    try {
      return service.viewProfileByAccountId(accountId);
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }

}
