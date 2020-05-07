package ca.jrvs.practice.codingChallenge;

/**
 * Ticket: https://www.notion.so/f413acfb1d0f4a98aaa8d54eedc37ff9?v=
 * 0f601e417e034e02adc859125ea08a62&p=fa7d1ecb722941c6823c0e0905d7132f
 */
public class CountPrimes {

  /**
   * Big-O: Time O(n log log n) Space O(n)
   * <p>
   * Justification: Sieve of Eratosthenes
   */
  public static int countPrimes(int n) {
    boolean[] isPrime = new boolean[n];
    for (int i = 2; i < n; i++) {
      isPrime[i] = true;
    }
    for (int i = 2; i * i < n; i++) {
      if (isPrime[i]) {
        for (int j = i * i; j < n; j += i) {
          isPrime[j] = false;
        }
      }
    }
    int sol = 0;
    for (boolean b : isPrime) {
      sol += b ? 1 : 0;
    }
    return sol;
  }

}
