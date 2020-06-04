package ca.jrvs.apps.trading.controller;

import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "App", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
@RequestMapping("/")
public class AppController {

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(path = "/health")
  public String health() {
    return "I'm very healthy!";
  }
}
