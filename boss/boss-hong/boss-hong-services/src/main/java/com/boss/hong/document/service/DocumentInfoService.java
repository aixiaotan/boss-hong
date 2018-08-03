package com.boss.hong.document.service;

import java.util.List;

import com.boss.common.dto.document.DocumentInfoDto;

/**
 * 附件相关接口服务
 * @author peihongwei
 *
 * 2018年7月6日
 */
public interface DocumentInfoService {
	
	/**
	 * 新增一条附件信息，并返回主键
	 * @author peiHongWei
	 * @date 2018年7月11日 上午9:48:29
	 */
	public Integer insertDocumentInfo (DocumentInfoDto documentInfoDto);
	
	/**
	 * 查询附件信息
	 * @author peiHongWei
	 * @date 2018年7月11日 上午9:48:29
	 */
	public List<DocumentInfoDto> listDocumentInfo (DocumentInfoDto documentInfoDto);
	
	/**
	 * 修改一条附件信息
	 * @author peiHongWei
	 * @date 2018年7月11日 上午9:48:47
	 */
//	public Integer updateDocumentInfo (DocumentInfoDto documentInfoDto);
	/**
	 * 修改一条附件信息
	 * @author peiHongWei
	 * @date 2018年7月11日 上午9:48:47
	 */
	public Integer updateBatchDocumentInfo (List<DocumentInfoDto> documentInfoDtos);
	
	/**
	 * 删除一条附件信息
	 * @author peiHongWei
	 * @date 2018年7月11日 上午9:48:47
	 */
	public Integer deleteDocumentInfo (DocumentInfoDto documentInfoDto);

}
