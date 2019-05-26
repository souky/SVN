package com.jy.common.utils.mathtools;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 算法工具
 * 
 * @author zhangmeng@jinyang.cn.cn
 * @since 2017-12-01
 *
 */
public class EduEvaluatingUtils {

	/**
	 * 
	 * 平均数
	 * 
	 * @param array:分数集合
	 * @return BigDecimal
	 */
	public static BigDecimal getAverage(List<BigDecimal> array) {
		// 判空
		if (null == array || 0 == array.size()) {
			return BigDecimal.ZERO;
		}

		// 变量sum:集合所有项的和
		BigDecimal sum = BigDecimal.ZERO;
		// 遍历求和
		for (BigDecimal bigDecimal : array) {
			sum = sum.add(bigDecimal);
		}

		// 返回平均数
		return (sum.divide(BigDecimal.valueOf(array.size()), 2, RoundingMode.HALF_UP));
	}

	/**
	 * 
	 * 方差
	 * 
	 * @param array:分数集合
	 * @return BigDecimal
	 */
	public static BigDecimal getVariance(List<BigDecimal> array) {
		// 判空
		if (null == array || 0 == array.size()) {
			return BigDecimal.ZERO;
		}

		// avg:集合平均数ֵ
		BigDecimal avg = BigDecimal.ZERO;
		// sum1:所有差平方的和
		BigDecimal sum1 = BigDecimal.ZERO;

		// 求平均ֵ
		avg = EduEvaluatingUtils.getAverage(array);

		// 遍历求差平均的和
		for (BigDecimal bigDecimal : array) {
			sum1 = sum1.add((avg.subtract(bigDecimal)).multiply((avg.subtract(bigDecimal))));
		}

		// 返回方差
		return sum1.divide(BigDecimal.valueOf(array.size()), 2, RoundingMode.HALF_UP);
	}

	/**
	 * 
	 * 标准差
	 * 
	 * @param array:分数集合
	 * @return BigDecimal
	 */
	public static BigDecimal getStandardDeviation(List<BigDecimal> array) {
		// 判空
		if (null == array || 0 == array.size()) {
			return BigDecimal.ZERO;
		}

		// variance:方差
		BigDecimal variance = BigDecimal.ZERO;
		// 调用求方差公式得到方差
		variance = EduEvaluatingUtils.getVariance(array);

		// 数学方法开根号求标准差
		BigDecimal std = new BigDecimal(Math.sqrt(variance.doubleValue()));

		// 返回标准差
		return std.setScale(2, RoundingMode.HALF_UP);
	}

	/**
	 * 
	 * 差异系数
	 * 
	 * @param array:分数集合
	 * @return String
	 */
	public static String getDifferenceCoefficient(List<BigDecimal> array) {
		// 判空
		if (null == array || 0 == array.size()) {
			return "0";
		}

		// std:标准差
		BigDecimal std = BigDecimal.ZERO;
		// avg:平均值ֵ
		BigDecimal avg = BigDecimal.ZERO;
		// cv:方差
		BigDecimal cv = BigDecimal.ZERO;

		// 百分比形式
		DecimalFormat df = new DecimalFormat("0.00%");

		// 调用类得平均数ֵ
		avg = EduEvaluatingUtils.getAverage(array);

		// 判断平均数是否为0
		if (avg.compareTo(BigDecimal.ZERO) == 0) {
			return "平均数为0";
		}

		// 调用类得标准差
		std = EduEvaluatingUtils.getStandardDeviation(array);

		// 公式求差异系数
		cv = std.divide(avg, 4, RoundingMode.HALF_UP);

		// 以百分比形式返回差异系数
		return df.format(cv);
	}

	/**
	 * 
	 * 标准Z分数
	 * 
	 * @param array:分数集合
	 * @param x:原始分数
	 * @return BigDecimal
	 */
		public static BigDecimal getStandardZ(List<BigDecimal> array,BigDecimal x) {
		
			// 判空
			if (null == array || 0 == array.size()) {
						return BigDecimal.ZERO;
					}			
					
			//avg:平均数ֵ
			BigDecimal avg = BigDecimal.ZERO;
			//std:标准差
			BigDecimal std = BigDecimal.ZERO;
			
			//调用方法求平均上数
			avg = EduEvaluatingUtils.getAverage(array);
			
			//调用方法求标准差
			std = EduEvaluatingUtils.getStandardDeviation(array);
			//判断输入的原始值在不在集合里面
			
			//判断标准差是否为0
			if(std.compareTo(BigDecimal.ZERO) == 0) {
				return BigDecimal.ZERO;
			}
			
			//differ:原始值与平均数的差
			BigDecimal differ = BigDecimal.ZERO;
			differ = x.subtract(avg);
		
			//返回Z标准分数
			return differ.divide(std, 2, RoundingMode.HALF_UP);
	}

	/**
	 *
	 * 标准Z分数
	 *
	 * @param array:分数集合
	 * @param x:原始分数
	 * @return BigDecimal
	 */
	public static BigDecimal getStandardZ_1(List<BigDecimal> array,BigDecimal x) {

		// 判空
		if (null == array || 0 == array.size()) {
			return BigDecimal.ZERO;
		}

		//avg:平均数ֵ
		BigDecimal avg = BigDecimal.ZERO;
		//std:标准差
		BigDecimal std = BigDecimal.ZERO;

		//调用方法求平均上数
		avg = EduEvaluatingUtils.getAverage(array);

		//调用方法求标准差
		std = EduEvaluatingUtils.getStandardDeviation(array);
		//判断输入的原始值在不在集合里面

		//判断标准差是否为0
		if(std.compareTo(BigDecimal.ZERO) == 0) {
			return BigDecimal.ZERO;
		}

		//differ:原始值与平均数的差
		BigDecimal differ = BigDecimal.ZERO;
		differ = x.subtract(avg);

		//返回Z标准分数
		return differ.divide(std, 3, RoundingMode.HALF_UP);
	}

	/**
	 * 
	 * 标准T分数
	 * 
	 * @param array:分数集合
	 * @param x:原始分数
	 * @return
	 */
	public static BigDecimal getStandardT(List<BigDecimal> array, BigDecimal x) {
		// 判空
		if (null == array || 0 == array.size()) {
			return BigDecimal.ZERO;
		}
		
		// z:Z标准分数
		BigDecimal z = BigDecimal.ZERO;
		// 调用方法得到Z标准分数
		z = EduEvaluatingUtils.getStandardZ_1(array, x);
		if(null != z) {
		// T标准分数求值过程中用到的常数
		BigDecimal a = new BigDecimal(50);
		BigDecimal b = new BigDecimal(10);

		// 返回T分数
		return a.add(b.multiply(z));
	}else {
		return null;
	}
	}
	/**
	 * 
	 * 全值
	 * 
	 * @param array:分数集合
	 * @return BigDecimal
	 */
	public static BigDecimal getRange(List<BigDecimal> array) {
		// 判空
		if (null == array || 0 == array.size()) {
			return BigDecimal.ZERO;
		}

		// max:集合中最大值
		BigDecimal max = array.get(0);
		// min:集合中最小值
		BigDecimal min = array.get(0);

		// 遍历求最大值和最小值ֵ
		for (BigDecimal bigDecimal : array) {
			if (max.compareTo(bigDecimal) < 0) {
				max = bigDecimal;
			}
			if (min.compareTo(bigDecimal) > 0) {
				min = bigDecimal;
			}
		}

		// 全距是集合中最大值减去最小值
		return max.subtract(min).setScale(2, RoundingMode.HALF_UP);
	}

	/**
	 * 
	 * 百分等级
	 * 
	 * @param array:分数集合
	 * @param x:原始分数
	 * @return BigDecimal
	 */
	public static BigDecimal getPercentagerank(List<BigDecimal> array, BigDecimal x) {
		// 判空
		if (null == array || 0 == array.size()) {
			return BigDecimal.ZERO;
		}
		if(array.contains(x)) {
		// 集合从大到小排序
		Collections.sort(array, Collections.reverseOrder());

		// 百分等级求值过程中的常量
		BigDecimal a = new BigDecimal(100);
		BigDecimal b = new BigDecimal(50);

		// rank:排名 （下标号+1）
		double rank = 0;
		for (BigDecimal bigDecimal : array) {
			if (x.compareTo(bigDecimal) == 0) {
				rank = array.indexOf(bigDecimal) + 1;
			}
		}

		// 返回百分等级
		return a.subtract(((a.multiply(BigDecimal.valueOf(rank))).subtract(b)).divide(BigDecimal.valueOf(array.size()),
				2, RoundingMode.HALF_UP));
		}else {
			return BigDecimal.ZERO;
		}
	}

	/**
	 * 
	 * 难度 二值记分题
	 * 
	 * @param array:分数集合
	 * @param k：答对该题人数
	 * @return BigDecimal
	 */
	public static BigDecimal getPBindary(List<BigDecimal> array, BigDecimal k) {
		//n为被测人数
		BigDecimal n =new BigDecimal(array.size()); 
		// 判空
		if (null == array || 0 == array.size() || n.compareTo(k) < 0) {
			return BigDecimal.ZERO;
		}

		// 返回二值记分题难度
		return k.divide(n, 2, RoundingMode.HALF_UP);
	}

	/**
	 * 
	 * 难度 多值记分题
	 * 
	 * @param xMax:该题的满分
	 * @param x:被试在某题目上的平均得分
	 * @return BigDecimal
	 */
	public static BigDecimal getPMultivalue(BigDecimal xMax, BigDecimal x) {
		// 判断该题满分是否为0，为0返回0
		if (xMax.compareTo(BigDecimal.ZERO) == 0 || xMax.compareTo(x) < 0) {
			return BigDecimal.ZERO;
		}

		// 返回多值记分题难度
		return x.divide(xMax, 2, RoundingMode.HALF_UP);
	}

	/**
	 * 
	 * 区分度
	 * 
	 * @param array:分数集合
	 * @return BigDecimal
	 * 包括27%
	 */
	public static BigDecimal getDegree(List<BigDecimal> array) {
		// 判空
		if (null == array || 0 == array.size()) {
			return BigDecimal.ZERO;
		}
		/*
		 * pl:低分组(总人数的27%)在题目上的得分率 ph:高分组(总人数的27%)在题目上的得分率
		 */
		BigDecimal pl = BigDecimal.ZERO;
		BigDecimal ph = BigDecimal.ZERO;
		int sum = array.size();

		// 集合从小到大排序
		Collections.sort(array);
		// 低分组
		for (int i = 0; i < Math.round(sum * 0.27); i++) {
			pl = pl.add(array.get(i));
		}

		// 集合从大到小排序
		Collections.sort(array, Collections.reverseOrder());
		// 高分组
		for (int j = 0; j < Math.round(sum * 0.27); j++) {
			ph = ph.add(array.get(j));
		}

		// 区分度为高分组得分率减去低分组得分率
		return ph.subtract(pl).setScale(2, RoundingMode.HALF_UP);
	}
	/**
	 * 个人分数等级
	 *
	 * @param totalScore:总分
	 * @param personalScore:个人原始分
	 * @return
	 */
	public static String getPersonalRatio(BigDecimal totalScore,BigDecimal personalScore) {
		/*
		 * 判断 考生原始分是否比总分大 
		 */
		if (totalScore.compareTo(personalScore) < 0 ) {
			return "0";
		}
		
		//a b c d 计算中要用的数
		BigDecimal a = BigDecimal.valueOf(0.9);
		BigDecimal b = BigDecimal.valueOf(0.8);
		BigDecimal c = BigDecimal.valueOf(0.7);
		BigDecimal d = BigDecimal.valueOf(0.6);
		
		if(personalScore.compareTo(totalScore.multiply(a)) >= 0) {
			return "高分";
		}else if (personalScore.compareTo(totalScore.multiply(b)) >= 0) {
			return "优秀";
		}else if (personalScore.compareTo(totalScore.multiply(c)) >= 0) {
			return "良好";
		}else if (personalScore.compareTo(totalScore.multiply(d)) >=0) {
			return "及格";
		}else {
			return "不及格";
		}
	}


	/**
	 * 分数等级占比
	 * 
	 * @param array:分数集合
	 * @param totalScore:总分
	 * @return
	 */
	@SuppressWarnings("unused")
	public static Map<String,BigDecimal> getRatio(List<BigDecimal> array, BigDecimal totalScore) {

		Map<String,BigDecimal> rateMap = new HashMap<>();
		// 判空
		if (null == array || 0 == array.size()) {
			return null;
		}
		// n集合长度
		BigDecimal n = BigDecimal.valueOf(array.size());

		// a b c d 计算中要用的数
		BigDecimal a = BigDecimal.valueOf(0.9);
		BigDecimal b = BigDecimal.valueOf(0.8);
		BigDecimal c = BigDecimal.valueOf(0.7);
		BigDecimal d = BigDecimal.valueOf(0.6);

		// highrateList 高分集合
		List<BigDecimal> highrateList = new ArrayList<>();
		// excellentrateList 优秀集合
		List<BigDecimal> excellentrateList = new ArrayList<>();
		// commissonrateList 良好集合
		List<BigDecimal> commissionrateList = new ArrayList<>();
		// passrateList 及格集合
		List<BigDecimal> passrateList = new ArrayList<>();
		// failurerateList 不及格集合
		List<BigDecimal> failurerateList = new ArrayList<>();
		
		//遍历array集合，将array集合中数分组
		for (BigDecimal bigDecimal : array) {
			if (bigDecimal.compareTo(totalScore.multiply(a)) >= 0) {
				highrateList.add(bigDecimal);
			} else if (bigDecimal.compareTo(totalScore.multiply(b)) >= 0) {
				excellentrateList.add(bigDecimal);
			} else if (bigDecimal.compareTo(totalScore.multiply(c)) >= 0) {
				commissionrateList.add(bigDecimal);
			} else if (bigDecimal.compareTo(totalScore.multiply(d)) >= 0) {
				passrateList.add(bigDecimal);
			} else {
				failurerateList.add(bigDecimal);
			}
		}
		
		//定义百分等级的形式
		DecimalFormat df = new DecimalFormat("0.00%");
		
		//highrate 高分率
		BigDecimal highRate = (BigDecimal.valueOf(highrateList.size())).divide(n, 4, RoundingMode.HALF_UP);
		//excellentrate 优秀率
		BigDecimal excellentRate = (BigDecimal.valueOf(excellentrateList.size())).divide(n, 4, RoundingMode.HALF_UP);
		//commissionrate 良好率
		BigDecimal commissionRate = (BigDecimal.valueOf(commissionrateList.size())).divide(n, 4, RoundingMode.HALF_UP);
		//passrate 及格率
		BigDecimal passRate = (BigDecimal.valueOf(passrateList.size())).divide(n, 4, RoundingMode.HALF_UP);
		//failurerate 不及格率
		BigDecimal failureRate = (BigDecimal.valueOf(failurerateList.size())).divide(n, 4, RoundingMode.HALF_UP);

		//返回制定形式占比率
		rateMap.put("highRate",highRate);
		rateMap.put("excellentRate",excellentRate);
		rateMap.put("commissionRate",commissionRate);
		rateMap.put("passRate",passRate);
		rateMap.put("failureRate",failureRate);

		return rateMap;
	}

}
