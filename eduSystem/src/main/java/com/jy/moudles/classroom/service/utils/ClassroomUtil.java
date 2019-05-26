package com.jy.moudles.classroom.service.utils;

import com.jy.moudles.classroom.constants.ClassroomConstants;

/**
 * 教室工具类
 * 
 * @author jinxiaoxiang@jrycn.cn
 * @since 2017-12-21
 *
 */
public class ClassroomUtil {
	
	// 年级坐标
	public static final int GRADE_INDEX = 0;
	
	// 班级坐标
	public static final int CLASS_INDEX = 1;

	/**
	 * 根据班级名称转换班级数组
	 * 
	 * @param classroomName
	 * @return
	 */
	public static int[] getGradeAndClassByClassroomName(String classroomName) {
		int[] gradeAndClass = new int[2];

		String midValue = classroomName.substring(0, classroomName.lastIndexOf(")"));

		String[] midValues = midValue.split("\\(");

		if (2 == midValues.length) {
			if (ClassroomConstants.GRADE_ONE_PRIMARY_SCHOOL_CHINESE.equals(midValues[GRADE_INDEX])) {	
				gradeAndClass[GRADE_INDEX] = ClassroomConstants.GRADE_ONE_PRIMARY_SCHOOL_NUMBER;
			} else if (ClassroomConstants.GRADE_TWO_PRIMARY_SCHOOL_CHINESE.equals(midValues[GRADE_INDEX])) {
				gradeAndClass[GRADE_INDEX] = ClassroomConstants.GRADE_TWO_PRIMARY_SCHOOL_NUMBER;
			} else if (ClassroomConstants.GRADE_THREE_PRIMARY_SCHOOL_CHINESE.equals(midValues[GRADE_INDEX])) {
				gradeAndClass[GRADE_INDEX] = ClassroomConstants.GRADE_THREE_PRIMARY_SCHOOL_NUMBER;
			} else if (ClassroomConstants.GRADE_FOUR_PRIMARY_SCHOOL_CHINESE.equals(midValues[GRADE_INDEX])) {
				gradeAndClass[GRADE_INDEX] = ClassroomConstants.GRADE_FOUR_PRIMARY_SCHOOL_NUMBER;
			} else if (ClassroomConstants.GRADE_FIVE_PRIMARY_SCHOOL_CHINESE.equals(midValues[GRADE_INDEX])) {
				gradeAndClass[GRADE_INDEX] = ClassroomConstants.GRADE_FIVE_PRIMARY_SCHOOL_NUMBER;
			} else if (ClassroomConstants.GRADE_SIX_PRIMARY_SCHOOL_CHINESE.equals(midValues[GRADE_INDEX])) {
				gradeAndClass[GRADE_INDEX] = ClassroomConstants.GRADE_SIX_PRIMARY_SCHOOL_NUMBER;
			} else if (ClassroomConstants.GRADE_ONE_JUNIOR_SCHOOL_CHINESE.equals(midValues[GRADE_INDEX])) {
				gradeAndClass[GRADE_INDEX] = ClassroomConstants.GRADE_ONE_JUNIOR_SCHOOL_NUMBER;
			} else if (ClassroomConstants.GRADE_TWO_JUNIOR_SCHOOL_CHINESE.equals(midValues[GRADE_INDEX])) {
				gradeAndClass[GRADE_INDEX] = ClassroomConstants.GRADE_TWO_JUNIOR_SCHOOL_NUMBER;
			} else if (ClassroomConstants.GRADE_THREE_JUNIOR_SCHOOL_CHINESE.equals(midValues[GRADE_INDEX])) {
				gradeAndClass[GRADE_INDEX] = ClassroomConstants.GRADE_THREE_JUNIOR_SCHOOL_NUMBER;
			} else if (ClassroomConstants.GRADE_ONE_SENIOR_SCHOOL_CHINESE.equals(midValues[GRADE_INDEX])) {
				gradeAndClass[GRADE_INDEX] = ClassroomConstants.GRADE_ONE_SENIOR_SCHOOL_NUMBER;
			} else if (ClassroomConstants.GRADE_TWO_SENIOR_SCHOOL_CHINESE.equals(midValues[GRADE_INDEX])) {
				gradeAndClass[GRADE_INDEX] = ClassroomConstants.GRADE_TWO_SENIOR_SCHOOL_NUMBER;
			} else if (ClassroomConstants.GRADE_THREE_SENIOR_SCHOOL_CHINESE.equals(midValues[GRADE_INDEX])) {
				gradeAndClass[GRADE_INDEX] = ClassroomConstants.GRADE_THREE_SENIOR_SCHOOL_NUMBER;
			} else {
				gradeAndClass[GRADE_INDEX] = 0;
			}
			gradeAndClass[CLASS_INDEX] = Integer.valueOf(midValues[CLASS_INDEX]);
		}

		return gradeAndClass;
	}
}
