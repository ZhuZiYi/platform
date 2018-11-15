package com.lbw.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.lbw.order.client.OrderClient;
import com.lbw.order.entity.Order;
import com.lbw.utils.ApiResult;
import com.lbw.utils.ApiUtil;
import com.lbw.utils.PageQuery;

@RestController
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private OrderClient orderClient;
	
    @GetMapping("/myOrder")
    @ResponseBody
    public ApiResult myOrder(Principal principal, PageQuery<Order> pageQuery, Order order) throws Exception{
    	List<Order> lstOrder = orderClient.getOrderList(order);
    	
    	pageQuery.setT(order);
        PageInfo<Order> pageInfo = orderClient.getOrderPage(pageQuery);
        
        return ApiUtil.getSuccessResult(pageInfo, "查询成功!");
    }
}
