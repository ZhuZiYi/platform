package com.lbw.order.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;
import com.lbw.order.entity.Order;
import com.lbw.utils.PageQuery;

@FeignClient("order-service")
@RequestMapping("/order")
public interface OrderClient {
	@PostMapping("/get_order_list")
	public List<Order> getOrderList(@RequestBody Order order);

	@PostMapping("/get_order_page")
	public PageInfo<Order> getOrderPage(@RequestBody PageQuery<Order> pageQuery);

}
