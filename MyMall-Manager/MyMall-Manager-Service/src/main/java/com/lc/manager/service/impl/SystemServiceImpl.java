package com.lc.manager.service.impl;

import com.lc.manager.mapper.TbLogMapper;
import com.lc.manager.pojo.TbLog;
import com.lc.manager.service.SystemService;
import com.lc.common.MyMallException;
import com.lc.manager.mapper.TbShiroFilterMapper;
import com.lc.manager.pojo.TbShiroFilter;
import com.lc.manager.pojo.TbShiroFilterExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SystemServiceImpl implements SystemService {
    @Autowired
    private TbShiroFilterMapper tbShiroFilterMapper;
    @Autowired
    private TbLogMapper tbLogMapper;

    @Override
    public List<TbShiroFilter> getShiroFilter() {
        TbShiroFilterExample example=new TbShiroFilterExample();
        example.setOrderByClause("sort_order");
        List<TbShiroFilter> list= null;
        list=tbShiroFilterMapper.selectByExample(example);
        if(list==null){
            throw new MyMallException("获取shiro过滤链失败");
        }
        return list;
    }

    @Override
    public int addLog(TbLog tbLog) {
        if(tbLogMapper.insert(tbLog)!=1){
            throw new MyMallException("保存日志失败");
        }
        return 1;
    }
}
