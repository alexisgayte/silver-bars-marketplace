package com.silverbars.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.silverbars.domain.Order;
import com.silverbars.domain.Order.Type;
import com.silverbars.domain.OrderBuilder;
import com.silverbars.domain.Summary;
import com.silverbars.service.impl.LiveOrderBoardImpl;

import java.math.BigDecimal;
import java.util.List;

import static com.silverbars.domain.Order.Type.BUY;
import static com.silverbars.domain.Order.Type.SELL;
import static org.junit.jupiter.api.Assertions.assertEquals;

final public class LiveOrderBoardImplTest {

    OrderBuilder orderBuilder;

    LiveOrderBoard liveOrderBoard;

    @BeforeEach
    public void setup() {

        orderBuilder = OrderBuilder.builder()
                        .pricePerKg(new BigDecimal("303"))
                        .quantity(new BigDecimal("10.33"))
                        .type(BUY)
                        .userId(1111l);

        liveOrderBoard = new LiveOrderBoardImpl();

    }

    @Test
    public void should_add_an_order() throws Exception {

        Order order = orderBuilder.build();

        liveOrderBoard.register(order);

        List<Summary> summary = liveOrderBoard.summary(BUY);

        assertEquals("[10.33kg for £303.0]", summary.toString());
    }


    @Test
    public void should_remove_an_order() throws Exception {

        Order order = orderBuilder.build();

        liveOrderBoard.register(order);
        liveOrderBoard.cancel(order);

        List<Summary> summary = liveOrderBoard.summary(BUY);

        assertEquals("[]", summary.toString());
    }

    @Test
    public void should_add_two_orders() throws Exception {

        Order order = orderBuilder.build();
        Order nextOrder = orderBuilder
                            .pricePerKg(new BigDecimal("304"))
                            .quantity(new BigDecimal("1.33"))
                            .build();

        liveOrderBoard.register(order);
        liveOrderBoard.register(nextOrder);

        List<Summary> summary = liveOrderBoard.summary(BUY);

        assertEquals("[1.33kg for £304.0, 10.33kg for £303.0]", summary.toString());
    }

    @Test
    public void should_add_two_orders_with_same_price() throws Exception {

        Order order = orderBuilder.build();
        Order nextOrder = orderBuilder
                            .pricePerKg(new BigDecimal("303"))
                            .quantity(new BigDecimal("1.33"))
                            .build();

        liveOrderBoard.register(order);
        liveOrderBoard.register(nextOrder);

        List<Summary> summary = liveOrderBoard.summary(BUY);

        assertEquals("[11.66kg for £303.0]", summary.toString());
    }

    @Test
    public void should_add_tree_orders_with_order() throws Exception {

        Order order = orderBuilder.build();
        Order nextOrder = orderBuilder
                            .pricePerKg(new BigDecimal("301"))
                            .quantity(new BigDecimal("1.33"))
                            .build();

        Order lastOrder = orderBuilder
                            .pricePerKg(new BigDecimal("302"))
                            .quantity(new BigDecimal("12.33"))
                            .build();

        liveOrderBoard.register(order);
        liveOrderBoard.register(nextOrder);
        liveOrderBoard.register(lastOrder);

        List<Summary> summary = liveOrderBoard.summary(BUY);

        assertEquals("[10.33kg for £303.0, 12.33kg for £302.0, 1.33kg for £301.0]", summary.toString());
    }


    @Test
    public void should_add_tree_orders_with_mix_order() throws Exception {

        Order order = orderBuilder.build();
        Order nextOrder = orderBuilder
                            .type(Type.SELL)
                            .pricePerKg(new BigDecimal("304"))
                            .quantity(new BigDecimal("1.33"))
                            .build();

        Order lastOrder = orderBuilder
                            .type(Type.SELL)
                            .pricePerKg(new BigDecimal("302"))
                            .quantity(new BigDecimal("12.33"))
                            .build();

        liveOrderBoard.register(order);
        liveOrderBoard.register(nextOrder);
        liveOrderBoard.register(lastOrder);

        List<Summary> summary = liveOrderBoard.summary(SELL);

        assertEquals("[12.33kg for £302.0, 1.33kg for £304.0]", summary.toString());
    }
}