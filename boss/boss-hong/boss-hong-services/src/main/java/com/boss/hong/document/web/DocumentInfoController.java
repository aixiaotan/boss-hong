package com.boss.hong.document.web;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.boss.common.beans.ApiResult;
import com.boss.common.dto.document.DocumentInfoDto;
import com.boss.common.util.ConfigUtil;
import com.boss.common.util.FastDFSClient;
import com.boss.common.util.StringUtils;
import com.boss.hong.document.service.DocumentInfoService;

/**
 * 附件
 * @author peihongwei
 *
 * 2018年7月6日
 */
@RestController
@RequestMapping(value = "/document")
public class DocumentInfoController {

private static final Logger logger = LoggerFactory.getLogger(DocumentInfoController.class);
	@Autowired
	private DocumentInfoService documentInfoService;
	
	/**
	 * 保存附件
	 * @author peiHongWei
	 * @date 2018年7月11日 下午2:45:43
	 */
	@PostMapping(value = "/insertDocument")
	public ApiResult<Integer> insertDocument(@RequestBody @Validated DocumentInfoDto documentInfoDto){
		logger.info("DocumentInfoController insertDocument param:{}",documentInfoDto);
		ApiResult<Integer> apiResult = new ApiResult<>();
		documentInfoService.insertDocumentInfo(documentInfoDto);
		logger.info("DocumentInfoController insertDocument result:{}" , apiResult);
		return apiResult;
	}
	
	/**
	 * 删除附件
	 * @author peiHongWei
	 * @date 2018年7月11日 下午2:48:13
	 */
	@DeleteMapping(value = "/deleteDocumentInfo/id/{id}")
	public ApiResult<Integer> deleteDocumentInfo(@PathVariable Integer id ){
		logger.info("DocumentInfoController deleteDocumentInfo id:{}",id);
		DocumentInfoDto DocumentInfoDto = new DocumentInfoDto();
		ApiResult<Integer> apiResult = new ApiResult<>();
		DocumentInfoDto.setId(id);
		documentInfoService.deleteDocumentInfo(DocumentInfoDto);
//		logger.info("DocumentInfoController deleteDocumentInfo result:{}" , apiResult);
		return apiResult;
	}
	
	/**
	 * 查询附件
	 * @author peiHongWei
	 * @date 2018年7月11日 下午2:48:05
	 */
	@PostMapping(value = "/getDocumentInfo")
	public ApiResult<List<DocumentInfoDto>> getDocumentInfo(@RequestBody @Validated DocumentInfoDto DocumentInfoDto){
		logger.info("DocumentInfoController getDocumentInfo param:{}",DocumentInfoDto);
		ApiResult<List<DocumentInfoDto>> apiResult = new ApiResult<>();
		List<DocumentInfoDto> resultList = documentInfoService.listDocumentInfo(DocumentInfoDto);
		logger.info("DocumentInfoController getDocumentInfo result:{}" , apiResult);
		return apiResult.ok(resultList);
	}
	
	/**
	 * 上传附件
	 * @param file 文件流
	 * @param 2018年7月19日
	 * @return 附件唯一标识(id)
	 */
	@PostMapping(value = "/uploadDocument")
	public ApiResult<Integer> uploadDocument(@RequestParam(required = true) MultipartFile file) throws IOException{
		ApiResult<Integer> result = new ApiResult<>();
		long size = file.getSize();
		if(size > 1024 * 1024){
			// 图片限制最大1M (1024K)
			return result.error("附件过大，请重新选择");
		}
		
		String fileName = file.getOriginalFilename();
		String fileExt = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
		logger.info("fileName:{},fileExt{},fileSize{}",new Object[]{fileName,fileExt,size});
		
		if (!"gif,jpeg,jpg,png,bmp".contains(fileExt)) {
			return result.error(fileName + "不是允许的文件类型(gif,jpeg,jpg,png,bmp格式)");
		}
		
		File uploadFile = new File(ConfigUtil.getUploadFileAddr() + fileName);
		logger.info("filePath:{}",uploadFile.getAbsolutePath());
		
		//判断文件父目录是否存在
		if(!uploadFile.getParentFile().exists()){ 
			uploadFile.getParentFile().mkdir();
        }
		
		// 通过FastDFS上传到远程服务器
		String fileId = FastDFSClient.uploadFile(file.getBytes(), fileName);
		FastDFSClient.releaseConnect();
		if(StringUtils.isEmpty(fileId)){
			return result.error("上传文件失败!!");
		}
		
		DocumentInfoDto document = new DocumentInfoDto();
		// XXX 新建附件类型枚举类
		document.setGroupCode("outline");
		document.setFileName(fileName);
		document.setFilePath(fileId);
		document.setFileSize((int) file.getSize());
		Integer count = documentInfoService.insertDocumentInfo(document);
       
//		logger.info("insertResult:{}",JSON.toJSONString(count));
		return result.ok(count);
	}
	
	public static void main(String[] args) {
		String fileName = "aaajpg";
		String fileExt = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length()).toLowerCase();
		String fileExt1 = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
		System.out.println(fileExt);
		System.out.println(fileExt1);
	}
}
