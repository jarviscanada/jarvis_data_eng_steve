package ca.jrvs.practice.dataStructure.list;

import java.util.Arrays;

public class LinkedJList<E> implements JList<E> {

  ListNode<E> first;
  int size = 0;

  @Override
  public boolean add(E e) {
    if (first == null) {
      first = new ListNode<>(e);
    } else {
      ListNode<E> cur = first;
      while (cur.next != null) {
        cur = cur.next;
      }
      cur.next = new ListNode<>(e);
    }
    size++;
    return true;
  }

  @Override
  public Object[] toArray() {
    final Object[] array = new Object[size];
    ListNode<E> cur = first;
    for (int i = 0; i < size; i++) {
      array[i] = cur.val;
      cur = cur.next;
    }
    return array;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public int indexOf(Object o) {
    int index = 0;
    ListNode<E> cur = first;
    while (cur != null && !o.equals(cur.val)) {
      cur = cur.next;
      index++;
    }
    return (cur == null) ? -1 : index;
  }

  @Override
  public boolean contains(Object o) {
    return this.indexOf(o) != -1;
  }

  @Override
  public E get(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException("Index: " + index);
    }
    ListNode<E> cur = first;
    for (int i = 0; i < index; i++) {
      cur = cur.next;
    }
    return cur.val;
  }

  @Override
  public E remove(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException("Index: " + index);
    }
    if (index == 0) {
      return unlinkFirst();
    } else {
      // locate the parent of node to be deleted
      ListNode<E> cur = first;
      for (int i = 0; i < index - 1; i++) {
        cur = cur.next;
      }
      return unlinkNode(cur);
    }
  }

  /**
   * Unlinks non-null first node f.
   */
  private E unlinkFirst() {
    final E value = first.val;
    final ListNode<E> next = first.next;
    first.val = null;
    first.next = null;

    first = next;
    size--;
    return value;
  }

  /**
   * Unlinks node.next
   */
  private E unlinkNode(ListNode<E> node) {
    final ListNode<E> old = node.next;
    final E value = old.val;
    node.next = old.next;
    size--;
    return value;
  }

  @Override
  public void clear() {
    for (ListNode<E> x = first; x != null; ) {
      ListNode<E> next = x.next;
      x.val = null;
      x.next = null;
      x = next;
    }
    first = null;
    size = 0;
  }

  @Override
  public String toString() {
    return Arrays.toString(this.toArray());
  }
}
