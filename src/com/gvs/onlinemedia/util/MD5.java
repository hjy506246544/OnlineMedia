package com.gvs.onlinemedia.util;
 
 
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
 
public class MD5 {  
 
   private final static String[] hexDigits = {  
       "0", "1", "2", "3", "4", "5", "6", "7",  
       "8", "9", "a", "b", "c", "d", "e", "f"};  
   /**  
    * 转换字节数组为16进制字串  
    * @param b 字节数组  
    * @return 16进制字串  
    */  
   public static String byteArrayToHexString(byte[] b) {  
     StringBuffer resultSb = new StringBuffer();  
     for (int i = 0; i < b.length; i++) {  
       resultSb.append(byteToHexString(b[i]));  
     }  
     return resultSb.toString();  
   } 
   private static String byteToHexString(byte b) {  
     int n = b;  
     if (n < 0)  
       n = 256 + n;  
     int d1 = n / 16;  
     int d2 = n % 16;  
     return hexDigits[d1] + hexDigits[d2];  
   }
   /**
    * Get the MD5 encrypt hex string of the origin string. <br/>The origin
    * string won't validate here, so who use the API should validate by
    * himself.
    * 
    * @param origin
    * @return
    * @throws NoSuchAlgorithmException
    */
   public static String MD5Encode(String origin)
           throws NoSuchAlgorithmException
   {
       MessageDigest md = MessageDigest.getInstance("MD5");
       return byteArrayToHexString(md.digest(origin.getBytes()));
   }
   
   public static String getMD5(String str) {

		MessageDigest md5 = null;

		try {

			md5 = MessageDigest.getInstance("MD5");

		} catch (Exception e) {

			e.printStackTrace();

			return "";

		}

		char[] charArray = str.toCharArray();

		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++) {

			byteArray[i] = (byte) charArray[i];

		}

		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();

		for (int i = 0; i < md5Bytes.length; i++)

		{

			int val = ((int) md5Bytes[i]) & 0xff;

			if (val < 16)

			{

				hexValue.append("0");

			}

			hexValue.append(Integer.toHexString(val));

		}

		return hexValue.toString();

	}
   
   public static String compile(String origin) {  
     String resultString = null;  
 
     try {
         resultString=new String(origin);  
         MessageDigest md = MessageDigest.getInstance("MD5");  
         
         resultString=byteArrayToHexString(md.digest(resultString.getBytes()));  
       }  
       catch (Exception ex) {  
   
       }       
       return resultString!=null?resultString.toUpperCase():"";  
     }
	  
   }     	 
