package ca.jrvs.apps.trading.model.domain;

import java.time.LocalDate;

public class TraderBuilder {

  private String firstName;
  private String lastName;
  private LocalDate dob;
  private String country;
  private String email;

  public TraderBuilder setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public TraderBuilder setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public TraderBuilder setDob(LocalDate dob) {
    this.dob = dob;
    return this;
  }

  public TraderBuilder setCountry(String country) {
    this.country = country;
    return this;
  }

  public TraderBuilder setEmail(String email) {
    this.email = email;
    return this;
  }

  public Trader createTrader() {
    return new Trader(firstName, lastName, dob, country, email);
  }
}