package idv.daniel.blogging.util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Md5util {

	public static String getMd5String(String str) {
		String ret = null;
		try {
			// 生成一個MD5加密計算摘要
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 計算md5函數
			md.update(str.getBytes());

			ret = new BigInteger(1, md.digest()).toString(16);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return ret;
	}

}