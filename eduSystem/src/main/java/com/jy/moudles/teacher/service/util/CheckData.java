package com.jy.moudles.teacher.service.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huang zhong liu
 * @create 2018-02-09
 **/
public class CheckData {
    /**
     * 表单去重
     *
     * @param teacherLists
     * @param check
     * @return
     */
    public static Map<String, List<ArrayList<String>>> handleData(List<ArrayList<String>> teacherLists, Integer check) {

        Map<String, Object> map = new HashMap<>();

        Map<String, Object> errorMap = new HashMap<>();

        List<Integer> errorList = new ArrayList<>();
        for (int i = 0; i < teacherLists.size(); i++) {

            if (!map.containsKey(teacherLists.get(i).get(check))) {
                map.put(teacherLists.get(i).get(check) + "", i);
            } else {
                errorList.add(i);
                if (!errorMap.containsKey(teacherLists.get(i).get(check))) {
                    errorMap.put(teacherLists.get(i).get(check) + "", map.get(teacherLists.get(i).get(check)));
                }
            }
        }

        List<ArrayList<String>> errorParam = new ArrayList<>();
//		List<String> resultParam = new ArrayList<>();
//        System.out.println("errorList-----------:"+errorList);
        for (Integer integer : errorList) {
            errorParam.add(teacherLists.get(integer));
        }

        // 遍历方法四 treemap keySet()遍历
        for (String str : errorMap.keySet()) {
            errorParam.add(teacherLists.get(Integer.parseInt(errorMap.get(str).toString())));
            map.remove(str);
        }

        List<ArrayList<String>> resultParam = new ArrayList<>();
        for (String str : map.keySet()) {
            resultParam.add(teacherLists.get(Integer.parseInt(map.get(str).toString())));
        }
//
//		System.out.println("list------------:"+list);
//        System.out.println("errorParam------------:" + errorParam);//错误项
//        System.out.println("resultParam------------:" + resultParam);

        Map<String, List<ArrayList<String>>> param = new HashMap<>();
        param.put("rightParam", resultParam);
        param.put("errorParam", errorParam);
        return param;
    }

}
