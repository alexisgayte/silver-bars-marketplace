package com.silverbars.domain;

import java.util.UUID;

public final class Order {

    public enum Type {
        BUY, SELL;
    }

    private final UUID uuid;
    private final long userId;
    private final long quantity;
    private final long pricePerKg;
    private final Type type;

    Order(UUID uuid, long userId, long quantity, long pricePerKg, Type type) {
        this.uuid = uuid;
        this.userId = userId;
        this.quantity = quantity;
        this.pricePerKg = pricePerKg;
        this.type = type;
    }

    public UUID getUuid() {
        return uuid;
    }

    public long getUserId() {
        return userId;
    }

    public long getQuantity() {
        return quantity;
    }

    public long getPricePerKg() {
        return pricePerKg;
    }

    public Type getType() {
        return type;
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Order)) return false;
        Order other = (Order) o;
        if (uuid != other.uuid) return false;
        if (userId != other.userId) return false;
        if (quantity != other.quantity) return false;
        if (pricePerKg != other.pricePerKg) return false;
        if (type != other.type) return false;

        return true;
    }
}
