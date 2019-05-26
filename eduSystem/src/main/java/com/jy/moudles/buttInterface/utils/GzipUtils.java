package com.jy.moudles.buttInterface.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

/**
 * 解压压缩字符串工具类
 * @author Administrator
 *
 */
public class GzipUtils {
	private final static String ENCODING = "UTF-8";
	private final static String GZIPCODING = "gzip";
	private static final int bufferSize = 10240;

	/**
	 * 通用解压gzip
	 * @param inputStream
	 * @param encoding
	 * @return
	 * @throws Exception
	 * @author 黄忠柳
	 * Create on 2018年2月5日 上午10:22:01
	 */
	public static String readStream(InputStream inputStream, String encoding) throws Exception {
		
		StringBuffer buffer = new StringBuffer();
		InputStreamReader inputStreamReader = null;
		GZIPInputStream gZIPInputStream = null;
		
		if (GZIPCODING.equals(encoding)) {
			gZIPInputStream = new GZIPInputStream(inputStream);
			inputStreamReader = new InputStreamReader(gZIPInputStream, ENCODING);
		} else {
			inputStreamReader = new InputStreamReader(inputStream, ENCODING);
		}

		char[] c = new char[bufferSize];

		int lenI;
		while ((lenI = inputStreamReader.read(c)) != -1) {
			buffer.append(new String(c, 0, lenI));
		}
		if (inputStream != null) {
			inputStream.close();
		}
		if (gZIPInputStream != null) {
			gZIPInputStream.close();
		}
		return buffer.toString();
	}

}
