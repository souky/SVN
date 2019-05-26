package com.jy.moudles.buttInterface.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.TreeMap;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jy.moudles.user.entity.User;

/**
 * 参数验证  加密  解密
 * @author hzl
 *
 */
public class VerificationUtils {
	public static Logger logger = LoggerFactory.getLogger(VerificationUtils.class);
	
	
	/**
	 * 验证签名合法性
	 * @param accessKey  验签签名
	 * @param param  参数
	 * @param key 密钥
	 * @return
	 * @author 黄忠柳
	 * Create on 2018年1月23日 下午3:03:36
	 */
	public static boolean checkParamKey(TreeMap<String,Object> param,String accessKey,String key) {
		//根据参数和密钥进行加密
		String encode = creatEncrypt(param,key);
        return accessKey.equals(encode)?true:false;
	}
	
	/**
	 * 生成加密字符串
	 * @param param 参数
	 * @param key 密钥
	 * @return
	 * @author 黄忠柳
	 * Create on 2018年1月24日 下午2:04:31
	 */
	public static String creatEncrypt(TreeMap<String,Object> param,String key) {
		StringBuffer sbf = new StringBuffer();
		for (Object value : param.values()) {
			sbf.append(value);
		}
		
		String pbe = pbeEncrypt(sbf.toString(),key);
		String md5 = Md5Encrypt(pbe);
		return md5;
	}
	
	/**
	 * MD5 加密函数
	 * @param msg 加密字符
	 * @return 加密好的字符
	 * @author 黄忠柳
	 * Create on 2018年1月23日 下午5:25:54
	 */
	public static String Md5Encrypt(String msg) {
		try {
			MessageDigest md5Digest = MessageDigest.getInstance("MD5");
			// 更新要计算的内容
	        md5Digest.update(msg.getBytes());
	        // 完成哈希计算，得到摘要
	        byte[] md5Encoded = md5Digest.digest();
	        return Base64.encodeBase64URLSafeString(md5Encoded);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	/**
	 * 生成token
	 * @param user 用户的信息
	 * @author 黄忠柳
	 * Create on 2018年1月23日 下午5:16:03
	 */
	public static String createToken(User user,String key) {
		TreeMap<String,Object> param = new TreeMap<>();
		param.put("id", user.getId());
		param.put("curryTime", System.currentTimeMillis());
		return creatEncrypt(param,key);
	}
	
	
	/**
	 * 生成codeMsg
	 * @return
	 * @author 黄忠柳
	 * Create on 2018年1月24日 下午4:47:31
	 */
	public static String createCodeMsg() {
		int math = (int) ((Math.random()*9+1)*100000);
		return Md5Encrypt((math+System.currentTimeMillis())+"");
	}
	
	/**
	 *  基于口令的加密（password），对称 + 消息摘要
	 * @param msg 加密信息
	 * @param key 密钥
	 * @return
	 * @author 黄忠柳
	 * Create on 2018年1月24日 下午3:16:46
	 */
	public static String pbeEncrypt(String msg,String key) {
		try {
			
	        PBEKeySpec pbeKeySpec = new PBEKeySpec(key.toCharArray());
	        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBEWITHMD5andDES");
	        SecretKey  generateKey = secretKeyFactory.generateSecret(pbeKeySpec);

	        PBEParameterSpec  pbeParameterSpec = new PBEParameterSpec("12345678".getBytes(), 100);
	        Cipher cipher = Cipher.getInstance("PBEWITHMD5andDES");
	        cipher.init(Cipher.ENCRYPT_MODE, generateKey,pbeParameterSpec);
	        byte[] resultBytes = cipher.doFinal(msg.getBytes("UTF-8"));
	        return Hex.encodeHexString(resultBytes);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
