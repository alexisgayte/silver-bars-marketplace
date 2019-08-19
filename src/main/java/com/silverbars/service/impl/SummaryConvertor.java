package com.silverbars.service.impl;

import com.silverbars.domain.Order;
import com.silverbars.domain.Summary;

import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.function.Function;

public class SummaryConvertor implements Function<Entry<OrderKey, Map<UUID, Order>>, Summary> {

	@Override
	public Summary apply(Entry<OrderKey, Map<UUID, Order>> entryOrder) {
        long grams = entryOrder.getValue().values().stream().mapToLong(Order::getQuantity).sum();
        return new Summary(grams, entryOrder.getKey().getPricePerKg());

	}
}