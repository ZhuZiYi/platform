package com.lbw.platform.order.mapper;

import java.util.List;

import com.lbw.platform.order.entity.Order;

public interface OrderMapper {
	 List<Order> getOrderList(Order order);
}
