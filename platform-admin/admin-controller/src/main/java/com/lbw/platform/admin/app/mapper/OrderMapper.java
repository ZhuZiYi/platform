package com.lbw.platform.admin.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lbw.platform.admin.app.entity.Order;

public interface OrderMapper {
	 List<Order> getOrderList(Order order);
}
