package com.lc.manager.service;

import com.lc.common.pojo.DataTablesResult;
import com.lc.manager.pojo.TbBase;
import com.lc.manager.pojo.TbLog;
import com.lc.manager.pojo.TbOrderItem;
import com.lc.manager.pojo.TbShiroFilter;

import java.util.List;

public interface SystemService {

    List<TbShiroFilter> getShiroFilter();

    /**
     * 添加日志
     * @param tbLog
     * @return
     */
    int addLog(TbLog tbLog);

    /**
     * 获取日志列表
     * @return
     */
    DataTablesResult getLogList();

    /**
     * 统计日志数量
     * @return
     */
    Long countLog();

    /**
     * 删除日志
     * @param id
     * @return
     */
    int deleteLog(int id);

    /**
     * 获取网站基础设置
     * @return
     */
    TbBase getBase();

    /**
     * 更新网站基础设置
     * @param tbBase
     * @return
     */
    int updateBase(TbBase tbBase);

    /**
     * 获取本周热销商品
     * @return
     */
    TbOrderItem getWeekHot();

    /**
     * 统计过滤链数目
     * @return
     */
    Long countShiroFilter();

    /**
     * 添加shiro过滤链
     * @param tbShiroFilter
     * @return
     */
    int addShiroFilter(TbShiroFilter tbShiroFilter);

    /**
     * 更新shiro过滤链
     * @param tbShiroFilter
     * @return
     */
    int updateShiroFilter(TbShiroFilter tbShiroFilter);

    /**
     * 删除shiro过滤链
     * @param id
     * @return
     */
    int deleteShiroFilter(int id);
}
