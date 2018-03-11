package com.lc.manager.service;

import com.lc.common.pojo.ZTreeNode;

import java.util.List;

public interface ContentCatService {
    List<ZTreeNode> getContentCatList(Long parentId);
}
