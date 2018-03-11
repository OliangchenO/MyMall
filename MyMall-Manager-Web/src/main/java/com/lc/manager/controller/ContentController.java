package com.lc.manager.controller;

import com.lc.common.exception.MyMallException;
import com.lc.common.pojo.DataTablesResult;
import com.lc.common.pojo.Result;
import com.lc.common.utils.ResultUtil;
import com.lc.manager.dto.DtoUtil;
import com.lc.manager.pojo.TbContent;
import com.lc.manager.service.ContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(description = "内容列表信息")
public class ContentController {
    private final static Logger log= LoggerFactory.getLogger(DtoUtil.class);
    @Autowired
    private ContentService contentService;

    @RequestMapping(value = "/content/add",method = RequestMethod.POST)
    @ApiOperation(value = "添加内容")
    public Result<Object> addContent(@ModelAttribute TbContent tbContent){
        contentService.addContent(tbContent);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/content/del/{id}",method = RequestMethod.DELETE)
    @ApiOperation(value = "删除内容")
    public Result<Object> deleteContent(@PathVariable Long id){

        contentService.deleteContent(id);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/content/update",method = RequestMethod.POST)
    @ApiOperation(value = "编辑内容")
    public Result<Object> updateContent(@ModelAttribute TbContent tbContent){
        contentService.updateContent(tbContent);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/content/list/{cid}",method = RequestMethod.GET)
    @ApiOperation(value = "通过cid获得内容列表")
    public DataTablesResult getContentByCid(@PathVariable Long cid){
        DataTablesResult result=contentService.getContentListByCid(cid);
        return result;
    }
}
