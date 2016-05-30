package com.gvs.onlinemedia.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @AES鍔犺В瀵嗘帴鍙� BY Y.LEE 2012-09-29
 *
 *           -www.cellcom.com.cn
 *
 *           AES/ECB/PKCS5Padding
 *
 * @鍔犲瘑鏂规硶锛歋tring Encrypt(String sSrc, String sKey)锛� 鍙傛暟锛氬緟鍔犲瘑瀛椾覆 锛屽瘑閽�
 *
 * @瑙ｅ瘑鏂规硶锛歋tring Decrypt(String sSrc, String sKey)锛� 鍙傛暟锛氬緟瑙ｅ瘑瀛椾覆锛屽瘑閽�
 *
 */
public class AESEncoding {
	// 鍔犲瘑
	public static String Encrypt(String sSrc, String sKey) throws Exception {
		if (sKey == null) {
			System.out.print("Key涓虹┖null");
			return null;
		}
		// 鍒ゆ柇Key鏄惁涓�16浣�
		if (sKey.length() != 16) {
			System.out.print("Key闀垮害涓嶆槸16浣�");
			return null;
		}
		byte[] raw = sKey.getBytes("utf-8");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// "绠楁硶/妯″紡/琛ョ爜鏂瑰紡"
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));

		return new Base64().encode(encrypted);// BASE64鍋氳浆鐮�
	}

	// 瑙ｅ瘑
	public static String Decrypt(String sSrc, String sKey) throws Exception {
		try {
			// 鍒ゆ柇Key鏄惁姝ｇ‘
			if (sKey == null) {
				System.out.print("Key涓虹┖null");
				return null;
			}
			// 鍒ゆ柇Key鏄惁涓�16浣�
			if (sKey.length() != 16) {
				System.out.print("Key闀垮害涓嶆槸16浣�");
				return null;
			}
			byte[] raw = sKey.getBytes("utf-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] encrypted1 = new Base64().decode(sSrc);// 鍏堢敤base64瑙ｅ瘑
			try {
				byte[] original = cipher.doFinal(encrypted1);
				String originalString = new String(original, "utf-8");
				return originalString;
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

}
