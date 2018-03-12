package com.lc.manager.service;

import com.lc.common.pojo.DataTablesResult;
import com.lc.manager.pojo.TbImage;

public interface ContentImageService {
    DataTablesResult getContentImage();

    int updateContentImage(TbImage tbImage);

    TbImage getContentImageById(Long id);
}
