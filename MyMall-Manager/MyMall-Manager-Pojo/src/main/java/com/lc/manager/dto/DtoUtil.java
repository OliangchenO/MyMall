package com.lc.manager.dto;

import com.lc.common.pojo.ZTreeNode;
import com.lc.manager.pojo.*;

public class DtoUtil {
    public static TbMember MemberDto2Member(MemberDto memberDto){

        TbMember tbMember =new TbMember();

        if(!memberDto.getUsername().isEmpty()){
            tbMember.setUsername(memberDto.getUsername());
        }
        if(!memberDto.getPassword().isEmpty()){
            tbMember.setPassword(memberDto.getPassword());
        }
        if(!memberDto.getPhone().isEmpty()){
            tbMember.setPhone(memberDto.getPhone());
        }
        if(!memberDto.getEmail().isEmpty()){
            tbMember.setEmail(memberDto.getEmail());
        }
        if(!memberDto.getSex().isEmpty()){
            tbMember.setSex(memberDto.getSex());
        }
        if(!memberDto.getDescription().isEmpty()){
            tbMember.setDescription(memberDto.getDescription());
        }
        if(!memberDto.getProvince().isEmpty()){
            tbMember.setAddress(memberDto.getProvince()+" "
                    +memberDto.getCity()+" "+memberDto.getDistrict());
        }

        return tbMember;
    }

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

    public static TbContentCategory ContentCatDto2TbContentCategory(ContentCatDto contentCatDto) {
        TbContentCategory tbContentCategory =new TbContentCategory();

        tbContentCategory.setId(contentCatDto.getId());
        tbContentCategory.setName(contentCatDto.getName());
        tbContentCategory.setIsParent(contentCatDto.getIsParent());
        tbContentCategory.setSortOrder(contentCatDto.getSortOrder());
        tbContentCategory.setNum(contentCatDto.getNum());
        tbContentCategory.setRemark(contentCatDto.getRemark());
        tbContentCategory.setStatus(contentCatDto.getStatus());

        return tbContentCategory;
    }

    public static ContentDto TbContent2ContentDto(TbContent tbContent) {
        ContentDto contentDto =new ContentDto();

        contentDto.setId(tbContent.getId());
        contentDto.setProductId(tbContent.getProductId());
        contentDto.setImage(tbContent.getImage());
        contentDto.setCreated(tbContent.getCreated());
        contentDto.setUpdated(tbContent.getUpdated());

        return contentDto;
    }

    public static TbItem ItemDto2TbItem(ItemDto itemDto) {
        TbItem tbItem =new TbItem();

        tbItem.setTitle(itemDto.getTitle());
        tbItem.setPrice(itemDto.getPrice());
        tbItem.setCid(itemDto.getCid());
        tbItem.setImage(itemDto.getImage());
        tbItem.setSellPoint(itemDto.getSellPoint());
        tbItem.setNum(itemDto.getNum());
        if(itemDto.getLimitNum()==null||itemDto.getLimitNum()<0){
            tbItem.setLimitNum(10);
        }else{
            tbItem.setLimitNum(itemDto.getLimitNum());
        }

        return tbItem;
    }

    public static ItemDto TbItem2ItemDto(TbItem tbItem) {
        ItemDto itemDto =new ItemDto();

        itemDto.setTitle(tbItem.getTitle());
        itemDto.setPrice(tbItem.getPrice());
        itemDto.setCid(tbItem.getCid());
        itemDto.setImage(tbItem.getImage());
        itemDto.setSellPoint(tbItem.getSellPoint());
        itemDto.setNum(tbItem.getNum());
        if(tbItem.getLimitNum()==null){
            itemDto.setLimitNum(tbItem.getNum());
        }else if(tbItem.getLimitNum()<0&&tbItem.getNum()<0) {
            itemDto.setLimitNum(10);
        }else{
            itemDto.setLimitNum(tbItem.getLimitNum());
        }

        return itemDto;
    }

    public static ImageDto TbImage2ImageDto(TbImage tbImage){

        ImageDto imageDto =new ImageDto();

        imageDto.setId(tbImage.getId());
        imageDto.setImage(tbImage.getImage());
        imageDto.setImageMobile(tbImage.getImageMobile());
        imageDto.setLink(tbImage.getLink());
        imageDto.setCreated(tbImage.getCreated());
        imageDto.setUpdated(tbImage.getUpdated());

        return imageDto;
    }

    public static ZTreeNode TbItemCat2ZTreeNode(TbItemCat tbItemCat){

        ZTreeNode zTreeNode =new ZTreeNode();

        zTreeNode.setId(Math.toIntExact(tbItemCat.getId()));
        zTreeNode.setStatus(tbItemCat.getStatus());
        zTreeNode.setSortOrder(tbItemCat.getSortOrder());
        zTreeNode.setName(tbItemCat.getName());
        zTreeNode.setpId(Math.toIntExact(tbItemCat.getParentId()));
        zTreeNode.setIsParent(tbItemCat.getIsParent());
        zTreeNode.setRemark(tbItemCat.getRemark());

        return zTreeNode;
    }
}
