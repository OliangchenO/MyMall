package com.lc.manager.controller;

import com.lc.common.pojo.DataTablesResult;
import com.lc.common.pojo.Result;
import com.lc.common.utils.ResultUtil;
import com.lc.manager.pojo.TbImage;
import com.lc.manager.service.ContentImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(description = "图片列表信息")
public class ContentImageController {
    private final static Logger log= LoggerFactory.getLogger(ContentImageController.class);

    @Autowired
    private ContentImageService contentImageService;

    @RequestMapping(value = "/image/list",method = RequestMethod.GET)
    @ApiOperation(value = "通过获得图片列表")
    public DataTablesResult getContentImage(){

        DataTablesResult result=contentImageService.getContentImage();
        return result;
    }

    @RequestMapping(value = "/image/update",method = RequestMethod.POST)
    @ApiOperation(value = "编辑图片")
    public Result<Object> updateContentImage(@ModelAttribute TbImage tbImage){

        contentImageService.updateContentImage(tbImage);
        return new ResultUtil<Object>().setData(null);
    }
}
