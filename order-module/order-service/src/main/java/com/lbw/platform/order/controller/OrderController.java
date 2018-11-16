package com.lbw.platform.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lbw.platform.order.entity.Order;
import com.lbw.platform.order.mapper.OrderMapper;
import com.lbw.platform.utils.PageQuery;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderMapper orderMapper;

    @PostMapping("/get_order_page")
    public PageInfo<Order> getOrderPage(@RequestBody PageQuery<Order> pageQuery){
        PageHelper.startPage(pageQuery.getPageNum(),pageQuery.getPageSize());
        List<Order> orderList=orderMapper.getOrderList(pageQuery.getT());
        PageInfo<Order> pageInfo=new PageInfo<Order>(orderList);
        return pageInfo;
    }
    
    @PostMapping("/get_order_list")
    public List<Order> getOrderList(@RequestBody Order order){
        return orderMapper.getOrderList(order);
    }
}