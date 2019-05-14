# The Live Order Board

Interview exercise

## Resume

Silver Bars Marketplace interaction.

 - Register an order
 - Cancel a registered order
 - Get the market

### Requirement : 

Imagine we have received the following orders:
- a) SELL: 3.5 kg for £306 [user1]
- b) SELL: 1.2 kg for £310 [user2]
- c) SELL: 1.5 kg for £307 [user3]
- d) SELL: 2.0 kg for £306 [user4]

Our ‘Live Order Board’ should provide us the following summary information:
- 5.5 kg for £306 // order a + order d
- 1.5 kg for £307 // order c
- 1.2 kg for £310 // order b

The first thing to note here is that orders for the same price should be merged together (even when they
are from different users). In this case it can be seen that order a) and d) were for the same amount (£306)
and this is why only their sum (5.5 kg) is displayed (for £306) and not the individual orders (3.5 kg and 2.0
kg).The last thing to note is that for SELL orders the orders with lowest prices are displayed first.
Opposite is true for the BUY orders.

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

the lib should be built in your .m2 repository


## Using the application

you can import the lib via maven for that you need to add the depedency :

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

LiveOrderBoardImpl is an implementation of the previous one.



