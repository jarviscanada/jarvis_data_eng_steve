package ca.jrvs.practice.dataStructure.list;

public class ListNode<E> {

  E val;
  ListNode<E> next;

  public ListNode() {
  }

  public ListNode(E val) {
    this.val = val;
  }

  public ListNode(E val, ListNode<E> next) {
    this.val = val;
    this.next = next;
  }

  public E getVal() {
    return val;
  }

  public void setVal(E val) {
    this.val = val;
  }

  public ListNode<E> getNext() {
    return next;
  }

  public void setNext(ListNode<E> next) {
    this.next = next;
  }

}
