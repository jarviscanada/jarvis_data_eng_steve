package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CountPrimesTest {

  @Test
  public void countPrimes() {
    assertEquals(0, CountPrimes.countPrimes(2));
    assertEquals(1, CountPrimes.countPrimes(3));
    assertEquals(4, CountPrimes.countPrimes(10));
  }
}