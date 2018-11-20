package com.lbw.platform.admin.mapper;

import java.util.List;

import com.lbw.platform.admin.entity.Order;


public interface OrderMapper {
	 List<Order> getOrderList(Order order);
}
