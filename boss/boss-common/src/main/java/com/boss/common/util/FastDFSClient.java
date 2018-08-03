package com.boss.common.util;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

import cn.touna.common.utils.fastdfs.ClientGlobal;
import cn.touna.common.utils.fastdfs.MyException;
import cn.touna.common.utils.fastdfs.NameValuePair;
import cn.touna.common.utils.fastdfs.ProtoCommon;
import cn.touna.common.utils.fastdfs.StorageClient1;
import cn.touna.common.utils.fastdfs.StorageServer;
import cn.touna.common.utils.fastdfs.TrackerClient;
import cn.touna.common.utils.fastdfs.TrackerServer;

/**
 * 
 * @描述: FastDFS分布式文件系统操作客户端 .
 * @作者: lsc .
 * @创建时间: 2015-11-18,下午8:13:49 .
 * @版本号: V1.0 .
 */
public class FastDFSClient {

	private static Logger logger = Logger.getLogger(FastDFSClient.class);
	
	private static String config_addr ="/config.properties";

	public static class StorageClient2 extends StorageClient1 {
		private TrackerServer trackerServer1;
		private StorageServer storageServer1;
		
		public StorageClient2(TrackerServer trackerServer, StorageServer storageServer) {
			super(trackerServer, storageServer);
			this.trackerServer1 = trackerServer;
			this.storageServer1 = storageServer;
		}

		public TrackerServer getTrackerServer1() {
			return trackerServer1;
		}

		public StorageServer getStorageServer1() {
			return storageServer1;
		}
		
	}
	
	private static StorageClient2 getStorageClient(){
		try {
			String classPath = new File(FastDFSClient.class.getResource(config_addr).getFile()).getCanonicalPath();

			logger.info("=== CONF_FILENAME:" + classPath);
			ClientGlobal.init(classPath);
			TrackerClient trackerClient = new TrackerClient(
					ClientGlobal.g_tracker_group);
			TrackerServer trackerServer = trackerClient.getConnection();
			if (trackerServer == null) {
				logger.error("getConnection return null");
			}
			StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
			if (storageServer == null) {
				logger.error("getStoreStorage return null");
			}
			return new StorageClient2(trackerServer, storageServer);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private static void close(StorageClient2 storageClient2) throws IOException {
		StorageServer storageServer = storageClient2.getStorageServer1();
		TrackerServer trackerServer = storageClient2.getTrackerServer1();
		boolean test = ProtoCommon.activeTest(storageServer.getSocket());
		boolean test2 = ProtoCommon.activeTest(trackerServer.getSocket());
		if (test) {
			storageServer.close();
		}

		if (test2) {
			trackerServer.close();
		}
	}

	public static void releaseConnect(){

	}
	
	public static String uploadFile(byte[] file_buff, String fileName) {
		logger.info("uploadFile <<<<<<<<<<<<UPLOAD A FILE fileName :" + fileName);
		StorageClient2 storageClient1 = getStorageClient();
		
		logger.info("uploadFile <<<<<<<<<<<<UPLOAD A FILE storageClient1 fileName  " + fileName);
		
		try {
			NameValuePair[] meta_list = null; // new NameValuePair[0];
			String fileid = storageClient1.upload_file1(file_buff, getFileExt(fileName), meta_list);
			logger.info("uploadFile <<<<<<<<<<<<UPLOAD A FILE ID IS:" + fileid);
			return fileid;
		} catch (Exception ex) {
			logger.error(ex);
			return null;
		} finally {
			try {
				close(storageClient1);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	/**
	 * 
	 * @param file
	 *            文件
	 * @param fileName
	 *            文件名
	 * @return 返回Null则为失败
	 */
	public static  String uploadFile(File file, String fileName) {
		FileInputStream fis = null;
		logger.info("uploadFile <<<<<<<<<<<<UPLOAD A FILE fileName :" + fileName);
		StorageClient2 storageClient1 = getStorageClient();
		
		logger.info("uploadFile <<<<<<<<<<<<UPLOAD A FILE storageClient1 fileName  " + fileName);
		
		try {
			NameValuePair[] meta_list = null; // new NameValuePair[0];
			fis = new FileInputStream(file);
			byte[] file_buff = null;
			if (fis != null) {
				int len = fis.available();
				file_buff = new byte[len];
				fis.read(file_buff);
			}

			String fileid = storageClient1.upload_file1(file_buff,
					getFileExt(fileName), meta_list);
			logger.info("uploadFile <<<<<<<<<<<<UPLOAD A FILE ID IS:" + fileid);
			return fileid;
		} catch (Exception ex) {
			logger.error(ex);
			return null;
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
			try {
				close(storageClient1);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	/**
	 * 
	 * @param file
	 *            文件
	 * @param fileName
	 *            文件名
	 * @return 返回Null则为失败
	 * @throws MyException 
	 * @throws IOException 
	 */
	public static  String uploadFileThrows(File file, String fileName) throws IOException, MyException {
		FileInputStream fis = null;
		NameValuePair[] meta_list = null; // new NameValuePair[0];
		fis = new FileInputStream(file);
		byte[] file_buff = null;
		if (fis != null) {
			int len = fis.available();
			file_buff = new byte[len];
			fis.read(file_buff);
		}
		StorageClient2 storageClient1 = getStorageClient();
		String fileid = storageClient1.upload_file1(file_buff,
				getFileExt(fileName), meta_list);
		logger.info("<<<<<<<<<<<<UPLOAD A FILE ID IS:" + fileid);
		fis.close();
		close(storageClient1);
		return fileid;
	}

	/**
	 * 根据组名和远程文件名来删除一个文件
	 * 
	 * @param groupName
	 *            例如 "group1" 如果不指定该值，默认为group1
	 * @param fileName
	 *            例如"M00/00/00/wKgxgk5HbLvfP86RAAAAChd9X1Y736.jpg"
	 * @return 0为成功，非0为失败，具体为错误代码
	 */
	public static  int deleteFile(String groupName, String fileName) {
		StorageClient2 storageClient1 = getStorageClient();
		try {
			int result = storageClient1.delete_file(
					groupName == null ? "group1" : groupName, fileName);
			logger.info("<<<<<<<<<<<<DELETE A FILE GROUP  IS:" + groupName
					+ " AND FILENAME IS:" + fileName);
			return result;
		} catch (Exception ex) {
			logger.error(ex);
			return 0;
		}
		finally {
			try {
				close(storageClient1);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	/**
	 * 根据fileId来删除一个文件（我们现在用的就是这样的方式，上传文件时直接将fileId保存在了数据库中）
	 * 
	 * @param fileId
	 *            file_id源码中的解释file_id the file id(including group name and
	 *            filename);例如
	 *            group1/M00/00/00/ooYBAFM6MpmAHM91AAAEgdpiRC0012.xml
	 * @return 0为成功，非0为失败，具体为错误代码
	 */
	public static  int deleteFile(String fileId) {
		StorageClient2 storageClient1 = getStorageClient();
		try {
			int result = storageClient1.delete_file1(fileId);
			logger.info("<<<<<<<<<<<<DELETE A FILE ID IS:" + fileId);
			return result;
		} catch (Exception ex) {
			logger.error(ex);
			return 0;
		}
		finally {
			try {
				close(storageClient1);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	/**
	 * 修改一个已经存在的文件
	 * 
	 * @param oldFileId
	 *            原来旧文件的fileId, file_id源码中的解释file_id the file id(including group
	 *            name and filename);例如
	 *            group1/M00/00/00/ooYBAFM6MpmAHM91AAAEgdpiRC0012.xml
	 * @param file
	 *            新文件
	 * @param filePath
	 *            新文件路径
	 * @return 返回空则为失败
	 */
	public static  String modifyFile(String oldFileId, File file, String filePath) {
		String fileid = null;
		try {
			// 先上传
			fileid = uploadFile(file, filePath);
			if (fileid == null) {
				return null;
			}
			// 再删除
			int delResult = deleteFile(oldFileId);
			if (delResult != 0) {
				return null;
			}
		} catch (Exception ex) {
			logger.error(ex);
			return null;
		}
		return fileid;
	}
	
	/**
	 * 下载远程文件服务器文件到web服务器
	 * 
	 * @param fileUrl
	 * @param savePath
	 * @return
	 */
	public static  boolean saveToLocal(String fileUrl, String savePath) {
		try {
			DataInputStream in = new DataInputStream(
					downloadFile(fileUrl));
			/* 此处也可用BufferedInputStream与BufferedOutputStream 需要保存的路径 */
			DataOutputStream out = new DataOutputStream(new FileOutputStream(
					savePath));
			/* 将参数savePath，即将截取的图片的存储在本地地址赋值给out输出流所指定的地址 */
			byte[] buffer = new byte[4096];
			int count = 0;
			/* 将输入流以字节的形式读取并写入buffer中 */
			while ((count = in.read(buffer)) > 0) {
				out.write(buffer, 0, count);
			}
			out.close();
			in.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 文件下载
	 * 
	 * @param fileId
	 * @return 返回一个流
	 */
	public static  InputStream downloadFile(String fileId) {
		StorageClient2 storageClient1 = getStorageClient();
		logger.info("downloadFile <<<<<<<<<<<<UPLOAD A FILE storageClient1 fileId：：  " + fileId);
		try {
			byte[] bytes = storageClient1.download_file1(fileId);
			InputStream inputStream = new ByteArrayInputStream(bytes);
			logger.info("<<<<<<<<<<<<DOWNLOAD A FILE ID IS:" + fileId);
			return inputStream;
		} catch (Exception ex) {
			logger.error(ex);
			return null;
		}
		finally {
			try {
				close(storageClient1);
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	/**
	 * 获取文件后缀名（不带点）.
	 * 
	 * @return 如："jpg" or "".
	 */
	private static  String getFileExt(String fileName) {
		if (StringUtils.isBlank(fileName) || !fileName.contains(".")) {
			return "";
		} else {
			return fileName.substring(fileName.lastIndexOf(".") + 1); // 不带最后的点
		}
	}
}
