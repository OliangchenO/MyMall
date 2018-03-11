package com.lc.manager.service;

import com.lc.common.pojo.DataTablesResult;
import com.lc.manager.pojo.TbContent;

public interface ContentService {
    int addContent(TbContent tbContent);

    int deleteContent(Long id);

    int updateContent(TbContent tbContent);

    TbContent getContentById(Long id);

    DataTablesResult getContentListByCid(Long cid);


}
