package com.silverbars.domain;

import com.silverbars.domain.Order.Type;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderBuilder {

    private long userId;
    private BigDecimal quantity;
    private BigDecimal pricePerKg;
    private Type type;

    public OrderBuilder userId(long userId) {
        this.userId = userId;
        return this;
    }

    public OrderBuilder quantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public OrderBuilder pricePerKg(BigDecimal pricePerKg) {
        this.pricePerKg = pricePerKg;
        return this;
    }

    public OrderBuilder type(Type type) {
        this.type = type;
        return this;
    }

    public static OrderBuilder builder() {
        return new OrderBuilder();
    }

    public Order build() {
        if (type == null || quantity == null || pricePerKg == null) {
            throw new NullPointerException();
        }

        if (quantity.scale() > 3) {
            throw new IllegalArgumentException();
        }

        if (pricePerKg.scale() > 2) {
            throw new IllegalArgumentException();
        }

        return new Order(UUID.randomUUID(),
                         userId,
                         quantity.movePointRight(3).intValue(),
                         pricePerKg.movePointRight(2).intValue(),
                         type);
    }

}