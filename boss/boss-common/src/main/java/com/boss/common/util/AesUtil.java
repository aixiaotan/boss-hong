package com.boss.common.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * aes加密工具类
 * @author Yao
 * @create 2018年7月25日
 */
public class AesUtil {

	private static final String ENCODE_RULES = "yyd@touna.cn";
	
	private static final String CHARSET = "utf-8";
	private static final String KEY_GENERATOR_ALGORITHM = "AES";
	private static final String SECURE_RANDOM_ALGORITHM = "SHA1PRNG";
	
	public static String encode(String content) {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_GENERATOR_ALGORITHM);
			SecureRandom random = SecureRandom.getInstance(SECURE_RANDOM_ALGORITHM);
			random.setSeed(ENCODE_RULES.getBytes());
			keyGenerator.init(128, random);
			SecretKey originalKey = keyGenerator.generateKey();
			byte[] rawByte = originalKey.getEncoded();
			SecretKey secretKey = new SecretKeySpec(rawByte, KEY_GENERATOR_ALGORITHM);
			Cipher cipher = Cipher.getInstance(KEY_GENERATOR_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] byteEncode = content.getBytes(CHARSET);
			byte[] bytesAes = cipher.doFinal(byteEncode);
			String resultEncode = new String(Base64.encodeBase64(bytesAes));
			return resultEncode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String decode(String content) {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_GENERATOR_ALGORITHM);
			SecureRandom random = SecureRandom.getInstance(SECURE_RANDOM_ALGORITHM);
			random.setSeed(ENCODE_RULES.getBytes());
			keyGenerator.init(128, random);
			SecretKey originalKey = keyGenerator.generateKey();
			byte[] byteArray = originalKey.getEncoded();
			SecretKey secretKey = new SecretKeySpec(byteArray, KEY_GENERATOR_ALGORITHM);
			Cipher cipher = Cipher.getInstance(KEY_GENERATOR_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] byteContent = Base64.decodeBase64(content);
			byte[] byteEncode = cipher.doFinal(byteContent);
			String resultEncode = new String(byteEncode, CHARSET);
			return resultEncode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
