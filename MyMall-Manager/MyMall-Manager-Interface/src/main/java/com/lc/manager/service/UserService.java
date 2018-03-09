package com.lc.manager.service;

import com.lc.manager.pojo.TbUser;

import java.util.Set;

public interface UserService {

    Set<String> getRoles(String username);

    Set<String> getPermissions(String username);

    TbUser getUserByUsername(String username);
}
