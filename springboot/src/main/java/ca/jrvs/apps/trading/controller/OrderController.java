package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.dto.MarketOrderDto;
import ca.jrvs.apps.trading.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Order", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
@RequestMapping("/order")
public class OrderController {

  private final OrderService service;

  @Autowired
  public OrderController(OrderService service) {
    this.service = service;
  }

  @ApiOperation(value = "Submit a market order", notes = "Submit a market order")
  @ApiResponses(value = {
      @ApiResponse(code = 404, message = "Account Id or ticker is not found"),
      @ApiResponse(code = 400, message = "Unable to proceed due to user input")
  })
  @PostMapping(path = "/marketOrder")
  @ResponseStatus(HttpStatus.CREATED)
  public SecurityOrder postMarketOrder(@RequestBody MarketOrderDto dto) {
    try {
      return service.executeMarketOrder(dto);
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }
}
