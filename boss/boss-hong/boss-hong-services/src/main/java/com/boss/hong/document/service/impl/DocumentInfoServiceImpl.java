package com.boss.hong.document.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.boss.common.dto.document.DocumentInfoDto;
import com.boss.hong.base.service.CommonService;
import com.boss.hong.document.entity.DocumentInfoDO;
import com.boss.hong.document.mapper.DocumentInfoMapper;
import com.boss.hong.document.service.DocumentInfoService;

/**
 * 附件相关服务实现
 * @author peihongwei
 *
 * 2018年7月6日
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DocumentInfoServiceImpl implements DocumentInfoService{
	
	private static final Logger logger = LoggerFactory.getLogger(DocumentInfoServiceImpl.class);
	
	@Autowired
	private DocumentInfoMapper documentInfoMapper;
	@Autowired
	private CommonService commonService;

	@Override
	public Integer insertDocumentInfo(DocumentInfoDto documentInfoDto) {
		logger.info("DocumentInfoServiceImpl insertDocumentInfo param:"+documentInfoDto);
		DocumentInfoDO documentInfoDo = new DocumentInfoDO();
		BeanUtils.copyProperties(documentInfoDto, documentInfoDo);
		// rowNo字段db中不能为空，但序号要等大纲提交才能确定,添加图片附件时暂时设为1
		documentInfoDo.setRowNo(1);
		documentInfoDo.setCreatedTime(new Date());
		documentInfoDo.setUpdatedTime(new Date());
		Integer count = commonService.insert(documentInfoDo);
		logger.info("DocumentInfoServiceImpl insertDocumentInfo result:{}",count);
		// FIXME 新增之后返回主键
		List<DocumentInfoDO> newDocument = commonService.dynamicQuery(documentInfoDo);
		return newDocument.get(0).getId();
	}

	@Override
	public List<DocumentInfoDto> listDocumentInfo(DocumentInfoDto documentInfoDto) {
		logger.info("DocumentInfoServiceImpl listDocumentInfo param:"+documentInfoDto);
		DocumentInfoDO documentInfoDo = new DocumentInfoDO();
		BeanUtils.copyProperties(documentInfoDto, documentInfoDo);
		List<DocumentInfoDO> documentInfoDtos = commonService.dynamicQuery(documentInfoDo);
		List<DocumentInfoDto> infoDtos = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(documentInfoDtos)) {
			DocumentInfoDto documentResultDto = null;
			for (DocumentInfoDO infoDto : documentInfoDtos) {
				documentResultDto = new DocumentInfoDto();
				BeanUtils.copyProperties(infoDto, documentResultDto);
				// 附件地址加上文件服务器前缀
				/*if(org.apache.commons.lang.StringUtils.isNotBlank(documentResultDto.getFilePath())){
					documentResultDto.setFilePath(ConfigUtils.getValue(ConfigConst.ATTACHMENT_DOMAIN) + documentResultDto.getFilePath());
				}*/
				infoDtos.add(documentResultDto);
			}
		}/*else {
			return result.error(ReturnMsg.SELECT_ERROR);
		}*/
		return infoDtos;
	}


	@Override
	public Integer deleteDocumentInfo(DocumentInfoDto documentInfoDto) {
		logger.info("DocumentInfoServiceImpl deleteDocumentInfo param:" + documentInfoDto);
		DocumentInfoDO documentInfoDo = new DocumentInfoDO();
		BeanUtils.copyProperties(documentInfoDto, documentInfoDo);
		Integer count = commonService.delete(documentInfoDo);
		logger.info("DocumentInfoServiceImpl deleteDocumentInfo result:{}" ,count);
		return count;
	}

	@Override
	public Integer updateBatchDocumentInfo(List<DocumentInfoDto> documentInfoDtos) {
		if(CollectionUtils.isEmpty(documentInfoDtos)) {
			return 0;
		}
		logger.info("DocumentInfoServiceImpl updateBatchDocumentInfo params:"+JSONObject.toJSONString(documentInfoDtos));
		List<DocumentInfoDO> documentInfoDos = new ArrayList<DocumentInfoDO>();
		for (DocumentInfoDto documentInfoDto : documentInfoDtos) {
			DocumentInfoDO documentInfoDO = new DocumentInfoDO();
			BeanUtils.copyProperties(documentInfoDto, documentInfoDO);
			documentInfoDos.add(documentInfoDO);
		}
		Integer count = documentInfoMapper.updateBatchDocumentInfo(documentInfoDos);
		
		logger.info("DocumentInfoServiceImpl updateBatchDocumentInfo result:{}",count);
		if(count == -1) {
			//TODO 
		}
		return count;
	}

	
}
