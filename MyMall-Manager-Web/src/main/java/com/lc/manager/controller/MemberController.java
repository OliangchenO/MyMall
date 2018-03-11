package com.lc.manager.controller;

import com.lc.common.pojo.DataTablesResult;
import com.lc.manager.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(description = "会员管理")
public class MemberController {
    final static Logger log = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/member/count",method = RequestMethod.GET)
    @ApiOperation(value = "获得总会员数目")
    public DataTablesResult getMemberCount(){
        return memberService.getMemberCount();
    }
}
