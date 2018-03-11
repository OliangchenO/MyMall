package com.lc.manager.dto;

import com.lc.common.pojo.ZTreeNode;
import com.lc.manager.pojo.TbContentCategory;

public class DtoUtil {
    public static ZTreeNode TbContentCategory2ZTreeNode(TbContentCategory tbContentCategory) {
        ZTreeNode zTreeNode =new ZTreeNode();

        zTreeNode.setId(Math.toIntExact(tbContentCategory.getId()));
        zTreeNode.setIsParent(tbContentCategory.getIsParent());
        zTreeNode.setpId(Math.toIntExact(tbContentCategory.getParentId()));
        zTreeNode.setName(tbContentCategory.getName());
        zTreeNode.setIcon(tbContentCategory.getIcon());
        zTreeNode.setSortOrder(tbContentCategory.getSortOrder());
        zTreeNode.setStatus(tbContentCategory.getStatus());
        zTreeNode.setRemark(tbContentCategory.getRemark());
        zTreeNode.setNum(tbContentCategory.getNum());

        return zTreeNode;
    }
}
