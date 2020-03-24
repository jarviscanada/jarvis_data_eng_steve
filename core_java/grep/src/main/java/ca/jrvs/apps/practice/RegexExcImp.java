package ca.jrvs.apps.practice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexExcImp implements RegexExc {

  public static void main(String[] args) {

    RegexExc myRegex = new RegexExcImp();

    String[] testJpeg = {"abc.jpg", "abc.jpeg", ".jpeg", "abc.jpegx", "jpg"};
    for (String str : testJpeg) {
      System.out.print("'" + str + "' is a valid jpeg file: ");
      System.out.println(myRegex.matchJpeg(str));
    }
    System.out.println();

    String[] testIp = {"192.168.0", "127#0#0#1", "172.16.0.1", "a.b.c.d"};
    for (String str : testIp) {
      System.out.print("'" + str + "' is a valid IP: ");
      System.out.println(myRegex.matchIp(str));
    }
    System.out.println();

    String[] testEmpty = {"", " ", "  ", "\t", "."};
    for (String str : testEmpty) {
      System.out.print("'" + str + "' is an empty line: ");
      System.out.println(myRegex.isEmptyLine(str));
    }
  }

  @Override
  public boolean matchJpeg(String filename) {
    Pattern jpegPattern = Pattern.compile(".+\\.jpe?g$");
    return matchHelper(jpegPattern, filename);
  }

  @Override
  public boolean matchIp(String ip) {
    Pattern ipPattern = Pattern.compile("\\b\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\b");
    return matchHelper(ipPattern, ip);
  }

  @Override
  public boolean isEmptyLine(String line) {
    Pattern emptyPattern = Pattern.compile("^\\s*$");
    return matchHelper(emptyPattern, line);
  }

  private boolean matchHelper(Pattern p, String s) {
    Matcher m = p.matcher(s);
    return m.matches();
  }
}
