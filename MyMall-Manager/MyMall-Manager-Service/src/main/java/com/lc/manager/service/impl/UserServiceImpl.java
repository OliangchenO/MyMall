package com.lc.manager.service.impl;

import com.lc.manager.service.UserService;
import com.lc.common.exception.MyMallException;
import com.lc.manager.mapper.TbPermissionMapper;
import com.lc.manager.mapper.TbRoleMapper;
import com.lc.manager.mapper.TbRolePermMapper;
import com.lc.manager.mapper.TbUserMapper;
import com.lc.manager.pojo.TbUser;
import com.lc.manager.pojo.TbUserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private TbUserMapper tbUserMapper;
    @Autowired
    private TbRoleMapper tbRoleMapper;
    @Autowired
    private TbPermissionMapper tbPermissionMapper;
    @Autowired
    private TbRolePermMapper tbRolePermMapper;
    @Override
    public Set<String> getRoles(String username) {
        return tbUserMapper.getRoles(username);
    }

    @Override
    public Set<String> getPermissions(String username) {
        return tbUserMapper.getPermissions(username);
    }

    @Override
    public TbUser getUserByUsername(String username) {
        List<TbUser> list = null;
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        criteria.andStateEqualTo(1);
        try {
            list = tbUserMapper.selectByExample(example);
        }catch (Exception e){
            throw new MyMallException("通过ID获取用户信息失败");
        }
        if(!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }
}
