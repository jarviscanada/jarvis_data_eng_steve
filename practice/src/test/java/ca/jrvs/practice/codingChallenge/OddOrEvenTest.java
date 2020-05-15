package ca.jrvs.practice.codingChallenge;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OddOrEvenTest {

  @BeforeClass
  public static void classSetUp() {
    System.out.println("--@BeforeClass static method runs once before class it instantiate");
  }

  @AfterClass
  public static void classTearDown() {
    System.out.println("--@AfterClass static method runs once");
  }

  @Before
  public void setUp() {
    System.out.println("--@Before method runs before each @Test method");
  }

  @After
  public void tearDown() {
    System.out.println("--@After method runs after each @Test method");
  }

  @Test
  public void oddEvenMod() {
    System.out.println("Test case: test oddEvenMod method from the test class");
    String expected = "odd";
    Assert.assertEquals(expected, OddOrEven.oddEvenMod(3));
    expected = "even";
    Assert.assertEquals(expected, OddOrEven.oddEvenMod(4));
    expected = "even";
    Assert.assertEquals(expected, OddOrEven.oddEvenMod(0));
  }

  @Test
  public void oddEvenBit() {
    System.out.println("Test case: test oddEvenBit method from the test class");
    String expected = "odd";
    Assert.assertEquals(expected, OddOrEven.oddEvenMod(3));
    expected = "even";
    Assert.assertEquals(expected, OddOrEven.oddEvenMod(4));
    expected = "even";
    Assert.assertEquals(expected, OddOrEven.oddEvenMod(0));
  }
}