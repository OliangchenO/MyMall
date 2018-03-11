package com.lc.manager.service.impl;

import com.lc.common.MyMallException;
import com.lc.manager.mapper.TbOrderMapper;
import com.lc.manager.pojo.TbOrderExample;
import com.lc.manager.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private TbOrderMapper tbOrderMapper;

    @Override
    public Long countOrder() {
        TbOrderExample example = new TbOrderExample();
        Long result = tbOrderMapper.countByExample(example);
        if (result == null) {
            throw new MyMallException("统计订单数目失败");
        }
        return result;
    }
}
