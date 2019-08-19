package com.silverbars.domain;

public final class Summary {

    long quantity;
    long pricePerKg;

    public Summary(long quantity, long pricePerKg) {
        super();
        this.quantity = quantity;
        this.pricePerKg = pricePerKg;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getPricePerKg() {
        return pricePerKg;
    }

    public void setPricePerKg(long pricePerKg) {
        this.pricePerKg = pricePerKg;
    }

    public String toString() {
        return quantity/1000d + "kg for £" + pricePerKg/100d;
    }
}
