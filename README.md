# see-es-vee 
![Java CI with Maven](https://github.com/see-es-vee/see-es-vee/workflows/Java%20CI%20with%20Maven/badge.svg?branch=master) [![codecov](https://codecov.io/gh/see-es-vee/see-es-vee/branch/master/graph/badge.svg)](https://codecov.io/gh/see-es-vee/see-es-vee) [![javadoc](https://javadoc.io/badge2/io.github.see-es-vee/see-es-vee/javadoc.svg)](https://javadoc.io/doc/io.github.see-es-vee/see-es-vee) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.see-es-vee/see-es-vee/badge.svg)](https://search.maven.org/artifact/io.github.see-es-vee/see-es-vee/1.0.0/jar) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/b8c362d38e454ff08dc9485ca3b1ab1d)](https://www.codacy.com/gh/see-es-vee/see-es-vee?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=see-es-vee/see-es-vee&amp;utm_campaign=Badge_Grade) [![GitHub license](https://img.shields.io/github/license/see-es-vee/see-es-vee)](https://github.com/see-es-vee/see-es-vee/blob/master/LICENSE)

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

### Examples

#### Creating a CSV Reader
```java
// Instantiation of new parse builder of our "Data" class
CSVParseBuilder<Data> parsebuilder = new CSVParseBuilder<>();

// Set the parse builder to use the "Data" class
parsebuilder.setClass(Data.class);

// Create the new parser using the "create()" function on the the parse builder
CSVParser<Data> csvParser = parsebuilder.create();

// return array of "Data" parsed from the .CSV file.
ArrayList<Data> datas = csvParser.parse(new File("ExampleInput.csv"));
```

#### Creating a CSV Writer
```java
/**
 *  return array of "Data" parsed from the .CSV file.
 *  see the above example to view how this was done.
**/
ArrayList<Data> datas = csvParser.parse(new File("test.csv"));

// Create a new CSVWriteBuilder with our "Data" class
CSVWriteBuilder<Data> writeBuilder = new CSVWriteBuilder<>();

// Set the class to "Data" using the "setClass()" method
writeBuilder.setClass(Data.class);

// Create the new CSVWriter using the ".create()" function on your write builder
CSVWriter<Data> writer = writeBuilder.create();

// Pass in the file name/location that you want to write to, and the ArrayList of "Data" objects to write from.
writer.write(new File("ExampleOutput.csv"), datas);
```

#### Creating a Handler

* In this example we can see that we simply create a new Handler - in this case a "DateHandler" that extends the Handler class and implements the provided abstract methods. 

* "handleRead" takes the given "value" that was parsed from the CSV File, and reads it in expecting it to be a "yyyy-MM-dd" format, once the value is parsed, it then uses the "setSafely()" function to set the new parsed Date in a specific "field" in the given "object".

* "handleWrite" creates a new simple date format with the pattern "yyyy-MM-dd", we then use the "getSafely()" function to get the Date value from the "object" and "field" and returns it as a String. 

* This is just one small but powerful example of how the Handler class can be used, these can be implemented in any which-way that you'd like in order to handle the datatypes of your choice.

```java
package io.github.seeesvee.handlers;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date datatype handler.
 */
public class DateHandler extends Handler {
    @Override
    public void handleRead(String value, Object object, Field field) throws IllegalAccessException, ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = df.parse(value);
        this.setSafely(value.length() < 1 ? null : currentDate, object, field);
    }

    @Override
    public String handleWrite(Object object, Field field) throws IllegalAccessException {
        String datePattern = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
        return sdf.format((Date) getSafely(object, field));
    }
}
```

#### Using Your Handler(s)

Using a custom made Handler is as simple as calling a method - this is the same in both the Parse Builder, and Write Builder. 

```java
// Instantiation of new parse builder of our "Data" class
CSVParseBuilder<Data> parsebuilder = new CSVParseBuilder<>();

// Set the parse builder to use the "Data" class
parsebuilder.setClass(Data.class);

// Add our newly created "DateHandler" from the above example
parsebuilder.addHandler(Date.class, new DateHandler());
```
