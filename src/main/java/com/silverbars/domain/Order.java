package com.silverbars.domain;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
@Builder
public final class Order {

    public class OrderKey {
        @Override
        public int hashCode() {
            return new Long(pricePerKg).hashCode() * type.demoninator;
        }

        private OrderKey() {
            super();
        }

        public long pricePerKg() {
            return pricePerKg;
        }

        long demoninator() {
            return type.demoninator;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) return true;
            if (!(o instanceof OrderKey)) return false;
            OrderKey other = (OrderKey) o;
            if (pricePerKg != other.pricePerKg()) return false;
            if (type.demoninator != other.demoninator()) return false;
            return true;
        }
    }

    public enum Type {
        BUY(1), SELL(-1);

        int demoninator;

        private Type(int demoninator) {
            this.demoninator = demoninator;
        }
    }

    UUID uuid;
    long userId;
    long quantity;
    long pricePerKg;
    Type type;

    private Order(UUID uuid, long userId, long quantity, long pricePerKg, Type type) {
        this.uuid = uuid;
        this.userId = userId;
        this.quantity = quantity;
        this.pricePerKg = pricePerKg;
        this.type = type;

    }

    public OrderKey getKey() {
        return new OrderKey();
    }


    public static class OrderBuilder {

        private long userId;
        private long quantity;
        private long pricePerKg;
        private Type type;

        public OrderBuilder userId(long userId) {
            this.userId = userId;
            return this;
        }

        public OrderBuilder quantity(BigDecimal quantity) {
            this.quantity = quantity.movePointRight(3).intValue();
            return this;
        }

        public OrderBuilder pricePerKg(BigDecimal pricePerKg) {
            this.pricePerKg = pricePerKg.movePointRight(2).intValue();
            return this;
        }

        public OrderBuilder type(Type type) {
            this.type = type;
            return this;
        }

        public Order build() {
            if (type == null) {
                throw new NullPointerException();
            }

            return new Order(UUID.randomUUID(), userId, quantity, pricePerKg, type);
        }
    }
}
