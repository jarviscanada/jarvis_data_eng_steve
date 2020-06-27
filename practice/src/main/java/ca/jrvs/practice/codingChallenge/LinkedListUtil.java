package ca.jrvs.practice.codingChallenge;

import ca.jrvs.practice.dataStructure.list.ListNode;

public class LinkedListUtil<E> {

  /**
   * Big-O: Time O(n) Space O(1)
   * <p>
   * Justification: two pointers one pass
   */
  public ListNode<E> middleNode(ListNode<E> head) {
    ListNode<E> slow = head, fast = head;
    while (fast != null && fast.getNext() != null) {
      slow = slow.getNext();
      fast = fast.getNext().getNext();
    }
    return slow;
  }

  /**
   * Big-O: Time O(n) Space O(1)
   * <p>
   * Justification: two pointers one pass, fast pointer will eventually catch up slow pointer if
   * cycle exists
   */
  public boolean hasCycle(ListNode<E> head) {
    ListNode<E> slow = head, fast = head;
    while (fast != null && fast.getNext() != null) {
      slow = slow.getNext();
      fast = fast.getNext().getNext();
      if (slow == fast) {
        return true;
      }
    }
    return false;
  }

  /**
   * Big-O: Time O(n) Space O(1)
   * <p>
   * Justification: two pointers one pass
   */
  public ListNode<E> removeNthFromEnd(ListNode<E> head, int n) {
    ListNode<E> dummy = new ListNode<>();
    dummy.setNext(head);
    ListNode<E> first = dummy, second = dummy;
    for (int i = 0; i <= n; i++) {
      if (first == null) {
        // n greater than size of list!
        return head;
      }
      first = first.getNext();

    }
    while (first != null) {
      first = first.getNext();
      second = second.getNext();
    }
    if (second.getNext() != null) {
      second.setNext(second.getNext().getNext());
    }
    return dummy.getNext();
  }

}
