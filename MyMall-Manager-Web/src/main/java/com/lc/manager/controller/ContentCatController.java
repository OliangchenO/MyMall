package com.lc.manager.controller;

import com.lc.common.pojo.ZTreeNode;
import com.lc.manager.dto.DtoUtil;
import com.lc.manager.service.ContentCatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(description = "内容分类信息")
public class ContentCatController {
    private final static Logger log= LoggerFactory.getLogger(DtoUtil.class);
    @Autowired
    private ContentCatService contentCatService;

    @RequestMapping(value = "/content/cat/list",method = RequestMethod.GET)
    @ApiOperation(value = "获得分类列表")
    public List<ZTreeNode> getContentByCid(@RequestParam(name="id", defaultValue="0") Long parentId){
        List<ZTreeNode> list=contentCatService.getContentCatList(parentId);
        return list;
    }
}
