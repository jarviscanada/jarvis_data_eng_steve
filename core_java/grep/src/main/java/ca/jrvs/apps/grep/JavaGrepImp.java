package ca.jrvs.apps.grep;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaGrepImp implements JavaGrep {

  final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

  private String regex;
  private String rootPath;
  private String outFile;

  public JavaGrepImp(String regex, String rootPath, String outFilePath) {
    this.regex = regex;
    this.rootPath = rootPath;
    this.outFile = outFilePath;
  }

  public JavaGrepImp() {
  }

  public static void main(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException("Usage: JavaGrep [regex] [rootPath] [outFilePath]");
    }
    JavaGrepImp grep = new JavaGrepImp(args[0], args[1], args[2]);
    BasicConfigurator.configure(); // configure log4j

    try {
      grep.process();
    } catch (Exception e) {
      grep.logger.error(e.getMessage(), e);
    }
  }

  @Override
  public void process() throws IOException {
    List<String> matchedLines = new LinkedList<>();
    for (File f : listFiles(this.rootPath)) {
      for (String s : readLines(f)) {
        if (containsPattern(s)) {
          matchedLines.add(s);
        }
      }
    }
    writeToFile(matchedLines);
  }

  @Override
  public List<File> listFiles(String rootDir) {
    List<File> ls = new LinkedList<>();
    try {
      Files.walkFileTree(Paths.get(rootDir), new SimpleFileVisitor<Path>() {
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
          ls.add(file.toFile());
          return super.visitFile(file, attrs);
        }
      });
    } catch (IOException e) {
      this.logger.error(e.getMessage(), e);
    }
    return ls;
  }

  @Override
  public List<String> readLines(File inputFile) {
    List<String> lines = new LinkedList<>();
    try (Scanner scanner = new Scanner(inputFile)) {
      while (scanner.hasNextLine()) {
        lines.add(scanner.nextLine());
      }
    } catch (FileNotFoundException e) {
      this.logger.error(e.getMessage(), e);
    }
    return lines;
  }

  @Override
  public boolean containsPattern(String line) {
    Pattern p = Pattern.compile(this.regex);
    Matcher m = p.matcher(line);
    return m.matches();
  }

  @Override
  public void writeToFile(List<String> lines) throws IOException {
    try (PrintWriter out = new PrintWriter(this.outFile)) {
      for (String s : lines) {
        out.println(s);
      }
      if (out.checkError()) {
        throw new IOException("PrintWriter has encountered an error.");
      }
    }
  }

  @Override
  public String getRegex() {
    return regex;
  }

  @Override
  public void setRegex(String regex) {
    this.regex = regex;
  }

  @Override
  public String getRootPath() {
    return rootPath;
  }

  @Override
  public void setRootPath(String rootPath) {
    this.rootPath = rootPath;
  }

  @Override
  public String getOutFile() {
    return outFile;
  }

  @Override
  public void setOutFile(String outFile) {
    this.outFile = outFile;
  }

}
