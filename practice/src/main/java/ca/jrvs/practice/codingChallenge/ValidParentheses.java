package ca.jrvs.practice.codingChallenge;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

/**
 * Ticket: https://www.notion.so/f413acfb1d0f4a98aaa8d54eedc37ff9?v=
 * 0f601e417e034e02adc859125ea08a62&p=50cc23bb8d134ac9ade6a0f307e293b7
 */
public class ValidParentheses {

  private static final HashMap<Character, Character> TABLE =
      new HashMap<Character, Character>() {{
        put(')', '(');
        put('}', '{');
        put(']', '[');
      }};

  /**
   * Big-O: Time O(n) Space O(n)
   * <p>
   * Justification: One loop. Left parentheses are stored on the stack.
   */
  public static boolean isValid(String s) {
    Deque<Character> stack = new ArrayDeque<>();
    for (char c : s.toCharArray()) {
      if (c == '(' || c == '{' || c == '[') {
        stack.addLast(c);
      } else if (c == ')' || c == '}' || c == ']') {
        if (stack.isEmpty() || !stack.getLast().equals(TABLE.get(c))) {
          return false;
        } else {
          stack.removeLast();
        }
      }
    }
    return stack.isEmpty();
  }

}
