package com.jy.moudles.classroom.utils;

import com.jy.moudles.classroom.constants.ClassroomConstants;

/**
 * 教室工具类
 * 
 * @author jinxiaoxiang@jrycn.cn
 * @since 2017-12-21
 *
 */
public class ClassroomUtil {

	/**
	 * 年级坐标
	 */
	public static final int GRADE_INDEX = 0;

	/**
	 * 班级坐标
	 */
	public static final int CLASS_INDEX = 1;

	/**
	 * 教室信息数组长度
	 */
	private static final int CLASSROOM_INFO_ARRAY_LENGTH = 2;

	/**
	 * 根据班级名称转换班级数组
	 * 
	 * @param classroomName
	 * @return
	 */
	public static int[] getGradeAndClassByClassroomName(String classroomName) {
		int[] gradeAndClass = new int[CLASSROOM_INFO_ARRAY_LENGTH];

		String midValue = classroomName.substring(0, classroomName.lastIndexOf(")"));

		String[] midValues = midValue.split("\\(");

		if (CLASSROOM_INFO_ARRAY_LENGTH == midValues.length) {
			gradeAndClass[GRADE_INDEX] = getIntGradeCodeByGradeCodeCN(midValues[GRADE_INDEX]);

			gradeAndClass[CLASS_INDEX] = Integer.valueOf(midValues[CLASS_INDEX]);
		}

		return gradeAndClass;
	}

	/**
	 * 根据文字年级CODE获取数字年级CODE
	 *
	 * @param gradeCode
	 * @return
	 */
	public static int getIntGradeCodeByGradeCodeCN(String gradeCode) {

		int intGradeCode = 0;

		if (ClassroomConstants.GRADE_ONE_PRIMARY_SCHOOL_CHINESE.equals(gradeCode)) {
			intGradeCode = ClassroomConstants.GRADE_ONE_PRIMARY_SCHOOL_NUMBER;
		} else if (ClassroomConstants.GRADE_TWO_PRIMARY_SCHOOL_CHINESE.equals(gradeCode)) {
			intGradeCode = ClassroomConstants.GRADE_TWO_PRIMARY_SCHOOL_NUMBER;
		} else if (ClassroomConstants.GRADE_THREE_PRIMARY_SCHOOL_CHINESE.equals(gradeCode)) {
			intGradeCode = ClassroomConstants.GRADE_THREE_PRIMARY_SCHOOL_NUMBER;
		} else if (ClassroomConstants.GRADE_FOUR_PRIMARY_SCHOOL_CHINESE.equals(gradeCode)) {
			intGradeCode = ClassroomConstants.GRADE_FOUR_PRIMARY_SCHOOL_NUMBER;
		} else if (ClassroomConstants.GRADE_FIVE_PRIMARY_SCHOOL_CHINESE.equals(gradeCode)) {
			intGradeCode = ClassroomConstants.GRADE_FIVE_PRIMARY_SCHOOL_NUMBER;
		} else if (ClassroomConstants.GRADE_SIX_PRIMARY_SCHOOL_CHINESE.equals(gradeCode)) {
			intGradeCode = ClassroomConstants.GRADE_SIX_PRIMARY_SCHOOL_NUMBER;
		} else if (ClassroomConstants.GRADE_ONE_JUNIOR_SCHOOL_CHINESE.equals(gradeCode)) {
			intGradeCode = ClassroomConstants.GRADE_ONE_JUNIOR_SCHOOL_NUMBER;
		} else if (ClassroomConstants.GRADE_TWO_JUNIOR_SCHOOL_CHINESE.equals(gradeCode)) {
			intGradeCode = ClassroomConstants.GRADE_TWO_JUNIOR_SCHOOL_NUMBER;
		} else if (ClassroomConstants.GRADE_THREE_JUNIOR_SCHOOL_CHINESE.equals(gradeCode)) {
			intGradeCode = ClassroomConstants.GRADE_THREE_JUNIOR_SCHOOL_NUMBER;
		} else if (ClassroomConstants.GRADE_ONE_SENIOR_SCHOOL_CHINESE.equals(gradeCode)) {
			intGradeCode = ClassroomConstants.GRADE_ONE_SENIOR_SCHOOL_NUMBER;
		} else if (ClassroomConstants.GRADE_TWO_SENIOR_SCHOOL_CHINESE.equals(gradeCode)) {
			intGradeCode = ClassroomConstants.GRADE_TWO_SENIOR_SCHOOL_NUMBER;
		} else if (ClassroomConstants.GRADE_THREE_SENIOR_SCHOOL_CHINESE.equals(gradeCode)) {
			intGradeCode = ClassroomConstants.GRADE_THREE_SENIOR_SCHOOL_NUMBER;
		}

		return intGradeCode;
	}

	/**
	 * 根据数字年级CODE获取中文年级CODE
	 *
	 * @param gradeCode
	 * @return
	 */
	public static String getgradeCodeCNByIntGradeCode(Integer gradeCode) {
		String gradeCodeCN = "";
		switch (gradeCode) {
			case ClassroomConstants.GRADE_ONE_PRIMARY_SCHOOL_NUMBER:
				gradeCodeCN = ClassroomConstants.GRADE_ONE_PRIMARY_SCHOOL_CHINESE;
				break;
			case ClassroomConstants.GRADE_TWO_PRIMARY_SCHOOL_NUMBER:
				gradeCodeCN = ClassroomConstants.GRADE_TWO_PRIMARY_SCHOOL_CHINESE;
				break;
			case ClassroomConstants.GRADE_THREE_PRIMARY_SCHOOL_NUMBER:
				gradeCodeCN = ClassroomConstants.GRADE_THREE_PRIMARY_SCHOOL_CHINESE;
				break;
			case ClassroomConstants.GRADE_FOUR_PRIMARY_SCHOOL_NUMBER:
				gradeCodeCN = ClassroomConstants.GRADE_FOUR_PRIMARY_SCHOOL_CHINESE;
				break;
			case ClassroomConstants.GRADE_FIVE_PRIMARY_SCHOOL_NUMBER:
				gradeCodeCN = ClassroomConstants.GRADE_FIVE_PRIMARY_SCHOOL_CHINESE;
				break;
			case ClassroomConstants.GRADE_SIX_PRIMARY_SCHOOL_NUMBER:
				gradeCodeCN = ClassroomConstants.GRADE_SIX_PRIMARY_SCHOOL_CHINESE;
				break;
			case ClassroomConstants.GRADE_ONE_JUNIOR_SCHOOL_NUMBER:
				gradeCodeCN = ClassroomConstants.GRADE_ONE_JUNIOR_SCHOOL_CHINESE;
				break;
			case ClassroomConstants.GRADE_TWO_JUNIOR_SCHOOL_NUMBER:
				gradeCodeCN = ClassroomConstants.GRADE_TWO_JUNIOR_SCHOOL_CHINESE;
				break;
			case ClassroomConstants.GRADE_THREE_JUNIOR_SCHOOL_NUMBER:
				gradeCodeCN = ClassroomConstants.GRADE_THREE_JUNIOR_SCHOOL_CHINESE;
				break;
			case ClassroomConstants.GRADE_ONE_SENIOR_SCHOOL_NUMBER:
				gradeCodeCN = ClassroomConstants.GRADE_ONE_SENIOR_SCHOOL_CHINESE;
				break;
			case ClassroomConstants.GRADE_TWO_SENIOR_SCHOOL_NUMBER:
				gradeCodeCN = ClassroomConstants.GRADE_TWO_SENIOR_SCHOOL_CHINESE;
				break;
			case ClassroomConstants.GRADE_THREE_SENIOR_SCHOOL_NUMBER:
				gradeCodeCN = ClassroomConstants.GRADE_THREE_SENIOR_SCHOOL_CHINESE;
				break;
			default:
				break;
		}
		return gradeCodeCN;
	}
}
