package ca.jrvs.practice.codingChallenge;

/**
 * Given a string, determine if it is a palindrome, considering only alphanumeric characters and
 * ignoring cases. Ticket: https://www.notion.so/f413acfb1d0f4a98aaa8d54eedc37ff9?v=
 * 0f601e417e034e02adc859125ea08a62&p=0e50daa0d3c44facaf8f8ed12405faca
 */
public class ValidPalindrome {

  /**
   * Recursion approach
   * <p>
   * Big-O: Time O(n) Space O(n) where n is the length of input
   * <p>
   * Justification: about n/2 recursive calls will be made, the recursion depth is also about n/2
   */
  public static boolean validByRecursion(String s) {
    return validByRecursion(s, 0, s.length() - 1);
  }

  private static boolean validByRecursion(String s, int head, int tail) {
    if (head >= tail) {
      return true;
    }
    while (!Character.isLetterOrDigit(s.charAt(head)) && head < tail) {
      head++;
    }
    while (!Character.isLetterOrDigit(s.charAt(tail)) && head < tail) {
      tail--;
    }
    if (Character.toLowerCase(s.charAt(head)) !=
        Character.toLowerCase(s.charAt(tail))) {
      return false;
    }
    return validByRecursion(s, head + 1, tail - 1);
  }

  /**
   * Two pointer approach
   * <p>
   * Big-O: Time O(n) Space O(1) where n is the length of input
   * <p>
   * Justification: one loop two pointers
   */
  public static boolean validByLoop(String s) {
    int head = 0, tail = s.length() - 1;
    while (head < tail) {
      while (!Character.isLetterOrDigit(s.charAt(head)) && head < tail) {
        head++;
      }
      while (!Character.isLetterOrDigit(s.charAt(tail)) && head < tail) {
        tail--;
      }
      if (Character.toLowerCase(s.charAt(head)) !=
          Character.toLowerCase(s.charAt(tail))) {
        return false;
      }
      head++;
      tail--;
    }
    return true;
  }

}
