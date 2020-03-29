# see-es-vee ![Java CI with Maven](https://github.com/see-es-vee/see-es-vee/workflows/Java%20CI%20with%20Maven/badge.svg?branch=master)

<br />
<p align="center">
<img alt="see-es-vee-logo" src="https://raw.githubusercontent.com/see-es-vee/see-es-vee/master/docs/see-es-vee.png">
</div>


A high efficiency CSV parsing library for Java 9 and above

see-es-vee is a Java library that can be used to convert CSV Files into their Java Object representation. It can also be used to convert a Java Obects to an equivalent CSV file. see-es-vee can work with arbitrary Java objects including pre-existing objects that you do not have source-code of.

There are a few open-source projects that can convert CSV Files to Java Objects. However, most of them require that you place Java annotations in your classes; something that you can not do if you do not have access to the source-code. Most also do not fully support the use of Java Generics. see-es-vee considers both of these as very important design goals.

### Goals
  * Provide simple `toJson()` and `fromJson()` methods to convert Java objects to JSON and vice-versa
  * Allow pre-existing unmodifiable objects to be converted to and from JSON
  * Extensive support of Java Generics
  * Allow custom representations for objects
  * Support arbitrarily complex objects (with deep inheritance hierarchies and extensive use of generic types)

### Download

Maven:
```xml
<dependency>
  <groupId>io.github.see-es-vee/see-es-vee</groupId>
  <artifactId>see-es-vee</artifactId>
  <version>1.0.0</version>
</dependency>
```

[see-es-vee jar downloads](https://maven-badges.herokuapp.com/maven-central/io.github.see-es-vee/see-es-vee) are available from Maven Central.


### Documentation
  * [API Javadoc](https://www.javadoc.io/doc/io.github.see-es-vee/see-es-vee): Documentation for the current release


### Coverage 
![Code Coverage Graph](https://codecov.io/gh/see-es-vee/see-es-vee/graphs/tree.svg)
