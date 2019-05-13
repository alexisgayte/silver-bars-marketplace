package com.silverbars.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.silverbars.domain.Order.OrderBuilder;
import com.silverbars.domain.Order.Type;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderTest {

    OrderBuilder orderBuilder;

    @BeforeEach
    public void setup() {

        orderBuilder = Order.builder()
            .pricePerKg(new BigDecimal("303"))
            .quantity(new BigDecimal("10.33"))
            .type(Type.BUY)
            .userId(1111l);

    }

    @Test
    public void should_transform_builder_value_into_order_value() throws Exception {

        Order order = orderBuilder.build();

        assertEquals(order.getPricePerKg(), 30300l);
        assertEquals(order.getQuantity(), 10330l);
        assertEquals(order.getType(), Type.BUY);
        assertEquals(order.getUserId(), 1111l);
    }


    @Test
    public void should_truncate_wrong_price() throws Exception {

        Order order = orderBuilder
                            .pricePerKg(new BigDecimal("303.011"))
                            .build();

        assertEquals(order.getPricePerKg(), 30301l);
    }

    @Test
    public void should_truncate_wrong_quantity() throws Exception {

        Order order = orderBuilder
                            .quantity(new BigDecimal("30.0011"))
                            .build();

        assertEquals(order.getQuantity(), 30001l);
    }

    @Test
    public void should_not_accept_null_type() throws Exception {

        orderBuilder.type(null);

        assertThrows(NullPointerException.class, () -> {
            orderBuilder.build();
          });
    }
}