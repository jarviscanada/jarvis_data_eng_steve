package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ValidParenthesesTest {

  @Test
  public void isValid() {
    assertTrue(ValidParentheses.isValid("()[]{}"));
    assertTrue(ValidParentheses.isValid("{([])}"));
    assertTrue(ValidParentheses.isValid(""));
    assertFalse(ValidParentheses.isValid("(]"));
    assertFalse(ValidParentheses.isValid("([)]"));
  }
}