package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ValidPalindromeTest {

  private static final String VALID1 = "amanaplanacanalpanama";
  private static final String VALID2 = "A man, a plan, a canal: Panama";
  private static final String VALID3 = "A";
  private static final String VALID4 = "";
  private static final String INVALID1 = "race a car";
  private static final String INVALID2 = "0P";

  @Test
  public void validByRecursion() {
    assertTrue(ValidPalindrome.validByRecursion(VALID1));
    assertTrue(ValidPalindrome.validByRecursion(VALID2));
    assertTrue(ValidPalindrome.validByRecursion(VALID3));
    assertTrue(ValidPalindrome.validByRecursion(VALID4));
    assertFalse(ValidPalindrome.validByRecursion(INVALID1));
    assertFalse(ValidPalindrome.validByRecursion(INVALID2));
  }

  @Test
  public void validByLoop() {
    assertTrue(ValidPalindrome.validByLoop(VALID1));
    assertTrue(ValidPalindrome.validByLoop(VALID2));
    assertTrue(ValidPalindrome.validByLoop(VALID3));
    assertTrue(ValidPalindrome.validByLoop(VALID4));
    assertFalse(ValidPalindrome.validByLoop(INVALID1));
    assertFalse(ValidPalindrome.validByLoop(INVALID2));
  }
}