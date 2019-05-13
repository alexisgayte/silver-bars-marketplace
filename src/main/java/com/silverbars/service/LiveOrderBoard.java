package com.silverbars.service;

import com.silverbars.domain.Order;
import com.silverbars.domain.Order.Type;
import com.silverbars.domain.Summary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

final public class LiveOrderBoard  {

    Map<Order.OrderKey, Map<UUID, Order>> sellOrderBoard = new ConcurrentHashMap<>();

    Map<Order.OrderKey, Map<UUID, Order>> buyOrderBoard = new ConcurrentHashMap<>();

    public void register(Order order) {

        Map<Order.OrderKey, Map<UUID, Order>> orderBoard = selectOrderBoard(order.getType());

        Order insideorder = orderBoard.computeIfAbsent(order.getKey(), k -> new ConcurrentHashMap<UUID, Order>())
                .computeIfAbsent(order.getUuid(), k -> order);

        if (!insideorder.equals(order)) {
            throw new RuntimeException();
        }
    }


    public void cancel(Order order) {
        Map<Order.OrderKey, Map<UUID, Order>> orderBoard = selectOrderBoard(order.getType());
        orderBoard.computeIfPresent(order.getKey(), (i, mapOrder) -> {
            mapOrder.remove(order.getUuid());
            return mapOrder.isEmpty() ? null : mapOrder;
        });

    }


    public List<Summary> summary(Type type) {

        Map<Order.OrderKey, Map<UUID, Order>> orderBoard = selectOrderBoard(type);
        List<Summary> summary = new ArrayList<>(orderBoard.size());
        orderBoard.forEach(populateSummary(summary));

        return summary;
    }

    private BiConsumer<? super Order.OrderKey, ? super Map<UUID, Order>> populateSummary(List<Summary> buySummary) {
        return (key, v) -> {
            long grams = v.values().stream().mapToLong(Order::getQuantity).sum();
            Summary summary = new Summary();
            summary.setPricePerKg(key.pricePerKg());
            summary.setQuantity(grams);

            buySummary.add(summary);
        };
    }


    private Map<Order.OrderKey, Map<UUID, Order>> selectOrderBoard(Type type) {
        Map<Order.OrderKey, Map<UUID, Order>> orderBoard;
        switch (type) {
        case BUY:
            orderBoard = buyOrderBoard;
            break;
        case SELL:
            orderBoard = sellOrderBoard;
            break;
        default:
            throw new RuntimeException();
        }
        return orderBoard;
    }
}