package ca.jrvs.practice.codingChallenge;

/**
 * ticket: https://www.notion.so/f413acfb1d0f4a98aaa8d54eedc37ff9?v=
 * 0f601e417e034e02adc859125ea08a62&p=c8ba7c4c2eac4ce68110493f3263cf0c
 *
 * Assuming 0th fib number is 0, 1st is 1, 2nd is 1 and so on...
 */
public class Fibonacci {

  /**
   * Big-O: Time O(2^n) Space O(1)
   *
   * Justification: each step creates 2 independent sub-problems before touching base
   * cases, very bad :<
   *
   * @param n positive integer less than 93
   */
  public static long computeByRecursion(long n) {
    checkInput(n);
    if (n == 1) {
      return 1;
    }
    if (n == 2) {
      return 1;
    }
    return computeByRecursion(n - 1) + computeByRecursion(n - 2);
  }

  /**
   * Big-O: Time O(n) Space O(1)
   *
   * Justification: a for loop
   *
   * @param n positive integer less than 93
   */
  public static long computeByDP(long n) {
    checkInput(n);
    long t1 = 1;
    long t2 = 1;
    for (int i = 3; i <= n; i++) {
      long tmp = t1 + t2;
      t1 = t2;
      t2 = tmp;
    }
    return t2;
  }

  private static void checkInput(long n) {
    if (n < 1) {
      throw new IllegalArgumentException("n should be positive");
    }
    if (n > 92) {
      throw new IllegalArgumentException("n will cause overflow");
    }
  }


}
