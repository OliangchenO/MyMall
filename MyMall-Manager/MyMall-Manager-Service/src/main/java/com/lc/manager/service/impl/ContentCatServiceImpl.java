package com.lc.manager.service.impl;

import com.lc.common.pojo.ZTreeNode;
import com.lc.manager.dto.DtoUtil;
import com.lc.manager.mapper.TbContentCategoryMapper;
import com.lc.manager.pojo.TbContentCategory;
import com.lc.manager.pojo.TbContentCategoryExample;
import com.lc.manager.service.ContentCatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ContentCatServiceImpl implements ContentCatService {
    private final static Logger log= LoggerFactory.getLogger(ContentCatServiceImpl.class);

    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;

    @Override
    public List<ZTreeNode> getContentCatList(Long parentId) {
        TbContentCategoryExample example=new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria=example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        example.setOrderByClause("sort_order");
        List<TbContentCategory> catList=tbContentCategoryMapper.selectByExample(example);

        List<ZTreeNode> list=new ArrayList<>();

        for(int i=0;i<catList.size();i++){
            ZTreeNode zTreeNode= DtoUtil.TbContentCategory2ZTreeNode(catList.get(i));
            list.add(zTreeNode);
        }

        return list;
    }
}
