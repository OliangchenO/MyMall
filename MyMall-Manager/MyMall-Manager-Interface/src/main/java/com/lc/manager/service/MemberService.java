package com.lc.manager.service;

import com.lc.common.pojo.DataTablesResult;
import com.lc.manager.dto.MemberDto;
import com.lc.manager.pojo.TbMember;

public interface MemberService {
    /**
     * 添加会员
     * @param memberDto
     * @return
     */
    TbMember addMember(MemberDto memberDto);

    /**
     * 彻底删除会员
     * @param id
     * @return
     */
    int deleteMember(Long id);

    /**
     * 更新会员信息
     * @param id
     * @param memberDto
     * @return
     */
    TbMember updateMember(Long id,MemberDto memberDto);

    /**
     * 修改会员密码
     * @param id
     * @param memberDto
     * @return
     */
    TbMember changePassMember(Long id,MemberDto memberDto);

    /**
     * 修改会员状态
     * @param id
     * @return
     */
    TbMember alterMemberState(Long id,Integer state);

    /**
     * 根据ID获取会员信息
     * @param memberId
     * @return
     */
    TbMember getMemberById(long memberId);

    /**
     * 获得所有会员总数
     * @return
     */
    DataTablesResult getMemberCount();

    DataTablesResult getRemoveMemberCount();

    //验证编辑邮箱是否存在
    TbMember getMemberByEditEmail(Long id,String email);

    //验证编辑手机是否存在
    TbMember getMemberByEditPhone(Long id,String phone);

    //验证编辑用户名是否存在
    TbMember getMemberByEditUsername(Long id,String username);

    TbMember getMemberByEmail(String email);

    TbMember getMemberByPhone(String phone);

    TbMember getMemberByUsername(String username);

    /**
     * 分页获得会员列表
     * @param draw
     * @param start
     * @param length
     * @param search
     * @return
     */
    DataTablesResult getMemberList(int draw, int start, int length, String search,
                                   String minDate, String maxDate, String orderCol, String orderDir);

    //分页获得移除会员列表
    DataTablesResult getRemoveMemberList(int draw, int start, int length, String search,
                                         String minDate, String maxDate, String orderCol, String orderDir);
}
