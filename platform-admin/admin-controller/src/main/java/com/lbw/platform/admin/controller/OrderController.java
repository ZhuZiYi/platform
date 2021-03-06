package com.lbw.platform.admin.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.lbw.platform.admin.app.entity.Order;
import com.lbw.platform.admin.app.mapper.OrderMapper;
import com.lbw.platform.utils.ApiResult;
import com.lbw.platform.utils.ApiUtil;
import com.lbw.platform.utils.EasyApiResult;
import com.lbw.platform.utils.PageQuery;

@RestController
//@RequestMapping("/order")
public class OrderController {
	@Autowired
	private OrderMapper orderMapper;
	
    @GetMapping("/order/list")
    @ResponseBody
    public ApiResult myOrder(Principal principal, PageQuery<Order> pageQuery, Order order) throws Exception{
    	List<Order> list = orderMapper.getOrderList(order);
    	
    	return ApiUtil.getEasyResult(list);
    }
    
    @GetMapping("/admin/list")
    @ResponseBody
    public String adminList(Principal principal) throws Exception{
    	return principal.getName();
    }
}
