package com.jy.moudles.student.service.impl;

import com.jy.common.excelUtil.ExportExcel;
import com.jy.common.persistence.interceptor.LoginInterceptor;
import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.classroom.dao.ClassroomDao;
import com.jy.moudles.classroom.entity.Classroom;
import com.jy.moudles.school.dao.SchoolDao;
import com.jy.moudles.school.entity.School;
import com.jy.moudles.score.entity.Score;
import com.jy.moudles.student.dao.StudentDao;
import com.jy.moudles.student.entity.Student;
import com.jy.moudles.student.service.StudentService;
import com.jy.moudles.user.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * student业务实现类
 * 创建人：1
 * 创建时间：2017-11-29
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao studentdao;
    @Autowired
    private ClassroomDao classroomDao;
    @Autowired
    private SchoolDao schoolDao;

    @Override
    public void insertStudent(Student student) {
        student.setId(UUIDUtil.get32UUID());
        studentdao.insertStudent(student);
    }

    @Override
    public void updateStudent(Student student) {
        studentdao.updateStudent(student);
    }

    @Override
    public Student getStudentById(String id) {
        return studentdao.getStudentById(id);
    }

    @Override
    public Student getStudentByIdNew(String id) {
        return studentdao.getStudentByIdNew(id);
    }

    @Override
    public Student getStudentByStuNo(String sno, String orgId) {
        return studentdao.getStudentByStuNo(sno, orgId);
    }

    @Override
    public List<Student> queryStudentsFilter(Map<String, Object> filter) {
        return studentdao.queryStudentsFilter(filter);
    }

    @Override
    public void deleteStudentById(String id) {
        studentdao.deleteStudentById(id);
    }

    @Override
    public void deleteStudents(List<String> ids) {
        studentdao.deleteStudents(ids);
    }

    @Override
    public void insertStudents(List<ArrayList<String>> students) {

        User user = LoginInterceptor.getCurrentUser();
        Map<String, Object> filter = new HashMap<>();
        filter.put("currentOrg", user.getOrgId());
        List<Student> studentList = studentdao.queryStudentsFilter(filter);
        School school = schoolDao.getSchoolByOrgId(user.getOrgId());
        Set<ArrayList<String>> set = new HashSet<>();
        for (Student stu : studentList) {
            for (ArrayList<String> stu1 : students) {
                if (!stu.getStudentNo().equals(stu1.get(2))) {
                    set.add(stu1);
                }
            }
        }
        studentList.clear();
        for (ArrayList<String> arrayList : set) {
            Student student = new Student();
            String grade;
            String classroom;
            String classroomName;
            if (StringUtils.isNotEmpty(arrayList.get(0))) {
                student.setStudentName(arrayList.get(0));
            } else {
                break;
            }
            if (StringUtils.isNotEmpty(arrayList.get(1))) {
                if ("男".equals(arrayList.get(1))) {
                    student.setStudentSex(1);
                }
                if ("女".equals(arrayList.get(1))) {
                    student.setStudentSex(0);
                }
            } else {
                break;
            }
            if (StringUtils.isNotEmpty(arrayList.get(2))) {
                student.setStudentNo(arrayList.get(2));
            } else {
                break;
            }
            if (StringUtils.isNotEmpty(arrayList.get(3))) {
                student.setStudentAge(Integer.valueOf(arrayList.get(3)));
            } else {
                break;
            }
            if (StringUtils.isNotEmpty(arrayList.get(4))) {
                if (StringUtils.isNotEmpty(arrayList.get(5))) {
                    grade = arrayList.get(4);
                    classroom = arrayList.get(5);
                    classroomName = grade + "(" + classroom + ")班";
                    Classroom classroomByName = classroomDao.getClassroomByName(classroomName, user.getOrgId());
                    student.setClassroomId(classroomByName.getId());
                }
            } else {
                break;
            }

            if (StringUtils.isNotEmpty(arrayList.get(6))) {
                student.setStudentContact(arrayList.get(6));
            } else {
                break;
            }
            if (StringUtils.isNotEmpty(arrayList.get(7))) {
                student.setStudentContactMobile(arrayList.get(7));
            } else {
                break;
            }
            student.setSchoolId(school.getId());
            student.setOrgId(user.getOrgId());
            student.setCreateUser(user.getUserName());
            student.setCreateDate(new Date());
            student.setUpdateUser(user.getCreateUser());
            student.setUpdateDate(new Date());
            student.setId(UUIDUtil.get32UUID());
            studentList.add(student);

        }
        studentdao.insertStudents(studentList);

    }

    @Override
    public void getStudentLists(HttpServletResponse response, Map<String, Object> filter) {
        List<Student> students = studentdao.queryStudentsFilter(filter);
        ExportExcel excel = new ExportExcel("学生名单列表", Student.class);
        excel.setDataList(students);
        try {
            excel.write(response, "Student.xlsx");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	@Override
	public void insertStudentsNew(List<Student> students) {
		studentdao.insertStudents(students);
	}

	@Override
	public void insertScores(List<Score> score) {
		studentdao.insertScores(score);
	}

}

