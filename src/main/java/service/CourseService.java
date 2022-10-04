package service;

import dto.AuditDto;
import pojo.Course;
import pojo.Knowledge;
import vo.CourseVo;

import java.util.List;

public interface CourseService {
    List<CourseVo> search(String courseName, String auditState);

    int audit(AuditDto auditDto);

    int delete(String ids);

    int update(Course course);

    int add(String courseName);

    List<Course> getArray();
}
