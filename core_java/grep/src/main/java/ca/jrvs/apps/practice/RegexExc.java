package ca.jrvs.apps.practice;

public interface RegexExc {

  /**
   * Match filename with extension jpg or jpeg
   * @param filename a filename
   * @return true if filename extension is jpg or jpeg (case insensitive)
   */
  boolean matchJpeg(String filename);

  /**
   * Match an ip address, to simplify the problem,
   * IP address is from 0.0.0.0 to 999.999.999.999
   * @param ip an ip address
   * @return true if given string is a valid
   */
  boolean matchIp(String ip);

  /**
   * Match an empty line (e.g. empty, white space, tabs, etc..)
   * @param line input string
   * @return true if line is empty
   */
  boolean isEmptyLine(String line);

}
