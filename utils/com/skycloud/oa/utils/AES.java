package com.skycloud.oa.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class AES {

	private final static String key = "chinaskycloudcom";
	private final static String keyiv = "chinacloudvalley";

	/**
	 * 加密程序
	 * 
	 * @param sSrc
	 * @return
	 * @throws Exception
	 */
	public static String Encrypt(String sSrc) throws Exception {

		byte[] raw = key.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// "算法/模式/补码方式"
		IvParameterSpec iv = new IvParameterSpec(keyiv.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		byte[] encrypted = cipher.doFinal(sSrc.getBytes());
		String encrypted1 = new BASE64Encoder().encode(encrypted);// 此处使用BASE64做转码功能，同时能起到2次加密的作用。
		return java.net.URLEncoder.encode(encrypted1, "UTF-8");
	}

	/**
	 * 解密程序
	 * 
	 * @param sSrc
	 * @return
	 * @throws Exception
	 */
	public static String Decrypt(String sSrc) throws Exception {
		try {
			sSrc = java.net.URLDecoder.decode(sSrc, "UTF-8");
			byte[] raw = key.getBytes();
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(keyiv.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);// 先用base64解密
			try {
				byte[] original = cipher.doFinal(encrypted1);
				String originalString = new String(original);
				return originalString;
			} catch (Exception e) {
				return null;
			}
		} catch (Exception ex) {
			return null;
		}
	}
}
