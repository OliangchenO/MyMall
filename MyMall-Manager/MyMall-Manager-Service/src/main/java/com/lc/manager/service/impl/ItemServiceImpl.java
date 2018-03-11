package com.lc.manager.service.impl;

import com.lc.common.pojo.DataTablesResult;
import com.lc.manager.mapper.TbItemMapper;
import com.lc.manager.pojo.TbItemExample;
import com.lc.manager.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {
    private final static Logger log= LoggerFactory.getLogger(ItemServiceImpl.class);
    @Autowired
    private TbItemMapper tbItemMapper;
    @Override
    public DataTablesResult getAllItemCount() {
        TbItemExample example=new TbItemExample();
        Long count=tbItemMapper.countByExample(example);
        DataTablesResult result=new DataTablesResult();
        result.setRecordsTotal(Math.toIntExact(count));
        return result;
    }
}
