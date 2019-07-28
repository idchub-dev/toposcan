package com.util;

import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {
	
	private static final String KEY = "bugbycode_system";
	
	public static String decrypt(String content) {
		byte[] result = {};
		try {
			byte[] input = toByte(content);
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] md5 = md.digest(KEY.getBytes("UTF-8"));
			SecretKeySpec skc = new SecretKeySpec(md5, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skc);
			cipher.update(input);
			result = cipher.doFinal();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return new String(result);
	}
	
	public static String encrypt(String content) {
		byte[] result = {};
		try {
			byte[] input = content.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] md5 = md.digest(KEY.getBytes("UTF-8"));
			SecretKeySpec skc = new SecretKeySpec(md5, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, skc);
			cipher.update(input);
			result = cipher.doFinal();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return parseByte2HexStr(result);
	}
	
	private static byte[] toByte(String hexString) {
		int len = hexString.length() / 2;
		byte[] result = new byte[len];
		for (int i = 0; i < len; i++) {
			result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
		}
		return result;
	}
	
	private static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex);
		}
		return sb.toString();
	}
}