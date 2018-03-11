package com.lc.manager.controller;

import com.lc.common.pojo.Result;
import com.lc.common.utils.ResultUtil;
import com.lc.manager.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@Api(description= "订单管理")
public class OrderController {
    private final static org.slf4j.Logger log= LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/order/count",method = RequestMethod.GET)
    @ApiOperation(value = "获取订单总数")
    public Result<Object> getOrderCount(){
        Long result=orderService.countOrder();
        return new ResultUtil<Object>().setData(result);
    }
}
