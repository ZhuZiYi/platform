package com.lbw.platform.admin.order.mapper;

import java.util.List;

import com.lbw.platform.admin.order.intf.Order;


public interface OrderMapper {
	 List<Order> getOrderList(Order order);
}
