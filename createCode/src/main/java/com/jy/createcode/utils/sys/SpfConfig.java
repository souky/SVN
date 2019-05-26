package com.jy.createcode.utils.sys;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import com.jy.createcode.utils.Logger;

public class SpfConfig {

	private Logger logger = Logger.getLogger(this.getClass());
	private Properties propertie;
	private FileInputStream inputFile;
	private FileOutputStream outputFile;
	private String fileName = "/spf.properties";
	private static SpfConfig spfConfig = null;

	public static SpfConfig getInstance() {
		if (spfConfig == null)
			spfConfig = new SpfConfig();
		return spfConfig;
	}

	private SpfConfig() {
		propertie = new Properties();
		try {
			String path = this.getClass().getClassLoader().getResource("/")
					.getPath();
			inputFile = new FileInputStream(path + fileName);
			propertie.load(inputFile);
			inputFile.close();
		} catch (FileNotFoundException e) {
			logger.error("找不到spf.properties文件：" + e.getMessage());
		} catch (IOException ex) {
			logger.error("读取spf.properties文件内容失败：" + ex.getMessage());
		}
	}

	public String getValue(String key) {
		if (propertie.containsKey(key)) {
			String value = propertie.getProperty(key);// 得到某一属性的值
			return value;
		} else
			return "";
	}

	public void setValue(String key, String value) {
		propertie.setProperty(key, value);
	}

	public void updatePropertie() {
		try {
			File file = new File(fileName);
			if (file.exists()) {
				outputFile = new FileOutputStream(fileName);
				SimpleDateFormat df = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				propertie.store(outputFile, df.format(new Date()));
				outputFile.close();
			} else {
				logger.error("spf.properties文件不存在！");
			}
		} catch (Exception e) {
			logger.error("更新spf.properties文件失败：" + e.getMessage());
		}
	}

	public void updatePropertie(String description) {
		try {
			outputFile = new FileOutputStream(fileName);
			propertie.store(outputFile, description);
			outputFile.close();
		} catch (Exception e) {
			logger.error("更新spf.properties文件失败：" + e.getMessage());
		}
	}

	public static void main(String[] args) {
		SpfConfig spf = new SpfConfig();
		spf.setValue("PageList-DefaultShowCount", "15");
		spf.updatePropertie();
		spf = new SpfConfig();
		String val = spf.getValue("PageList-DefaultShowCount");
		System.out.println(val);
	}
}
