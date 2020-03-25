package ca.jrvs.apps.practice;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public interface LambdaStreamExc {

  /**
   * Create a String stream from array
   *
   * @param strings arbitrary number of strings
   * @return stream of strings
   */
  Stream<String> createStrStream(String... strings);

  /**
   * Convert all strings to uppercase
   *
   * @param strings arbitrary number of strings
   * @return stream of strings which all have been converted to uppercase
   */
  Stream<String> toUpperCase(String... strings);

  /**
   * Filter strings that contains the pattern
   *
   * @param stringStream input stream of strings
   * @param pattern      string pattern which you don't like
   * @return stream of strings with unwanted pattern filtered
   */
  Stream<String> filter(Stream<String> stringStream, String pattern);

  /**
   * Create a intStream from a arr[]
   *
   * @param arr input int array
   * @return IntStream
   */
  IntStream createIntStream(int[] arr);

  /**
   * Convert a stream to list
   *
   * @param stream input stream
   * @param <E>    generic
   * @return list constructed from input stream
   */
  <E> List<E> toList(Stream<E> stream);

  /**
   * Convert a intStream to list
   *
   * @param intStream input intStream
   * @return Integer list constructed from input stream
   */
  List<Integer> toList(IntStream intStream);

  /**
   * Create a IntStream range from start to end inclusive
   *
   * @param start start of the range
   * @param end   end of the range
   * @return IntStream range from start to end
   */
  IntStream createIntStream(int start, int end);

  /**
   * Convert a intStream to a doubleStream and compute square root of each element
   *
   * @param intStream input intStream
   * @return doubleStream which each element is the root of the input
   */
  DoubleStream squareRootIntStream(IntStream intStream);

  /**
   * Filter all even number and return odd numbers from a intStream
   *
   * @param intStream input intStream
   * @return intStream with even numbers filtered
   */
  IntStream getOdd(IntStream intStream);

  /**
   * Return a lambda function that print a message with a prefix and suffix This lambda can be
   * useful to format logs.
   * <p>
   * You will learn: - functional interface http://bit.ly/2pTXRwM & http://bit.ly/33onFig - lambda
   * syntax
   * <p>
   * e.g. LambdaStreamExc lse = new LambdaStreamImp(); Consumer<String> printer =
   * lse.getLambdaPrinter("start>", "<end"); printer.accept("Message body");
   * <p>
   * sout: start>Message body<end
   *
   * @param prefix prefix str
   * @param suffix suffix str
   * @return lambda function
   */
  Consumer<String> getLambdaPrinter(String prefix, String suffix);

  /**
   * Print each message with a given printer Please use `getLambdaPrinter` method
   * <p>
   * e.g. String[] messages = {"a","b", "c"}; lse.printMessages(messages,
   * lse.getLambdaPrinter("msg:", "!") );
   * <p>
   * sout: msg:a! msg:b! msg:c!
   *
   * @param messages array of messages
   * @param printer  a lambda printer
   */
  void printMessages(String[] messages, Consumer<String> printer);

  /**
   * Print all odd number from a intStream. Please use `createIntStream` and `getLambdaPrinter`
   * methods
   * <p>
   * e.g. lse.printOdd(lse.createIntStream(0, 5), lse.getLambdaPrinter("odd number:", "!"));
   * <p>
   * sout: odd number:1! odd number:3! odd number:5!
   *
   * @param intStream input intStream
   * @param printer   a lambda printer
   */
  void printOdd(IntStream intStream, Consumer<String> printer);

  /**
   * Square each number from the input. Please write two solutions and compare difference - using
   * flatMap
   *
   * @param ints Stream of list of Integers
   * @return Stream of Integers which each number has been squared
   */
  Stream<Integer> flatNestedInt(Stream<List<Integer>> ints);

}
