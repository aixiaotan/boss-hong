package com.boss.common.util;

import java.io.PrintStream;
import java.nio.ByteBuffer;

import org.mission.common.utils.CommonUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

// Referenced classes of package org.mission.common.utils:
//            CommonUtils

public class BASE64Utils {

	public BASE64Utils() {
	}

	public static String encodeUTF8(String input) {
		return encode(input, "UTF-8");
	}

	public static String encode(String input, String charset) {
		try {
			return encode(input.getBytes(charset));
		} catch (Exception e) {
			throw CommonUtils.illegalStateException(e);
		}
	}

	public static String encode(byte bytes[]) {
		return (new BASE64Encoder()).encode(bytes);
	}

	public static String encode(ByteBuffer buf) {
		return (new BASE64Encoder()).encode(buf);
	}

	public static String decodeUTF8(String input) {
		try {
			return decode(input, "UTF-8");
		} catch (Exception e) {
			throw CommonUtils.illegalStateException(e);
		}
	}

	public static String decode(String input, String charset) {
		try {
			return new String(decode(input), charset);
		} catch (Exception e) {
			throw CommonUtils.illegalStateException(e);
		}
	}

	public static byte[] decode(String input) {
		try {
			return (new BASE64Decoder()).decodeBuffer(input);
		} catch (Exception e) {
			throw CommonUtils.illegalStateException(e);
		}
	}

	public static void main(String args[]) {
		String input = "\u878D\u4FE1\u8D22\u5BCC";
		String output = encodeUTF8(input);
		System.out.println((new StringBuilder()).append("encode input=").append(output).toString());
		System.out.println((new StringBuilder()).append("decode output=").append(decodeUTF8(output)).toString());
	}
}
