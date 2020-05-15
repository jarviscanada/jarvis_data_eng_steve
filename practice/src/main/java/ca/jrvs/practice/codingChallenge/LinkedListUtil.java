package ca.jrvs.practice.codingChallenge;

import ca.jrvs.practice.dataStructure.list.ListNode;

public class LinkedListUtil<E> {

  public ListNode<E> middleNode(ListNode<E> head) {
    ListNode<E> slow = head, fast = head;
    while (fast != null && fast.getNext() != null) {
      slow = slow.getNext();
      fast = fast.getNext().getNext();
    }
    return slow;
  }

}
