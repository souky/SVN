package com.jy.moudles.teacher.service.util;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhang chao
 * @create 2018-02-08
 **/
public class Util {
    public static final String SPACE = "space";
    public static final String MAN = "男";
    public static final String WOMAN = "女";
    /**
     * 类型区分 0 老师 1 学生
     */
    public static final String TEACHER = "0";
    public static final String STUDENT = "1";

    public static final Integer STUDENTSIZE = 8;
    public static final Integer TEACHERSIZE = 7;
    /**
     * 导入区分 5 老师手机号所在列 3 学生学号所在列
     */
    public static final Integer TEACHERMOBILE = 5;
    public static final Integer STUDENTNO = 3;

    /**
     * 该老师教授的班级科目存在另一老师教授
     *
     * @param subject
     * @param classes
     * @param subjects
     * @param classroom
     * @return
     */
    public static boolean checkTeacherSub(String[] subject, String[] classes, String subjects, String classroom) {
        for (String sub : subject) {
            if (subjects != null) {
                if (subjects.contains(sub)) {
                    for (String cla : classes) {
                        if (classroom.contains(cla)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 校验导入表单
     * 姓名 性别 班级 科目
     *
     * @param param
     * @param
     * @param
     * @param rule
     * @return
     * @throws IOException
     */
    @SafeVarargs
    public static Map<String, List<ArrayList<String>>> check(List<ArrayList<String>> param, String rule, List<String>... items) throws IOException {
        Map<String, List<ArrayList<String>>> resultParam = new HashMap<>();
        List<ArrayList<String>> errorData = new ArrayList<>();
        List<ArrayList<String>> trueData = new ArrayList<>();
        //检验空
        List<ArrayList<String>> arrayLists = param;
        String type = arrayLists.get(0).get(0);
        //类型区分  老师和学生
        if (TEACHER.equals(type)) {
            for (ArrayList<String> list : param) {
                boolean bol = false;
                for (int i = 0; i < list.size(); i++) {
                    if (StringUtils.isBlank(list.get(i))) {
                        errorData.add(list);
                        bol = true;
                    }
                }
                if (bol) {
                    continue;
                }
                //检验姓名
                if (!list.get(1).matches(rule)) {
                    errorData.add(list);
                    continue;
                }
                //检验性别
                if (!MAN.equals(list.get(2)) && !WOMAN.equals(list.get(2))) {
                    errorData.add(list);
                    continue;
                }
                //检验班级
                String[] classroom = list.get(4).split(",");
                for (String cla : classroom) {
                    if (!items[0].contains(cla)) {
                        errorData.add(list);
                    }
                }
                //检验学科
                String[] subjects = list.get(7).split(",");
                for (String sub : subjects) {
                    if (!items[1].contains(sub)) {
                        errorData.add(list);
                    }
                }
                trueData.add(list);
            }       //学生类型
        } else if (STUDENT.equals(type)) {
            for (ArrayList<String> list : param) {
                boolean bol = false;
                //判空
                for (int i = 0; i < list.size(); i++) {
                    if (StringUtils.isBlank(list.get(i))) {
                        errorData.add(list);
                        bol = true;
                    }
                }
                if (bol) {
                    continue;
                }
                //检验姓名
                if (!list.get(1).matches(rule)) {
                    errorData.add(list);
                    continue;
                }
                //检验性别
                if (!MAN.equals(list.get(2)) && !WOMAN.equals(list.get(2))) {
                    errorData.add(list);
                    continue;
                }
                //检验班级
                String classroom;
                classroom = list.get(5) + "(" + list.get(6) + ")班";
                if (!items[0].contains(classroom)) {
                    errorData.add(list);
                }
                trueData.add(list);
            }
        }
        resultParam.put("error", errorData);
        resultParam.put("true", trueData);
        return resultParam;
    }
}

