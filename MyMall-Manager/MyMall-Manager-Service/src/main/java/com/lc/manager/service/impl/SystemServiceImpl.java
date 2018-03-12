package com.lc.manager.service.impl;

import com.lc.common.pojo.DataTablesResult;
import com.lc.manager.mapper.TbBaseMapper;
import com.lc.manager.mapper.TbLogMapper;
import com.lc.manager.mapper.TbOrderItemMapper;
import com.lc.manager.pojo.*;
import com.lc.manager.service.SystemService;
import com.lc.common.exception.MyMallException;
import com.lc.manager.mapper.TbShiroFilterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SystemServiceImpl implements SystemService {
    @Autowired
    private TbShiroFilterMapper tbShiroFilterMapper;
    @Autowired
    private TbLogMapper tbLogMapper;
    @Autowired
    private TbBaseMapper tbBaseMapper;
    @Autowired
    private TbOrderItemMapper tbOrderItemMapper;

    @Value("${BASE_ID}")
    private String BASE_ID;

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

    @Override
    public DataTablesResult getLogList() {

        DataTablesResult result=new DataTablesResult();
        TbLogExample example=new TbLogExample();
        List<TbLog> list=tbLogMapper.selectByExample(example);
        if(list==null){
            throw new MyMallException("获取日志列表失败");
        }
        result.setSuccess(true);
        result.setData(list);
        return result;
    }

    @Override
    public Long countLog() {

        TbLogExample example=new TbLogExample();
        Long result=tbLogMapper.countByExample(example);
        if(result==null){
            throw new MyMallException("获取日志数量失败");
        }
        return result;
    }

    @Override
    public int deleteLog(int id) {

        if(tbLogMapper.deleteByPrimaryKey(id)!=1){
            throw new MyMallException("删除日志失败");
        }
        return 1;
    }

    @Override
    public TbBase getBase() {
        TbBase tbBase=tbBaseMapper.selectByPrimaryKey(Integer.valueOf(BASE_ID));
        if(tbBase==null){
            throw new MyMallException("获取基础设置失败");
        }
        return tbBase;
    }

    @Override
    public int updateBase(TbBase tbBase) {

        if(tbBaseMapper.updateByPrimaryKey(tbBase)!=1){
            throw new MyMallException("更新基础设置失败");
        }
        return 1;
    }

    @Override
    public TbOrderItem getWeekHot() {
        List<TbOrderItem> list=tbOrderItemMapper.getWeekHot();
        if(list==null){
            throw new MyMallException("获取热销商品数据失败");
        }
        if(list.size()==0){
            TbOrderItem tbOrderItem=new TbOrderItem();
            tbOrderItem.setTotal(0);
            tbOrderItem.setTitle("暂无数据");
            tbOrderItem.setPicPath("");
            return tbOrderItem;
        }
        return list.get(0);
    }

    @Override
    public Long countShiroFilter() {
        TbShiroFilterExample example=new TbShiroFilterExample();
        Long result=tbShiroFilterMapper.countByExample(example);
        if(result==null){
            throw new MyMallException("获取shiro过滤链数目失败");
        }
        return result;
    }

    @Override
    public int addShiroFilter(TbShiroFilter tbShiroFilter) {

        if(tbShiroFilterMapper.insert(tbShiroFilter)!=1){
            throw new MyMallException("添加shiro过滤链失败");
        }
        return 1;
    }

    @Override
    public int updateShiroFilter(TbShiroFilter tbShiroFilter) {

        if(tbShiroFilterMapper.updateByPrimaryKey(tbShiroFilter)!=1){
            throw new MyMallException("更新shiro过滤链失败");
        }
        return 1;
    }

    @Override
    public int deleteShiroFilter(int id) {
        if(tbShiroFilterMapper.deleteByPrimaryKey(id)!=1){
            throw new MyMallException("删除shiro过滤链失败");
        }
        return 1;
    }
}
