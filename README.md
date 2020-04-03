# see-es-vee ![Java CI with Maven](https://github.com/see-es-vee/see-es-vee/workflows/Java%20CI%20with%20Maven/badge.svg?branch=master) [![codecov](https://codecov.io/gh/see-es-vee/see-es-vee/branch/master/graph/badge.svg)](https://codecov.io/gh/see-es-vee/see-es-vee) [![javadoc](https://javadoc.io/badge2/io.github.see-es-vee/see-es-vee/javadoc.svg)](https://javadoc.io/doc/io.github.see-es-vee/see-es-vee)
<br />
<p align="center">
<img alt="see-es-vee-logo" src="https://raw.githubusercontent.com/see-es-vee/see-es-vee/master/docs/see-es-vee.png">
</div>


A high efficiency CSV parsing library for Java 9 and above

see-es-vee is a Java library that can be used to convert CSV Files into their Java Object representation. It can also be used to convert a Java Obects to an equivalent CSV file. see-es-vee can work with arbitrary Java objects including pre-existing objects that you do not have source-code of.

There are a few open-source projects that can convert CSV Files to Java Objects. However, most of them require that you place Java annotations in your classes; something that you can not do if you do not have access to the source-code. Most also do not fully support the use of Java Generics. see-es-vee considers both of these as very important design goals.

### Goals
  * Provide simple way to read .CSV documents into Java Objects and write Java Objects to .CSV files
  * Allow pre-existing unmodifiable objects to be converted to and from a CSV format
  * Extensive support of Java Generics
  * Allow custom representations for objects

### Download

Download the latest stable release .jar [here](https://github.com/see-es-vee/see-es-vee/releases)

Maven:
```xml
<dependency>
  <groupId>io.github.see-es-vee</groupId>
  <artifactId>see-es-vee</artifactId>
  <version>1.0.0</version>
</dependency>
```

### Documentation
  * [API Javadoc](https://www.javadoc.io/doc/io.github.see-es-vee): Documentation for the current release


### Coverage 
![Code Coverage Graph](https://codecov.io/gh/see-es-vee/see-es-vee/graphs/tree.svg)
