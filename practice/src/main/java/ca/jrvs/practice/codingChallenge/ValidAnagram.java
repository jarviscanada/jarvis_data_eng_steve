package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Ticket: https://www.notion.so/f413acfb1d0f4a98aaa8d54eedc37ff9?v=
 * 0f601e417e034e02adc859125ea08a62&p=0ab0e5309a094a7389de26acb859e785
 */
public class ValidAnagram {

  /**
   * Sorting approach
   * <p>
   * Big-O: Time O(n log n) Space O(n)
   * <p>
   * Justification: we sorted extra array
   */
  public static boolean validBySorting(String s, String t) {
    char[] sArray = s.toCharArray();
    Arrays.sort(sArray);
    char[] tArray = t.toCharArray();
    Arrays.sort(tArray);
    return Arrays.equals(sArray, tArray);
  }

  /**
   * Sorting approach
   * <p>
   * Big-O: Time O(n) Space O(n)
   * <p>
   * Justification: 2 loops, map operations take constant time.
   */
  public static boolean validByRecord(String s, String t) {
    if (s.length() != t.length()) {
      return false;
    }
    HashMap<Character, Integer> record = new HashMap<>();
    for (char cs : s.toCharArray()) {
      record.put(cs, record.getOrDefault(cs, 0) + 1);
    }
    for (char ct : t.toCharArray()) {
      int count = record.getOrDefault(ct, 0);
      if (count == 0) {
        return false;
      }
      record.put(ct, count - 1);
    }
    return true;
  }
}
