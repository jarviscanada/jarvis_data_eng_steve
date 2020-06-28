package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ValidAnagramTest {

  @Test
  public void validBySorting() {
    assertTrue(ValidAnagram.validBySorting("anagram", "nagaram"));
    assertFalse(ValidAnagram.validBySorting("anagram", "nagaran"));
    assertTrue(ValidAnagram.validBySorting("語言", "言語"));
  }

  @Test
  public void validByRecord() {
    assertTrue(ValidAnagram.validByRecord("anagram", "nagaram"));
    assertFalse(ValidAnagram.validByRecord("anagram", "nagaran"));
    assertTrue(ValidAnagram.validByRecord("語言", "言語"));
  }
}