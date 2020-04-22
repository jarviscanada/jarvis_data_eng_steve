package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class CompareMapsTest {

  private Map<String, String> capitalOne;
  private Map<String, String> capitalTwo;
  private Map<String, String> capitalThree;
  private Map<String, String> cityOne;

  private final CompareMaps<String, String> compare = new CompareMaps<>();

  @Before
  public void setUp() {
    capitalOne = new HashMap<>();
    capitalOne.put("SK", "Regina");
    capitalOne.put("AB", "Edmonton");

    capitalTwo = new HashMap<>();
    capitalTwo.put("SK", "Regina");
    capitalTwo.put("AB", "Edmonton");

    capitalThree = new HashMap<>();
    capitalThree.put("SK", "Regina");
    capitalThree.put("MB", "Winnipeg");

    cityOne = new HashMap<>();
    cityOne.put("SK", "Regina");
    cityOne.put("AB", "Calgary");
  }

  @Test
  public void compareMapsDefault() {
    assertTrue(compare.compareMapsDefault(capitalOne, capitalTwo));
    assertFalse(compare.compareMapsDefault(capitalOne, capitalThree));
    assertFalse(compare.compareMapsDefault(capitalOne, cityOne));
  }

  @Test
  public void compareMapsManual() {
    assertTrue(compare.compareMapsDefault(capitalOne, capitalTwo));
    assertFalse(compare.compareMapsDefault(capitalOne, capitalThree));
    assertFalse(compare.compareMapsDefault(capitalOne, cityOne));
  }
}