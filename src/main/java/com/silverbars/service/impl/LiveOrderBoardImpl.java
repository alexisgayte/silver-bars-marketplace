package com.silverbars.service.impl;

import com.silverbars.domain.Order;
import com.silverbars.domain.Order.Type;
import com.silverbars.domain.Summary;
import com.silverbars.service.LiveOrderBoard;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

final public class LiveOrderBoardImpl implements LiveOrderBoard  {

    private static final Function<Entry<OrderKey, Map<UUID, Order>>, Summary> SUMMARY_CONVERTOR_INSTANCE = new SummaryConvertor();

    private final Map<OrderKey, Map<UUID, Order>> sellOrderBoard = new ConcurrentHashMap<>();

    private final Map<OrderKey, Map<UUID, Order>> buyOrderBoard = new ConcurrentHashMap<>();


    @Override
    public void register(Order order) {

        Map<OrderKey, Map<UUID, Order>> orderBoard = selectOrderBoard(order.getType());

        Order insideorder = orderBoard.computeIfAbsent(new OrderKey(order), k -> new ConcurrentHashMap<UUID, Order>())
                                      .computeIfAbsent(order.getUuid(), k -> order);

        if (!insideorder.equals(order)) {
            throw new RuntimeException();
        }
    }


    @Override
    public void cancel(Order order) {
        Map<OrderKey, Map<UUID, Order>> orderBoard = selectOrderBoard(order.getType());
        orderBoard.computeIfPresent(new OrderKey(order), (i, mapOrder) -> {
            mapOrder.remove(order.getUuid());
            return mapOrder.isEmpty() ? null : mapOrder;
        });

    }


    @Override
    public List<Summary> summary(Type type) {

        Map<OrderKey, Map<UUID, Order>> orderBoard = selectOrderBoard(type);

        return orderBoard.entrySet()
                  .stream()
                  .sorted(Map.Entry.comparingByKey())
                  .map(SUMMARY_CONVERTOR_INSTANCE)
                  .collect(Collectors.toList());
    }


    private Map<OrderKey, Map<UUID, Order>> selectOrderBoard(Type type) {
        Map<OrderKey, Map<UUID, Order>> orderBoard;
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