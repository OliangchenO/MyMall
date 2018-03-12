package com.lc.manager.service;

import com.lc.common.pojo.ZTreeNode;
import com.lc.manager.pojo.TbItemCat;

import java.util.List;

public interface ItemCatService {
    int addItemCat(TbItemCat tbItemCat);

    void deleteZTree(Long id);

    void deleteItemCat(Long id);

    int updateItemCat(TbItemCat tbItemCat);

    TbItemCat getItemCatById(Long id);

    List<ZTreeNode> getItemCatList(int parentId);
}
