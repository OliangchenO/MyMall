package com.lc.manager.service;

import com.lc.common.pojo.DataTablesResult;
import com.lc.manager.pojo.TbPermission;
import com.lc.manager.pojo.TbRole;
import com.lc.manager.pojo.TbUser;

import java.util.List;
import java.util.Set;

public interface UserService {
    //***************用户******************
    int addUser(TbUser user);

    int deleteUser(Long userId);

    int updateUser(TbUser user);

    int changeUserState(Long id,int state);

    int changePassword(TbUser tbUser);

    /**
     * 通过用户名获取用户
     * @param username
     * @return
     */
    TbUser getUserByUsername(String username);

    DataTablesResult getUserList();

    TbUser getUserById(Long id);

    boolean getUserByName(String username);

    boolean getUserByPhone(String phone);

    boolean getUserByEmail(String emaill);

    boolean getUserByEditName(Long id,String username);

    boolean getUserByEditPhone(Long id,String phone);

    boolean getUserByEditEmail(Long id,String emaill);

    Long countUser();

    //*******************角色******************
    int addRole(TbRole tbRole);

    int deleteRole(int id);

    int updateRole(TbRole tbRole);

    Set<String> getRoles(String userName);

    DataTablesResult getRoleList();

    List<TbRole> getAllRoles();

    /**
     * 通过角色名获取角色
     * @param roleName
     * @return
     */
    TbRole getRoleByRoleName(String roleName);

    /**
     * 判断角色编辑名是否已存在
     * @param roleName
     * @return
     */
    boolean getRoleByEditName(int id,String roleName);

    Long countRole();

    //****************权限**********************
    int addPermission(TbPermission tbPermission);

    int deletePermission(int id);

    int updatePermission(TbPermission tbPermission);

    Set<String> getPermissions(String userName);

    /**
     * 获得所有权限列表
     * @return
     */
    DataTablesResult getPermissionList();

    Long countPermission();
}
