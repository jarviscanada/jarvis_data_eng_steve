package ca.jrvs.practice.codingChallenge;

import java.util.Map;

/**
 * Ticket: https://www.notion.so/f413acfb1d0f4a98aaa8d54eedc37ff9?v=
 * 0f601e417e034e02adc859125ea08a62&p=14f498137c9b4af1ad08f794747040a4
 */
public class CompareMaps<K, V> {

  /**
   * Using Java Collection .equals API
   */
  public boolean compareMapsDefault(Map<K, V> m1, Map<K, V> m2) {
    return m1.equals(m2);
  }

  /**
   * Self implementation
   * <p>
   * Big-O: Time O(n) Space O(1)
   * <p>
   * Justification: we go through each key-value pair in m1, every time it takes constant time to
   * lookup value in m2 and do the comparision
   */
  public boolean compareMapsManual(Map<K, V> m1, Map<K, V> m2) {
    if (m1.size() != m2.size()) {
      return false;
    }
    return m1.entrySet().stream().allMatch(
        e -> e.getValue().equals(m2.get(e.getKey()))
    );
  }
}
