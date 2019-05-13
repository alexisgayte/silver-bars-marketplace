package com.silverbars.service;

import com.silverbars.domain.Order;
import com.silverbars.domain.Order.Type;
import com.silverbars.domain.Summary;

import java.util.List;

public interface LiveOrderBoard {

    void register(Order order);

    void cancel(Order order);

    List<Summary> summary(Type type);

}