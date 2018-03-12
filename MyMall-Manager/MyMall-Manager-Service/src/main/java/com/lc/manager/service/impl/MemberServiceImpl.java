package com.lc.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lc.common.exception.MyMallException;
import com.lc.common.pojo.DataTablesResult;
import com.lc.manager.dto.DtoUtil;
import com.lc.manager.dto.MemberDto;
import com.lc.manager.mapper.TbMemberMapper;
import com.lc.manager.pojo.TbMember;
import com.lc.manager.pojo.TbMemberExample;
import com.lc.manager.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private TbMemberMapper tbMemberMapper;

    @Override
    public TbMember addMember(MemberDto memberDto) {
        TbMember tbMember = DtoUtil.MemberDto2Member(memberDto);

        if (getMemberByUsername(tbMember.getUsername()) != null) {
            throw new MyMallException("用户名已被注册");
        }
        if (getMemberByPhone(tbMember.getPhone()) != null) {
            throw new MyMallException("手机号已被注册");
        }
        if (getMemberByEmail(tbMember.getEmail()) != null) {
            throw new MyMallException("邮箱已被注册");
        }

        tbMember.setState(1);
        tbMember.setCreated(new Date());
        tbMember.setUpdated(new Date());
        String md5Pass = DigestUtils.md5DigestAsHex(tbMember.getPassword().getBytes());
        tbMember.setPassword(md5Pass);

        if (tbMemberMapper.insert(tbMember) != 1) {
            throw new MyMallException("添加用户失败");
        }
        return getMemberByPhone(tbMember.getPhone());
    }

    @Override
    public int deleteMember(Long id) {
        if (tbMemberMapper.deleteByPrimaryKey(id) != 1) {
            throw new MyMallException("删除会员失败");
        }
        return 1;
    }

    @Override
    public TbMember updateMember(Long id, MemberDto memberDto) {
        TbMember tbMember = DtoUtil.MemberDto2Member(memberDto);
        tbMember.setId(id);
        tbMember.setUpdated(new Date());
        TbMember oldMember = getMemberById(id);
        tbMember.setState(oldMember.getState());
        tbMember.setCreated(oldMember.getCreated());
        if (tbMember.getPassword() == null || tbMember.getPassword() == "") {
            tbMember.setPassword(oldMember.getPassword());
        } else {
            String md5Pass = DigestUtils.md5DigestAsHex(tbMember.getPassword().getBytes());
            tbMember.setPassword(md5Pass);
        }

        if (tbMemberMapper.updateByPrimaryKey(tbMember) != 1) {
            throw new MyMallException("更新会员信息失败");
        }
        return getMemberById(id);
    }

    @Override
    public TbMember changePassMember(Long id, MemberDto memberDto) {
        TbMember tbMember = getMemberById(id);
        if (tbMember.getPassword() == null || tbMember.getPassword() == "") {
            tbMember.setPassword(tbMember.getPassword());
        } else {
            String md5Pass = DigestUtils.md5DigestAsHex(tbMember.getPassword().getBytes());
            tbMember.setPassword(md5Pass);
        }

        if (tbMemberMapper.updateByPrimaryKey(tbMember) != 1) {
            throw new MyMallException("修改会员密码失败");
        }
        return getMemberById(id);
    }

    @Override
    public TbMember alterMemberState(Long id, Integer state) {
        TbMember tbMember = getMemberById(id);
        tbMember.setState(state);
        tbMember.setUpdated(new Date());

        if (tbMemberMapper.updateByPrimaryKey(tbMember) != 1){
            throw new MyMallException("修改会员状态失败");
        }
        return getMemberById(id);
    }

    @Override
    public TbMember getMemberById(long memberId) {
        TbMember tbMember;
        try {
            tbMember=tbMemberMapper.selectByPrimaryKey(memberId);
        }catch (Exception e){
            throw new MyMallException("ID获取会员信息失败");
        }
        tbMember.setPassword("");
        return tbMember;
    }

    @Override
    public DataTablesResult getMemberCount() {
        DataTablesResult result = new DataTablesResult();
        TbMemberExample example = new TbMemberExample();
        TbMemberExample.Criteria criteria = example.createCriteria();
        criteria.andStateNotEqualTo(2);
        try {
            result.setRecordsTotal((int) tbMemberMapper.countByExample(example));
        } catch (Exception e) {
            throw new MyMallException("统计会员数失败");
        }
        return result;
    }

    @Override
    public DataTablesResult getRemoveMemberCount() {
        DataTablesResult result=new DataTablesResult();
        TbMemberExample example=new TbMemberExample();
        TbMemberExample.Criteria criteria=example.createCriteria();
        criteria.andStateEqualTo(2);
        try{
            result.setRecordsTotal((int)tbMemberMapper.countByExample(example));
        }catch (Exception e){
            throw new MyMallException("统计移除会员数失败");
        }

        return result;
    }

    @Override
    public TbMember getMemberByEditEmail(Long id, String email) {
        TbMember tbMember=getMemberById(id);
        TbMember newTbMember=null;
        if(tbMember.getEmail()==null||!tbMember.getEmail().equals(email)){
            newTbMember=getMemberByEmail(email);
            if(newTbMember != null){
                newTbMember.setPassword("");
            }
        }
        return newTbMember;
    }

    @Override
    public TbMember getMemberByEditPhone(Long id, String phone) {
        TbMember tbMember=getMemberById(id);
        TbMember newTbMember=null;
        if(tbMember.getPhone()==null||!tbMember.getPhone().equals(phone)){
            newTbMember=getMemberByPhone(phone);
            if(newTbMember != null){
                newTbMember.setPassword("");
            }
        }
        return newTbMember;
    }

    @Override
    public TbMember getMemberByEditUsername(Long id, String username) {
        TbMember tbMember=getMemberById(id);
        TbMember newTbMember=null;
        if(tbMember.getUsername()==null||!tbMember.getUsername().equals(username)){
            newTbMember=getMemberByUsername(username);
            if(newTbMember != null){
                newTbMember.setPassword("");
            }
        }
        return newTbMember;
    }

    @Override
    public TbMember getMemberByEmail(String email) {
        List<TbMember> list;
        TbMemberExample example=new TbMemberExample();
        TbMemberExample.Criteria criteria=example.createCriteria();
        criteria.andEmailEqualTo(email);
        try {
            list=tbMemberMapper.selectByExample(example);
        }catch (Exception e){
            throw new MyMallException("Email获取会员信息失败");
        }
        if(!list.isEmpty()){
            list.get(0).setPassword("");
            return list.get(0);
        }
        return null;
    }

    @Override
    public TbMember getMemberByPhone(String phone) {
        List<TbMember> list;
        TbMemberExample example=new TbMemberExample();
        TbMemberExample.Criteria criteria=example.createCriteria();
        criteria.andPhoneEqualTo(phone);
        try {
            list=tbMemberMapper.selectByExample(example);
        }catch (Exception e){
            throw new MyMallException("Phone获取会员信息失败");
        }
        if(!list.isEmpty()){
            list.get(0).setPassword("");
            return list.get(0);
        }
        return null;
    }

    @Override
    public TbMember getMemberByUsername(String username) {
        List<TbMember> list;
        TbMemberExample example=new TbMemberExample();
        TbMemberExample.Criteria criteria=example.createCriteria();
        criteria.andUsernameEqualTo(username);
        try {
            list=tbMemberMapper.selectByExample(example);
        }catch (Exception e){
            throw new MyMallException("ID获取会员信息失败");
        }
        if(!list.isEmpty()){
            list.get(0).setPassword("");
            return list.get(0);
        }
        return null;
    }

    @Override
    public DataTablesResult getMemberList(int draw, int start, int length, String search, String minDate, String maxDate, String orderCol, String orderDir) {
        DataTablesResult result = new DataTablesResult();

        try {
            //分页
            PageHelper.startPage(start / length + 1, length);
            List<TbMember> list = tbMemberMapper.selectByMemberInfo("%" + search + "%", minDate, maxDate, orderCol, orderDir);
            PageInfo<TbMember> pageInfo = new PageInfo<>(list);

            for (TbMember tbMember : list) {
                tbMember.setPassword("");
            }

            result.setRecordsFiltered((int) pageInfo.getTotal());
            result.setRecordsTotal(getMemberCount().getRecordsTotal());

            result.setDraw(draw);
            result.setData(list);
        } catch (Exception e) {
            throw new MyMallException("加载用户列表失败");
        }

        return result;
    }

    @Override
    public DataTablesResult getRemoveMemberList(int draw, int start, int length, String search, String minDate, String maxDate, String orderCol, String orderDir) {
        DataTablesResult result=new DataTablesResult();

        try{
            //分页执行查询返回结果
            PageHelper.startPage(start/length+1,length);
            List<TbMember> list = tbMemberMapper.selectByRemoveMemberInfo("%"+search+"%",minDate,maxDate,orderCol,orderDir);
            PageInfo<TbMember> pageInfo=new PageInfo<>(list);

            for(TbMember tbMember:list){
                tbMember.setPassword("");
            }

            result.setRecordsFiltered((int)pageInfo.getTotal());
            result.setRecordsTotal(getRemoveMemberCount().getRecordsTotal());

            result.setDraw(draw);
            result.setData(list);
        }catch (Exception e){
            throw new MyMallException("加载删除用户列表失败");
        }

        return result;
    }


}
