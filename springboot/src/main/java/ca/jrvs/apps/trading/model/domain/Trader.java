package ca.jrvs.apps.trading.model.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

@javax.persistence.Entity
public class Trader implements Entity<Integer> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotBlank
  @Column(name = "first_name")
  private String firstName;

  @NotBlank
  @Column(name = "last_name")
  private String lastName;

  @Past
  @Column
  private LocalDate dob;

  @NotBlank
  @Column
  private String country;

  @Email
  @Column
  private String email;

  @OneToMany(mappedBy = "trader")
  private List<Account> accounts;

  public Trader() {
  }

  public Trader(String firstName, String lastName, LocalDate dob, String country, String email) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.dob = dob;
    this.country = country;
    this.email = email;
  }

  @Override
  public String toString() {
    return "Trader{" +
        "id=" + id +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", dob=" + dob +
        ", country='" + country + '\'' +
        ", email='" + email + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Trader trader = (Trader) o;
    return id.equals(trader.id) &&
        firstName.equals(trader.firstName) &&
        lastName.equals(trader.lastName) &&
        dob.equals(trader.dob) &&
        country.equals(trader.country) &&
        email.equals(trader.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, dob, country, email);
  }

  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public void setId(Integer id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public LocalDate getDob() {
    return dob;
  }

  public void setDob(LocalDate dob) {
    this.dob = dob;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
