# Introduction
The grep app allows user to search for a specific text pattern recursively in a given directory, 
the matched lines will then be written to a file.

There are two versions of this app, [JavaGrepImp](./src/main/java/ca/jrvs/apps/grep/JavaGrepImp.java) 
and [JavaGrepLambdaImp](./src/main/java/ca/jrvs/apps/grep/JavaGrepLambdaImp.java). 
The first implementation takes advantage of Java 8 features including Stream API and Lambda, 
the second one uses the language features available before Java 8, e.g. java.nio, anonymous class, etc.
This project also let us be familiar with the basics of build automation tool [Maven](https://maven.apache.org/) 
and IDE [IntelliJ IDEA](https://www.jetbrains.com/idea/), which are used extensively in Java development.

# Usage
The program takes 3 input arguments, namely `regex pattern`, `search directory`, and `output path`. 
Try the example:
```
.*IllegalArgumentException.* ./src /tmp/grep.out
```

# Pseudocode
For your curiosity, the program internally works as follows:
```r
matchedLines = []
for file in listFilesRecursively(rootDir)
  for line in readLines(file)
      if containsPattern(line)
        matchedLines.add(line)
writeToFile(matchedLines)
```

# Performance Issue
The performance bottleneck is in the space part. To process the matching task, it is necessary to
load all the data into the memory, i.e., saving all the lines in a List. So when the size of files
is larger than available RAM the program will obviously fail. One possible workaround would be 
setting a threshold for the incoming data: when it reached the limit we stop reading files and 
start to process the existing data in the buffer, then after finishing we clear the buffer and 
repeat the previous procedure.

# Improvement (TODO)
1. Use parallelStream to reduce the processing time on multi-core machines.
2. Capability of processing larger-than-memory files. 
3. Record not only matched lines but also the path-to-file.
