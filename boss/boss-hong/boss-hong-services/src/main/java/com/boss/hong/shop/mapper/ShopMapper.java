package com.boss.hong.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.boss.common.dto.shop.LabelDto;
import com.boss.common.dto.shop.OutlineDto;
import com.boss.hong.shop.entity.LabelDO;
import com.boss.hong.shop.entity.OutlineDO;
import com.boss.hong.shop.entity.ShopDO;
import com.boss.hong.shop.entity.VisitRecordDO;

@Mapper
public interface ShopMapper {

    /**
     * 插入店铺信息并返回主键
     * @author peiHongWei
     * @date 2018年7月10日 下午5:37:28
     */
    int insertShopAndReturnId(ShopDO record);
    
    /**
     * 插入店铺产品大纲信息并返回主键
     * @author peiHongWei
     * @date 2018年7月10日 下午5:37:28
     */
    int insertOutlineAndReturnId(OutlineDO record);
    
    /**
     * 批量插入标签
     * @author peiHongWei
     * @date 2018年7月9日 下午5:28:19
     */
    int insertShopLabelBatch(List<LabelDO> list);
    
    /**
     * 获取店铺下面大纲数量
     * @author peiHongWei
     * @date 2018年7月9日 下午5:28:19
     */
    int countOutlineByShopId(Integer shopId);
    
    /**
     * 获取店铺访问量
     * @author peiHongWei
     * @date 2018年7月9日 下午5:28:19
     */
    int getShopVisitCount(VisitRecordDO visitRecordDo);
    /**
     * 查询产品大纲列表
     */
    List<OutlineDto> listOutlineByShop(ShopDO shop);
    
    /**
     * 查询店铺标签
     */
    List<LabelDto> listLabelByShopId(Integer shopId);
}