package com.silverbars.domain;

import lombok.Data;

@Data
public final class Summary {

    long quantity;
    long pricePerKg;


    public String toString() {
        return quantity/1000d + "kg for £" + pricePerKg/100d;
    }
}
