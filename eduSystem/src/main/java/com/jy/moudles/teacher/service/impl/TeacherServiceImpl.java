package com.jy.moudles.teacher.service.impl;

import com.jy.common.excelUtil.ExportExcel;
import com.jy.common.persistence.interceptor.LoginInterceptor;
import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.school.dao.SchoolDao;
import com.jy.moudles.school.entity.School;
import com.jy.moudles.teacher.dao.TeacherDao;
import com.jy.moudles.teacher.entity.Teacher;
import com.jy.moudles.teacher.service.TeacherService;
import com.jy.moudles.user.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * teacher业务实现类
 * 创建人：1
 * 创建时间：2017-11-30
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherDao teacherdao;

    @Autowired
    private SchoolDao schoolDao;

    @Override
    public void insertTeacher(User currentUser, Teacher teacher) {
        teacher.setId(UUIDUtil.get32UUID());


        String[] classArray = teacher.getClassArray();
        String[] subjectArray = teacher.getSubjectArray();

        String classroom = "";
        String subject = "";

        if (null != classArray && 0 < classArray.length) {
            classroom = StringUtils.join(classArray, ",");
        }

        if (null != subjectArray && 0 < subjectArray.length) {
            subject = StringUtils.join(subjectArray, ",");
        }
        teacher.setClassroom(classroom);
        teacher.setSubject(subject);

        teacher.setOrgId(currentUser.getOrgId());

        teacher.setCreateUser(currentUser.getUserName());
        teacher.setCreateDate(new Date());
        teacher.setUpdateUser(currentUser.getUserName());
        teacher.setUpdateDate(new Date());

        School school = schoolDao.getSchoolByOrgId(currentUser.getOrgId());

        if (null != school) {
            teacher.setSchoolId(school.getId());
        }

        teacherdao.insertTeacher(teacher);
    }

    @Override
    public void updateTeacher(Teacher teacher) {
        teacherdao.updateTeacher(teacher);
    }

    @Override
    public Teacher getTeacherById(String id) {
        return teacherdao.getTeacherById(id);
    }

    @Override
    public List<Teacher> queryTeachersFilter(Map<String, Object> filter) {
        return teacherdao.queryTeachersFilter(filter);
    }

    @Override
    public void deleteTeacherById(String id) {
        teacherdao.deleteTeacherById(id);
    }

    @Override
    public void deleteTeachers(List<String> ids) {
        teacherdao.deleteTeachers(ids);
    }

    @Override
    public void insertTeacherListBatch(List<ArrayList<String>> lists) {

        Teacher teacher;
        User user = LoginInterceptor.getCurrentUser();

        School school = schoolDao.getSchoolByOrgId(user.getOrgId());
        List<Teacher> teacherList = new ArrayList<>();
        for (ArrayList<String> list : lists) {
            teacher = new Teacher();

            teacher.setTeacherName(list.get(1));

            if (StringUtils.isNotEmpty(list.get(2))) {
                if ("男".equals(list.get(2))) {
                    teacher.setTeacherSex(1);
                }
                if ("女".equals(list.get(2))) {
                    teacher.setTeacherSex(0);
                }
            }
            teacher.setTeacherAge(Integer.valueOf(list.get(3)));

            teacher.setClassroom(list.get(4));

            teacher.setTeacherMobile(list.get(5));


            teacher.setTeacherDuty(list.get(6));

            teacher.setSubject(list.get(7));

            teacher.setOrgId(user.getOrgId());
            teacher.setSchoolId(school.getId());
            teacher.setCreateUser(user.getUserName());
            teacher.setCreateDate(new Date());
            teacher.setUpdateUser(user.getUpdateUser());
            teacher.setUpdateDate(new Date());
            teacher.setId(UUIDUtil.get32UUID());
            teacherList.add(teacher);
        }
        teacherdao.insertTeacherListBatch(teacherList);
    }

    @Override
    public void getTeacherLists(HttpServletResponse response, Map<String, Object> filter) {
        List<Teacher> teacherList = teacherdao.queryTeachersFilter(filter);
        ExportExcel exportExcel = new ExportExcel("老师名单列表的标题", Teacher.class);
        List<ArrayList<Object>> excelData = new ArrayList<>();
        for (Teacher teacher : teacherList) {
            for (ArrayList<Object> excelDetail : excelData) {
                excelDetail.add(0, teacher.getTeacherName());
                excelDetail.add(2, teacher.getTeacherAge());
                excelDetail.add(4, teacher.getTeacherMobile());
                excelDetail.add(5, teacher.getTeacherDuty());
//				excelDetail.add(6, teacher.getTeacherJobTitle());
                excelDetail.add(3, teacher.getClassroom());
                excelDetail.add(6, teacher.getSubject());
//				excelDetail.add(9, teacher.getRemark());
                if (0 == teacher.getTeacherSex()) {
                    excelDetail.add(1, "女");
                } else {
                    excelDetail.add(1, "男");
                }
            }
        }

        exportExcel.setDataList(excelData);
        try {
            exportExcel.write(response, "teacherList.xlsx");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

