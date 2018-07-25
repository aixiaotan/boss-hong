package com.boss.common.util;

import java.util.Random;

public class VerifyCodeUtil {

	private static char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W','X', 'Y', 'Z', 
			'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t',
			'u','v','w','x','y','z','0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};  
	public static String getVerifyCode(Integer codeCount) {
		Random random = new Random();
		StringBuilder randomCode = new StringBuilder();
		// 随机产生codeCount数字的验证码。         
        for (int i = 0; i < codeCount; i++) {         
            // 得到随机产生的验证码数字。         
            String strRand = String.valueOf(codeSequence[random.nextInt(62)]);         
            // 将产生的四个随机数组合在一起。         
            randomCode.append(strRand);         
        } 
		return randomCode.toString();
	}
	
}
