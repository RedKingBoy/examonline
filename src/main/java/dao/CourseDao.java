package dao;

import dto.AuditDto;
import pojo.Course;
import vo.CourseVo;

import java.util.List;

public interface CourseDao {
    List<CourseVo> searchCourses(String courseName, String auditState);

    int audit(AuditDto auditDto);

    int deleteCourse(String ids);

    int updateCourse(Course course);

    int addCourse(String courseName);

    List<Course> getCoursesArray();
}
