package com.lc.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lc.common.exception.MyMallException;
import com.lc.common.jedis.JedisClient;
import com.lc.common.pojo.DataTablesResult;
import com.lc.common.utils.IDUtil;
import com.lc.manager.dto.DtoUtil;
import com.lc.manager.dto.ItemDto;
import com.lc.manager.mapper.TbItemCatMapper;
import com.lc.manager.mapper.TbItemDescMapper;
import com.lc.manager.mapper.TbItemMapper;
import com.lc.manager.pojo.TbItem;
import com.lc.manager.pojo.TbItemCat;
import com.lc.manager.pojo.TbItemDesc;
import com.lc.manager.pojo.TbItemExample;
import com.lc.manager.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    private final static Logger log= LoggerFactory.getLogger(ItemServiceImpl.class);
    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private TbItemDescMapper tbItemDescMapper;
    @Autowired
    private TbItemCatMapper tbItemCatMapper;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Resource
    private Destination topicDestination;
    @Autowired
    private JedisClient jedisClient;

    @Value("${RDEIS_ITEM}")
    private String RDEIS_ITEM;

    @Override
    public DataTablesResult getAllItemCount() {
        TbItemExample example=new TbItemExample();
        Long count=tbItemMapper.countByExample(example);
        DataTablesResult result=new DataTablesResult();
        result.setRecordsTotal(Math.toIntExact(count));
        return result;
    }

    @Override
    public ItemDto getItemById(Long id) {
        ItemDto itemDto=new ItemDto();

        TbItem tbItem=tbItemMapper.selectByPrimaryKey(id);
        itemDto=DtoUtil.TbItem2ItemDto(tbItem);

        TbItemCat tbItemCat=tbItemCatMapper.selectByPrimaryKey(itemDto.getCid());
        itemDto.setCname(tbItemCat.getName());

        TbItemDesc tbItemDesc=tbItemDescMapper.selectByPrimaryKey(id);
        itemDto.setDetail(tbItemDesc.getItemDesc());

        return itemDto;
    }

    @Override
    public TbItem getNormalItemById(Long id) {
        return tbItemMapper.selectByPrimaryKey(id);
    }

    @Override
    public DataTablesResult getItemList(int draw, int start, int length, int cid, String search, String orderCol, String orderDir) {
        DataTablesResult result=new DataTablesResult();

        //分页执行查询返回结果
        PageHelper.startPage(start/length+1,length);
        List<TbItem> list = tbItemMapper.selectItemByCondition(cid,"%"+search+"%",orderCol,orderDir);
        PageInfo<TbItem> pageInfo=new PageInfo<>(list);
        result.setRecordsFiltered((int)pageInfo.getTotal());
        result.setRecordsTotal(getAllItemCount().getRecordsTotal());
        result.setDraw(draw);
        result.setData(list);

        return result;
    }

    @Override
    public DataTablesResult getItemSearchList(int draw, int start, int length, int cid, String search, String minDate, String maxDate, String orderCol, String orderDir) {
        DataTablesResult result=new DataTablesResult();

        //分页执行查询返回结果
        PageHelper.startPage(start/length+1,length);
        List<TbItem> list = tbItemMapper.selectItemByMultiCondition(cid,"%"+search+"%",minDate,maxDate,orderCol,orderDir);
        PageInfo<TbItem> pageInfo=new PageInfo<>(list);
        result.setRecordsFiltered((int)pageInfo.getTotal());
        result.setRecordsTotal(getAllItemCount().getRecordsTotal());

        result.setDraw(draw);
        result.setData(list);

        return result;
    }

    @Override
    public TbItem addItem(ItemDto itemDto) {
        long id= IDUtil.getRandomId();
        TbItem tbItem= DtoUtil.ItemDto2TbItem(itemDto);
        tbItem.setId(id);
        tbItem.setStatus((byte) 1);
        tbItem.setCreated(new Date());
        tbItem.setUpdated(new Date());
        if(tbItem.getImage().isEmpty()){
            // TODO: 2018/3/11 修改图片地址
            tbItem.setImage("http://ow2h3ee9w.bkt.clouddn.com/nopic.jpg");
        }
        if(tbItemMapper.insert(tbItem)!=1){
            throw new MyMallException("添加商品失败");
        }

        TbItemDesc tbItemDesc=new TbItemDesc();
        tbItemDesc.setItemId(id);
        tbItemDesc.setItemDesc(itemDto.getDetail());
        tbItemDesc.setCreated(new Date());
        tbItemDesc.setUpdated(new Date());

        if(tbItemDescMapper.insert(tbItemDesc)!=1){
            throw new MyMallException("添加商品详情失败");
        }
        // TODO: 2018/3/11 activeMq创建消费者
        //发送消息同步索引库
        sendRefreshESMessage("add",id);
        return getNormalItemById(id);
    }

    @Override
    public TbItem updateItem(Long id, ItemDto itemDto) {
        TbItem oldTbItem=getNormalItemById(id);

        TbItem tbItem= DtoUtil.ItemDto2TbItem(itemDto);

        if(tbItem.getImage().isEmpty()){
            tbItem.setImage(oldTbItem.getImage());
        }
        tbItem.setId(id);
        tbItem.setStatus(oldTbItem.getStatus());
        tbItem.setCreated(oldTbItem.getCreated());
        tbItem.setUpdated(new Date());
        if(tbItemMapper.updateByPrimaryKey(tbItem)!=1){
            throw new MyMallException("更新商品失败");
        }

        TbItemDesc tbItemDesc=new TbItemDesc();

        tbItemDesc.setItemId(id);
        tbItemDesc.setItemDesc(itemDto.getDetail());
        tbItemDesc.setUpdated(new Date());
        tbItemDesc.setCreated(oldTbItem.getCreated());

        if(tbItemDescMapper.updateByPrimaryKey(tbItemDesc)!=1){
            throw new MyMallException("更新商品详情失败");
        }
        //同步缓存
        deleteProductDetRedis(id);
        //发送消息同步索引库
        sendRefreshESMessage("add",id);
        return getNormalItemById(id);
    }

    @Override
    public TbItem alterItemState(Long id, Integer state) {
        TbItem tbItemr = getNormalItemById(id);
        tbItemr.setStatus(state.byteValue());
        tbItemr.setUpdated(new Date());

        if (tbItemMapper.updateByPrimaryKey(tbItemr) != 1){
            throw new MyMallException("修改商品状态失败");
        }
        return getNormalItemById(id);
    }

    @Override
    public int deleteItem(Long id) {
        if(tbItemMapper.deleteByPrimaryKey(id)!=1){
            throw new MyMallException("删除商品失败");
        }
        if(tbItemDescMapper.deleteByPrimaryKey(id)!=1){
            throw new MyMallException("删除商品详情失败");
        }
        //发送消息同步索引库
        sendRefreshESMessage("delete",id);
        return 1;
    }

    //同步商品详情缓存
    public void deleteProductDetRedis(Long id){
        try {
            jedisClient.del(RDEIS_ITEM+":"+id);
        }catch (Exception e){
            log.error(e.toString(), e);
        }
    }

    //发送消息同步索引库
    public void sendRefreshESMessage(String type,Long id) {
        jmsTemplate.send(topicDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage(type+","+String.valueOf(id));
                return textMessage;
            }
        });
    }
}
