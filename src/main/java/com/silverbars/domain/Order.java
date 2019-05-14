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
            return Long.hashCode(pricePerKg) * type.demoninator;
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
}
