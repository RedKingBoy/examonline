package dao.Impl;

import dao.CourseDao;
import dto.AuditDto;
import pojo.Course;
import utils.JdbcUtil;
import utils.MultiResultHandler;
import vo.CourseVo;

import java.util.ArrayList;
import java.util.List;

public class CourseDaoImpl implements CourseDao {
    @Override
    public List<CourseVo> searchCourses(String courseName, String auditState) {
        String sql = "SELECT id,name,audit_state auditState FROM course WHERE is_active = 0";
        ArrayList<Object> params = new ArrayList<>();
        if (courseName != null && !"".equals(courseName)) {
            sql += " AND name LIKE ?";
            params.add("%" + courseName + "%");
        }
        if (auditState != null && !"".equals(auditState)) {
            sql += " AND audit_state = ?";
            params.add(auditState);
        }
        return JdbcUtil.query(new MultiResultHandler<>(CourseVo.class),sql,params.toArray());
    }

    @Override
    public int audit(AuditDto auditDto) {
        String sql = "UPDATE course SET audit_state = ? WHERE is_active = 0 AND id IN(";
        List<Integer> ids = auditDto.getIds();
        String idsStr = ids.toString();
        idsStr = idsStr.substring(1, idsStr.length() - 1);
        sql += idsStr + ")";
        return JdbcUtil.update(sql, auditDto.getAuditState());
    }

    @Override
    public int deleteCourse(String ids) {
        String sql = "UPDATE course SET is_active = 1 WHERE id IN (" + ids + ")";
        return JdbcUtil.update(sql);
    }

    @Override
    public int updateCourse(Course course) {
        String sql = "UPDATE course SET name = ? WHERE id = ?";
        return JdbcUtil.update(sql,course.getName(),course.getId());
    }

    @Override
    public int addCourse(String courseName) {
        String sql = "INSERT INTO course(name) VALUES (?)";
        return JdbcUtil.update(sql,courseName);
    }

    @Override
    public List<Course> getCoursesArray() {
        String sql = "SELECT id,name FROM course WHERE is_active = 0 AND audit_state = 1";
        return JdbcUtil.query(new MultiResultHandler<>(Course.class),sql);
    }
}
