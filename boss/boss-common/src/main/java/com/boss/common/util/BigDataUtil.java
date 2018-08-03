package com.boss.common.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

public class BigDataUtil {
	/**
	 * 获取签名
	 * 
	 * @param parameters
	 * @return
	 */

	public static String getSign(String parameters, String timestamp, String verificationCode) {
		StringBuilder sb = new StringBuilder();
		sb.append(parameters).append(verificationCode).append(timestamp);
		return MD5(sb.toString());

	}

	/**
	 * 获取文件签名
	 * 
	 * @param fileParams
	 * @param parameters
	 * @param appKey
	 * @param timestamp
	 * @return
	 * @throws Exception
	 */
	public static String fileSign(TreeMap<String, Object> fileParams, String parameters, String timestamp, String verificationCode) throws Exception {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, Object> fileEntry : fileParams.entrySet()) {
			String fileMD5 = getFileSign((FileInputStream) fileEntry.getValue());
			sb.append(fileMD5);
		}
		sb.append(parameters).append(verificationCode).append(timestamp);
		return MD5(sb.toString());
	}

	/**
	 * MD5 加密
	 * 
	 * @param str
	 * @return
	 */
	public static String MD5(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString();
	}

	/**
	 * 文件签名
	 * 
	 * @param fileInputStream
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public static String getFileSign(InputStream fileInputStream) throws IOException, NoSuchAlgorithmException {
		// 缓冲区大小（这个可以抽出一个参数）
		int bufferSize = 256 * 1024;
		DigestInputStream digestInputStream = null;
		// 拿到一个MD5转换器（同样，这里可以换成SHA1）
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		// 使用DigestInputStream
		digestInputStream = new DigestInputStream(fileInputStream, messageDigest);
		// read的过程中进行MD5处理，直到读完文件
		byte[] buffer = new byte[bufferSize];
		while (digestInputStream.read(buffer) > 0)
			;
		// 获取最终的MessageDigest
		messageDigest = digestInputStream.getMessageDigest();
		// 拿到结果，也是字节数组，包含16个元素
		byte[] resultByteArray = messageDigest.digest();
		// 同样，把字节数组转换成字符串
		return bufferToHex(resultByteArray);
	}

	public static String bufferToHex(byte[] byteArray) {
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString();
	}
}
