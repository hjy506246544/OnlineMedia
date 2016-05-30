package com.gvs.onlinemedia.util;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * 3DES���ܹ�����
 * 
 * @author liufeng 
 * @date 2012-10-11
 */
public class Des3 {
	// ��Կ
	private final static String secretKey = "liuyunqiang@lx100$#365#$";
	// ��
	public final static String iv = "01234567";
	// �ӽ���ͳһʹ�õı��뷽ʽ
	private final static String encoding = "utf-8";

	/**
	 * 3DES����
	 * 
	 * @param plainText ��ͨ�ı�
	 * @return
	 * @throws Exception 
	 */
	public static String encode(String plainText,String secretKey/*,String iv*/) throws Exception {
//		if(iv == ""){
//			iv="01234567";
//		}
		
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);

		Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
		IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
		byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
		return Base64.encode(encryptData);
	}

	/**
	 * 3DES����
	 * 
	 * @param encryptText �����ı�
	 * @return
	 * @throws Exception
	 */
	public static String decode(String encryptText,String secretKey/*,String iv*/) throws Exception {
//		if(iv == ""){
//			iv="01234567";
//		}
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
		IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

		byte[] decryptData = cipher.doFinal(Base64.decode(encryptText));

		return new String(decryptData, encoding);
	}

	public static void main(String[] args) throws Exception {
		 System.out.println(Des3.encode("test","cellcomfala1234567890000"/*,"efalacom"*/));
		 System.out.println(Des3.decode("11dA/rktdCc=","cellcomfala1234567890000"/*,"efalacom"*/));
	}

}
