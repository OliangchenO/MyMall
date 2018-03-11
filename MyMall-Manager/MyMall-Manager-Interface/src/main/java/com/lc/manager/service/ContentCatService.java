package com.lc.manager.service;

import com.lc.common.pojo.ZTreeNode;
import com.lc.manager.dto.ContentCatDto;
import com.lc.manager.pojo.TbContentCategory;

import java.util.List;

public interface ContentCatService {
    TbContentCategory getContentCatById(Long id);

    List<ZTreeNode> getContentCatList(Long parentId);

    int addContentCat(ContentCatDto contentCatDto);

    int deleteContentCat(Long id);

    int updateContentCat(ContentCatDto contentCatDto);

}
