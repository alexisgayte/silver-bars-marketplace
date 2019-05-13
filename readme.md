# The Live Order Board


## Features


## Building the application

The project uses [Maven](https://maven.apache.org) as a build tool.

### Running the test

To run the test execute the following command:

```bash
  ./mvn test
```

### Building the application

To build the lib execute the following command:

```bash
  ./mvn install
```

the lib is build in your .m2 repository.


## Using the application

you can import the lib via maven adding the depedency :

```xml
        <dependency>
            <groupId>com.alexisgayte</groupId>
            <artifactId>silver-bars-marketplace</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
```

Or via gradle 

```kotlin

dependencies {
    compile group: 'com.alexisgayte', name: 'silver-bars-marketplace'
}

```

## Usage 

Order can be created via the builder `Order.OrderBuilder.builder()` cf test.

Interactions with the live market are accessible via the LiveOrderBoard interface :
```java
public interface LiveOrderBoard {

    void register(Order order);

    void cancel(Order order);

    List<Summary> summary(Type type);

}
```

LiveOrderBoardImpl is a simple implementation of this previous one.



