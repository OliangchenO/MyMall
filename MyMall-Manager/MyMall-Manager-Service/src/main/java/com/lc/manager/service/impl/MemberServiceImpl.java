package com.lc.manager.service.impl;

import com.lc.common.exception.MyMallException;
import com.lc.common.pojo.DataTablesResult;
import com.lc.manager.mapper.TbMemberMapper;
import com.lc.manager.pojo.TbMemberExample;
import com.lc.manager.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private TbMemberMapper tbMemberMapper;

    @Override
    public DataTablesResult getMemberCount() {
        DataTablesResult result=new DataTablesResult();
        TbMemberExample example=new TbMemberExample();
        TbMemberExample.Criteria criteria=example.createCriteria();
        criteria.andStateNotEqualTo(2);
        try{
            result.setRecordsTotal((int)tbMemberMapper.countByExample(example));
        }catch (Exception e){
            throw new MyMallException("统计会员数失败");
        }
        return result;
    }
}
