package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import ca.jrvs.practice.dataStructure.list.ListNode;
import org.junit.Before;
import org.junit.Test;

public class LinkedListUtilTest {

  private final LinkedListUtil<Integer> util = new LinkedListUtil<>();
  private ListNode<Integer> head;

  @Before
  public void setUp() {
  }

  @Test
  public void middleNode() {
    assertNull(util.middleNode(head));

    head = new ListNode<>(100);
    ListNode<Integer> middle = util.middleNode(head);
    assertEquals(100, middle.getVal().intValue());

    head.setNext(new ListNode<>(200));
    middle = util.middleNode(head);
    assertEquals(200, middle.getVal().intValue());

    head.getNext().setNext(new ListNode<>(300));
    middle = util.middleNode(head);
    assertEquals(200, middle.getVal().intValue());

    head.getNext().getNext().setNext(new ListNode<>(400));
    middle = util.middleNode(head);
    assertEquals(300, middle.getVal().intValue());
  }

  @Test
  public void hasCycle() {
    assertFalse(util.hasCycle(head));

    head = new ListNode<>(100);
    head.setNext(new ListNode<>(200));
    assertFalse(util.hasCycle(head));

    head.getNext().setNext(head);
    assertTrue(util.hasCycle(head));
  }

  @Test
  public void removeNthFromEnd() {
    // remove null
    assertNull(util.removeNthFromEnd(head, 1));

    head = new ListNode<>(100);

    // do nothing
    assertEquals(head, util.removeNthFromEnd(head, 0));
    assertEquals(head, util.removeNthFromEnd(head, 2));

    head.setNext(new ListNode<>(200));
    ListNode<Integer> tail = new ListNode<>(300);
    head.getNext().setNext(tail);

    // remove middle
    assertEquals(head, util.removeNthFromEnd(head, 2));
    assertEquals(tail, head.getNext());

    // remove head
    assertEquals(tail, util.removeNthFromEnd(head, 2));

    // remove only node
    assertNull(util.removeNthFromEnd(tail, 1));
  }
}