package com.jy.moudles.buttInterface.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
/**
 * 发送工具类
 * @author Administrator
 *
 */
public class SendPostUtils {
	
	/** 
     * 发送POST请求 
     *  
     * @param url 
     *            目的地址 
     * @param parameters 
     *            请求参数，Map类型。 
     * @return 远程响应结果 
     */  
    public static String sendPost(String url, Map<String, Object> parameters) {  
        String result = "";// 返回的结果  
        BufferedReader in = null;// 读取响应输入流  
        PrintWriter out = null;  
        StringBuffer sb = new StringBuffer();// 处理请求参数  
        String params = "";// 编码之后的参数  
        try {  
            // 编码请求参数  
            if (parameters.size() == 1) {  
                for (String name : parameters.keySet()) {  
                    sb.append(name).append("=").append(  
                            java.net.URLEncoder.encode(parameters.get(name).toString(),  
                                    "UTF-8"));  
                }  
                params = sb.toString();  
            } else {  
                for (String name : parameters.keySet()) {  
                    sb.append(name).append("=").append(  
                            java.net.URLEncoder.encode(parameters.get(name).toString(),  
                                    "UTF-8")).append("&");  
                }  
                String temp_params = sb.toString();  
                params = temp_params.substring(0, temp_params.length() - 1);  
            }  
            // 创建URL对象  
            java.net.URL connURL = new java.net.URL(url);  
            // 打开URL连接  
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL  
                    .openConnection();  
            // 设置通用属性  
            httpConn.setRequestProperty("Accept", "*/*");  
            httpConn.setRequestProperty("Connection", "Keep-Alive");  
            httpConn.setRequestProperty("User-Agent",  
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");  
            // 设置POST方式  
            httpConn.setDoInput(true);  
            httpConn.setDoOutput(true);  
            // 获取HttpURLConnection对象对应的输出流  
            out = new PrintWriter(httpConn.getOutputStream());  
            // 发送请求参数  
            out.write(params);  
            // flush输出流的缓冲  
            out.flush();  
            // 定义BufferedReader输入流来读取URL的响应，设置编码方式  
            in = new BufferedReader(new InputStreamReader(httpConn  
                    .getInputStream(), "UTF-8"));  
            String line;  
            // 读取返回的内容  
            while ((line = in.readLine()) != null) {  
                result += line;  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (out != null) {  
                    out.close();  
                }  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }  
        return result;  
    }  
    
    /**
     * post
     */
    public static String CHARSET = "UTF-8";
    private static final int connectTimeout = 1000*60*5;
	private static final int readTimeout = 1000*60*60;
	public static String webVisit(String remoteUrl, String charSet, String params) {
		if ("".equals(charSet))
			charSet = CHARSET;
		String result = "";
		HttpURLConnection httpConn = null;
		try {
			httpConn = instance(remoteUrl, "POST");

			if (!"".equalsIgnoreCase(params)) {
				OutputStreamWriter out = new OutputStreamWriter(httpConn.getOutputStream(), charSet);
				out.write(params);
				out.flush();
				out.close();
			}
			InputStream inputStream = httpConn.getInputStream();
			String encoding = httpConn.getContentEncoding();
			result = GzipUtils.readStream(inputStream, encoding);
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("exception", e.getMessage());
			result = jsonObject.toString();
		}
		return result;
	}
    
	private static HttpURLConnection instance(String urlString, String method) {
		HttpURLConnection httpConn = null;
		try {
			URL url = new URL(urlString);
			httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);
			httpConn.setUseCaches(false);
			httpConn.setAllowUserInteraction(false);
			httpConn.setInstanceFollowRedirects(true);
			httpConn.setConnectTimeout(connectTimeout);
			httpConn.setReadTimeout(readTimeout);

			httpConn.setRequestMethod(method);
			httpConn.setRequestProperty("Connection", "Keep-Alive");
		} catch (Exception e) {
			e.printStackTrace();
			httpConn = null;
		}
		return httpConn;
	}

    
    
}
