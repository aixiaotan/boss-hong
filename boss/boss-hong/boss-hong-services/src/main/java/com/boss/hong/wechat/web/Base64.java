package com.boss.hong.wechat.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Base64 {

	private static Logger log = LoggerFactory.getLogger(Base64.class);

	public Base64() {
	}

	public static String encode(byte data[]) {
		int start = 0;
		int len = data.length;
		StringBuffer buf = new StringBuffer((data.length * 3) / 2);
		int end = len - 3;
		int i = start;
		int n = 0;
		do {
			if (i > end)
				break;
			int d = (data[i] & 255) << 16 | (data[i + 1] & 255) << 8 | data[i + 2] & 255;
			buf.append(legalChars[d >> 18 & 63]);
			buf.append(legalChars[d >> 12 & 63]);
			buf.append(legalChars[d >> 6 & 63]);
			buf.append(legalChars[d & 63]);
			i += 3;
			if (n++ >= 14) {
				n = 0;
				buf.append(" ");
			}
		} while (true);
		if (i == (start + len) - 2) {
			int d = (data[i] & 255) << 16 | (data[i + 1] & 255) << 8;
			buf.append(legalChars[d >> 18 & 63]);
			buf.append(legalChars[d >> 12 & 63]);
			buf.append(legalChars[d >> 6 & 63]);
			buf.append("=");
		} else if (i == (start + len) - 1) {
			int d = (data[i] & 255) << 16;
			buf.append(legalChars[d >> 18 & 63]);
			buf.append(legalChars[d >> 12 & 63]);
			buf.append("==");
		}
		return buf.toString();
	}

	private static int decode(char c) {
		if (c >= 'A' && c <= 'Z')
			return c - 65;
		if (c >= 'a' && c <= 'z')
			return (c - 97) + 26;
		if (c >= '0' && c <= '9')
			return (c - 48) + 26 + 26;
		switch (c) {
		case 43: // '+'
			return 62;

		case 47: // '/'
			return 63;

		case 61: // '='
			return 0;
		}
		throw new RuntimeException((new StringBuilder()).append("unexpected code: ").append(c).toString());
	}

	public static byte[] decode(String s) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			decode(s, ((OutputStream) (bos)));
		} catch (IOException e) {
			throw new RuntimeException();
		}
		byte decodedBytes[] = bos.toByteArray();
		try {
			bos.close();
			bos = null;
		} catch (IOException ex) {
			log.error("base64 decode error.", ex);
		}
		return decodedBytes;
	}

	private static void decode(String s, OutputStream os) throws IOException {
		int i = 0;
		do {
			int len;
			for (len = s.length(); i < len && s.charAt(i) <= ' '; i++)
				;
			if (i == len)
				break;
			int tri = (decode(s.charAt(i)) << 18) + (decode(s.charAt(i + 1)) << 12) + (decode(s.charAt(i + 2)) << 6)
					+ decode(s.charAt(i + 3));
			os.write(tri >> 16 & 255);
			if (s.charAt(i + 2) == '=')
				break;
			os.write(tri >> 8 & 255);
			if (s.charAt(i + 3) == '=')
				break;
			os.write(tri & 255);
			i += 4;
		} while (true);
	}

	private static final char legalChars[] = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
			.toCharArray();

}
