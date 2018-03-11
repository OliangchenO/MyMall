package com.lc.manager.service;

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
     * 获取网站基础设置
     * @return
     */
    TbBase getBase();

    /**
     * 获取本周热销商品
     * @return
     */
    TbOrderItem getWeekHot();
}
