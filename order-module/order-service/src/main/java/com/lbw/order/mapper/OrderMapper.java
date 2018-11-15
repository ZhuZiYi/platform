package com.lbw.order.mapper;

import java.util.List;

import com.lbw.order.entity.Order;

public interface OrderMapper {
	 List<Order> getOrderList(Order order);
}
