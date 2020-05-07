package ca.jrvs.practice.dataStructure.list;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.stream.IntStream;
import org.junit.Before;
import org.junit.Test;

public class LinkedJListIntTest {

  private static final int START = 100;
  private static final int NUMBER_OF_VALUES = 10;
  private LinkedJList<Integer> list;

  @Before
  public void setUp() {
    list = new LinkedJList<>();
  }

  @Test
  public void integrationTest() {
    assertTrue(list.isEmpty());

    // test with single element
    list.add(START);
    assertFalse(list.isEmpty());
    assertEquals(1, list.size());

    list.remove(0);
    assertTrue(list.isEmpty());
    assertEquals(0, list.size());

    // test with multiple elements
    IntStream.range(START, START + NUMBER_OF_VALUES).forEach(list::add);
    assertEquals(NUMBER_OF_VALUES, list.size());
    System.out.println(list);

    // indexOf
    assertEquals(0, list.indexOf(START));
    assertEquals(NUMBER_OF_VALUES - 1, list.indexOf(START + NUMBER_OF_VALUES - 1));
    assertEquals(-1, list.indexOf(START + NUMBER_OF_VALUES));

    // get
    assertEquals(START, list.get(0).intValue());
    assertEquals(START + 1, list.get(1).intValue());

    // corner case
    try {
      list.get(-1);
      fail();
    } catch (IndexOutOfBoundsException e) {
      System.out.println(e.getMessage());
    }
    try {
      list.get(NUMBER_OF_VALUES);
      fail();
    } catch (IndexOutOfBoundsException e) {
      System.out.println(e.getMessage());
    }
    try {
      list.remove(-1);
      fail();
    } catch (IndexOutOfBoundsException e) {
      System.out.println(e.getMessage());
    }
    try {
      list.remove(NUMBER_OF_VALUES);
      fail();
    } catch (IndexOutOfBoundsException e) {
      System.out.println(e.getMessage());
    }

    // toArray()
    Object[] array = list.toArray();
    assertEquals(NUMBER_OF_VALUES, array.length);
    assertEquals(START, array[0]);

    // remove head
    int val = list.remove(0);
    assertEquals(START, val);
    assertFalse(list.contains(val));
    assertTrue(list.contains(START + 1));
    assertEquals(NUMBER_OF_VALUES - 1, list.size());
    // remove tail
    val = list.remove(list.size() - 1);
    assertEquals(START + NUMBER_OF_VALUES - 1, val);
    assertFalse(list.contains(val));
    assertEquals(NUMBER_OF_VALUES - 2, list.size());
    // remove middle
    val = list.remove(list.size() / 2);
    assertFalse(list.contains(val));
    assertEquals(NUMBER_OF_VALUES - 3, list.size());

    // clear
    list.clear();
    assertTrue(list.isEmpty());
    try {
      list.get(0);
      fail();
    } catch (IndexOutOfBoundsException e) {
      System.out.println(e.getMessage());
    }
  }

}