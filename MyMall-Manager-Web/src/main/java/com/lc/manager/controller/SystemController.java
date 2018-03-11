package com.lc.manager.controller;

import com.lc.common.pojo.Result;
import com.lc.common.utils.IPInfoUtil;
import com.lc.common.utils.ResultUtil;
import com.lc.manager.pojo.TbBase;
import com.lc.manager.pojo.TbOrderItem;
import com.lc.manager.service.SystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(description= "系统配置管理")
public class SystemController {
    @Autowired
    private SystemService systemService;

    @RequestMapping(value = "/sys/base",method = RequestMethod.GET)
    @ApiOperation(value = "获取基本设置")
    public Result<TbBase> getBase(){
        TbBase tbBase=systemService.getBase();
        return new ResultUtil<TbBase>().setData(tbBase);
    }

    @RequestMapping(value = "/sys/weather",method = RequestMethod.GET)
    @ApiOperation(value = "获取天气信息")
    public Result<Object> getWeather(HttpServletRequest request){
        String result= IPInfoUtil.getIpInfo(IPInfoUtil.getIpAddr(request));
        return new ResultUtil<Object>().setData(result);
    }

    @RequestMapping(value = "/sys/weekHot",method = RequestMethod.GET)
    @ApiOperation(value = "获取本周热销商品数据")
    public Result<TbOrderItem> getWeekHot(){
        TbOrderItem tbOrderItem=systemService.getWeekHot();
        return new ResultUtil<TbOrderItem>().setData(tbOrderItem);
    }
}
