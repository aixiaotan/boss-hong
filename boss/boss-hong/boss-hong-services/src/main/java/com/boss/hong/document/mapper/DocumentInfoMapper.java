package com.boss.hong.document.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.boss.hong.document.entity.DocumentInfoDO;

@Mapper
public interface DocumentInfoMapper {
	
    /**
     * 批量修改附件关联关系
     * @author peiHongWei
     * @date 2018年7月11日 下午2:17:47
     */
    int updateBatchDocumentInfo(List<DocumentInfoDO> list);
}