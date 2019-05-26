package com.jy.createcode.ctrl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jy.createcode.entity.GenEntity;
import com.jy.createcode.entity.GenFiledsEntity;
import com.jy.createcode.utils.FileDel;
import com.jy.createcode.utils.FileZip;
import com.jy.createcode.utils.Freemarker;
import com.jy.createcode.utils.PathUtil;

/**
 * 快速生成代码的Controller类
 * @author QXQ
 * 20170313
 */
@Controller
public class CodeController extends BaseController {

	/**
	 * 去代码生成器页面
	 */
	@RequestMapping(value = "/code/initCreateCode.do")
	public ModelAndView initCreateCode() throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("spf/tableManager.jsp");
		return mv;
	}

	/**
	 * 生成代码
	 */
	@RequestMapping(value = "/code/createCode.do", method = RequestMethod.POST)
	public void createCode(HttpServletResponse response,@RequestBody GenEntity entity) throws Exception {
		// 表名称
		String tableName = entity.getTableName();
		// 对象名称
		String objectName = entity.getObjectName();
		
		String packageName = entity.getPackageName();
		
		//对象注释
		String remark = entity.getRemark();
		
		List<String[]> fieldList = new ArrayList<String[]>();
		for (GenFiledsEntity genEntity : entity.getGenFiledsEntities()) {
			String[] ss = new String[9];
			// 数据库字段
			ss[0] = genEntity.getColumnName();
			if (!"datetime".equalsIgnoreCase(genEntity.getColumnType())) {
				// 数据库类型
				ss[1] = genEntity.getColumnType().substring(0, (genEntity.getColumnType().indexOf("(")));
				// 数据库字段长度
//				ss[2] = genEntity.getColumnType().substring(genEntity.getColumnType().indexOf("("), genEntity.getColumnType().indexOf(")"));
				ss[2] = genEntity.getColumnType().replace(ss[1], "");
			} else {
				ss[1] = genEntity.getColumnType();
				ss[2] = "";
			}
			// 对象字段
			ss[3] = genEntity.getJavaProprety();
			// 对象类型
			ss[4] = genEntity.getJavaType();
			// 注释
			ss[5] = genEntity.getRemark();
			// 是否允许为空
			ss[6] = String.valueOf(genEntity.getRequired());
			// 默认值
			ss[7] = genEntity.getColumnDefault();
			// 无符号
			ss[8] = "0";
			fieldList.add(ss);
		}

		Map<String, Object> root = new HashMap<String, Object>(); // 创建数据模型
		root.put("fieldList", fieldList);
		root.put("packageName", packageName); // 包名
		root.put("objectName", objectName); // 类名
		root.put("objectNameLower", objectName.toLowerCase()); // 类名(全小写)
		root.put("objectNameUpper", objectName.toUpperCase()); // 类名(全大写)
		root.put("nowDate", new Date()); // 当前日期
		root.put("remark", remark);
		Map<String, String> map = System.getenv();
	    String userName = map.get("USERNAME");// 获取用户名
		root.put("createUser", userName == null ? "1" : userName);
		root.put("tableName", tableName);

		FileDel.delFolder(PathUtil.getClasspath() + "user/ftl/code"); // 生成代码前,先清空之前生成的代码

		String filePath = "user/ftl/code/" + packageName; // 存放路径
		String ftlPath = "createCode"; // ftl路径
		/* 生成controller */
		Freemarker.printFile("controllerTemplate.ftl", root, "/controller/"
				+ firstLetterToUpper(objectName) + "Controller.java", filePath, ftlPath);
		/* 生成service */
		Freemarker.printFile("serviceTemplate.ftl", root, "/service/"
				+ firstLetterToUpper(objectName) + "Service.java", filePath, ftlPath);
		/* 生成serviceImpl */
		Freemarker.printFile("serviceImplTemplate.ftl", root, "/service/impl/"
				+ firstLetterToUpper(objectName) + "ServiceImpl.java", filePath, ftlPath);
		// 生成Entity
		Freemarker.printFile("entityTemplate.ftl", root, "/entity/" 
				+ firstLetterToUpper(objectName) + ".java", filePath, ftlPath);
		// 生成Dao
		Freemarker.printFile("daoTemplate.ftl", root, "/dao/"
				+ firstLetterToUpper(objectName) + "Dao.java", filePath, ftlPath);
		/* 生成mybatis xml */
		Freemarker.printFile("mapperMysqlTemplate.ftl", root, "/mapper/" + firstLetterToUpper(objectName) + "MysqlMapper.xml",
				filePath, ftlPath);
//		Freemarker.printFile("mapperOracleTemplate.ftl", root, "code/"
//				+ packageName + "/mybatis_oracle/" + objectName + "Mapper.xml",
//				filePath, ftlPath);
		/* 生成SQL脚本 */
		Freemarker.printFile("mysql_SQL_Template.ftl", root, "/mysql数据库脚本/JY_"
				+ objectName.toUpperCase() + "_TAB.sql", filePath,
				ftlPath);
//		Freemarker.printFile("oracle_SQL_Template.ftl", root, "oracle数据库脚本/"
//				+ tabletop + objectName.toUpperCase() + ".sql", filePath,
//				ftlPath);
		/* 生成jsp页面 */
//		Freemarker.printFile("jsp_list_Template.ftl", root, "/jsp/"
//				+ packageName + "/" + objectName.toLowerCase() + "_list.jsp",
//				filePath, ftlPath);
//		Freemarker.printFile("jsp_edit_Template.ftl", root, "/jsp/"
//				+ packageName + "/" + objectName.toLowerCase() + "_edit.jsp",
//				filePath, ftlPath);
//		/* 生成说明文档 */
//		Freemarker.printFile("docTemplate.ftl", root, "说明.doc", filePath,
//				ftlPath);
		/* 生成的全部代码压缩成zip文件 */
		FileZip.zip(PathUtil.getClasspath() + "user/ftl/code/" + packageName,
				PathUtil.getClasspath() + "user/ftl/code/" + packageName + ".zip");
		/* 下载代码 */
//		FileDownload.fileDownload(response, PathUtil.getClasspath()
//				+ "user/ftl/code.zip", "code.zip");

	}
	
	private static String firstLetterToUpper(String str){
		char[] array = str.toCharArray();
		//ASCII a-z 范围为97-122
		if(array[0] > 96 && array[0] < 123 ){
			array[0] -= 32;
		}
		
		return String.valueOf(array);
	}

}
