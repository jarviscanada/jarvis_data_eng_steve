package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
}