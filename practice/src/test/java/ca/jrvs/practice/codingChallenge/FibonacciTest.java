package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class FibonacciTest {

  @Test
  public void computeByRecursion() {
    Assert.assertEquals(2, Fibonacci.computeByRecursion(3));
    Assert.assertEquals(233, Fibonacci.computeByRecursion(13));
    Assert.assertEquals(2971215073L, Fibonacci.computeByRecursion(47));
  }

  @Test
  public void computeByDP() {
    Assert.assertEquals(2, Fibonacci.computeByDP(3));
    Assert.assertEquals(233, Fibonacci.computeByDP(13));
    Assert.assertEquals(2971215073L, Fibonacci.computeByDP(47));
  }
}