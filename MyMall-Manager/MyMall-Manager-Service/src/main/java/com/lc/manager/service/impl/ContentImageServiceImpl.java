package com.lc.manager.service.impl;

import com.lc.common.exception.MyMallException;
import com.lc.common.jedis.JedisClient;
import com.lc.common.pojo.DataTablesResult;
import com.lc.manager.dto.DtoUtil;
import com.lc.manager.dto.ImageDto;
import com.lc.manager.mapper.TbContentCategoryMapper;
import com.lc.manager.mapper.TbImageMapper;
import com.lc.manager.pojo.TbContentCategory;
import com.lc.manager.pojo.TbImage;
import com.lc.manager.pojo.TbImageExample;
import com.lc.manager.service.ContentImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContentImageServiceImpl implements ContentImageService {
    private static final Logger log = LoggerFactory.getLogger(ContentImageServiceImpl.class);

    @Autowired
    private TbImageMapper tbImageMapper;
    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;
    @Autowired
    private JedisClient jedisClient;

    @Value("${PRODUCT_HOME}")
    private String PRODUCT_HOME;

    @Override
    public DataTablesResult getContentImage() {
        DataTablesResult result=new DataTablesResult();
        List<ImageDto> list=new ArrayList<>();

        TbImageExample example=new TbImageExample();
        List<TbImage> listImage=tbImageMapper.selectByExample(example);

        for(int i=0;i<listImage.size();i++){
            ImageDto imageDto= DtoUtil.TbImage2ImageDto(listImage.get(i));
            TbContentCategory tbContentCategory=tbContentCategoryMapper.selectByPrimaryKey(Long.valueOf(listImage.get(i).getCategoryId()));
            imageDto.setCategory(tbContentCategory.getName());
            list.add(imageDto);
        }

        result.setData(list);
        return result;
    }

    @Override
    public int updateContentImage(TbImage tbImage) {

        TbImage old=getContentImageById(Long.valueOf(tbImage.getId()));

        if(tbImage.getImage().isEmpty()){
            tbImage.setImage(old.getImage());
        }
        tbImage.setUpdated(new Date());
        tbImage.setImageMobile(old.getImageMobile());
        tbImage.setCreated(old.getCreated());

        if(tbImageMapper.updateByPrimaryKey(tbImage)!=1){
            throw new MyMallException("更新图片失败");
        }
        //同步缓存
        deleteHomeRedis();
        return 1;
    }

    @Override
    public TbImage getContentImageById(Long id) {
        TbImage tbImage=tbImageMapper.selectByPrimaryKey(Math.toIntExact(id));
        if(tbImage==null){
            throw new MyMallException("通过id获取图片失败");
        }
        return tbImage;
    }

    //同步首页缓存
    public void deleteHomeRedis(){
        try {
            jedisClient.hdel(PRODUCT_HOME,PRODUCT_HOME);
        }catch (Exception e){
            log.error(e.toString(), e);
        }
    }
}
