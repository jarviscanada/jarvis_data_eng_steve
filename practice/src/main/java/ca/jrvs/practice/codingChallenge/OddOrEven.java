package ca.jrvs.practice.codingChallenge;

/**
 * Ticket: https://www.notion.so/f413acfb1d0f4a98aaa8d54eedc37ff9?v=
 * 0f601e417e034e02adc859125ea08a62&p=06c2321a4bc343c39cf6b6316b3e423a
 */
public class OddOrEven {

  /**
   * Big-O: O(1) Justification: it's an arithmetic operation
   */
  public static String oddEvenMod(int i) {
    return i % 2 == 0 ? "even" : "odd";
  }

  /**
   * Big-O: O(1) Justification: it's a bit operation, the constant is the size of int
   */
  public static String oddEvenBit(int i) {
    return (i & 1) == 1 ? "odd" : "even";
  }
}
