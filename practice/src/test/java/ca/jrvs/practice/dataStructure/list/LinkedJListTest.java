package ca.jrvs.practice.dataStructure.list;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class LinkedJListTest {

  private LinkedJList<Integer> list;

  @Before
  public void setUp() {
    list = new LinkedJList<>();
    list.add(100);
    list.add(100);
    list.add(200);
    list.add(300);
    list.add(300);
  }

  @Test
  public void unique() {
    list.unique();
    assertEquals(3, list.size());

    list.add(400);
    list.unique();
    assertEquals(4, list.size());

    list.clear();
    list.unique();
    assertEquals(0, list.size());
  }
}