package com.lc.manager.service.impl;

import com.lc.common.exception.MyMallException;
import com.lc.common.jedis.JedisClient;
import com.lc.common.pojo.DataTablesResult;
import com.lc.manager.dto.ContentDto;
import com.lc.manager.dto.DtoUtil;
import com.lc.manager.mapper.TbContentCategoryMapper;
import com.lc.manager.mapper.TbContentMapper;
import com.lc.manager.pojo.TbContent;
import com.lc.manager.pojo.TbContentCategory;
import com.lc.manager.pojo.TbContentExample;
import com.lc.manager.service.ContentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {
    private final static Logger log= LoggerFactory.getLogger(ContentCatServiceImpl.class);
    @Autowired
    private TbContentMapper tbContentMapper;
    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;
    @Autowired
    private JedisClient jedisClient;

    @Value("${PRODUCT_HOME}")
    private String PRODUCT_HOME;

    @Override
    public int addContent(TbContent tbContent) {
        tbContent.setCreated(new Date());
        tbContent.setUpdated(new Date());
        if(tbContentMapper.insert(tbContent)!=1){
            throw new MyMallException("添加内容失败");
        }
        //同步缓存
        deleteHomeRedis();
        return 1;
    }

    @Override
    public int deleteContent(Long id) {
        if(tbContentMapper.deleteByPrimaryKey(id)!=1){
            throw new MyMallException("删除内容失败");
        }
        //同步缓存
        deleteHomeRedis();
        return 1;
    }

    @Override
    public int updateContent(TbContent tbContent) {
        TbContent old=getContentById(tbContent.getId());
        if(tbContent.getImage().isEmpty()){
            tbContent.setImage(old.getImage());
        }
        tbContent.setCreated(old.getCreated());
        tbContent.setUpdated(new Date());
        if(tbContentMapper.updateByPrimaryKey(tbContent)!=1){
            throw new MyMallException("更新内容失败");
        }
        //同步缓存
        deleteHomeRedis();
        return 1;
    }

    @Override
    public TbContent getContentById(Long id) {
        TbContent tbContent=tbContentMapper.selectByPrimaryKey(id);
        if(tbContent==null){
            throw new MyMallException("通过id获取内容失败");
        }
        return tbContent;
    }

    @Override
    public DataTablesResult getContentListByCid(Long cid) {
        DataTablesResult result=new DataTablesResult();
        List<ContentDto> list=new ArrayList<>();

        TbContentExample example=new TbContentExample();
        TbContentExample.Criteria criteria=example.createCriteria();
        //条件查询
        criteria.andCategoryIdEqualTo(cid);
        List<TbContent> listTbContent=tbContentMapper.selectByExample(example);

        TbContentCategory tbContentCategory=tbContentCategoryMapper.selectByPrimaryKey(cid);

        for(int i=0;i<listTbContent.size();i++){
            ContentDto contentDto= DtoUtil.TbContent2ContentDto(listTbContent.get(i));
            contentDto.setCategory(tbContentCategory.getName());
            list.add(contentDto);
        }

        result.setData(list);
        return result;
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
