package service.Impl;

import dao.CourseDao;
import dao.Impl.CourseDaoImpl;
import dao.Impl.KnowledgeDaoImpl;
import dao.KnowledgeDao;
import dto.AuditDto;
import pojo.Course;
import pojo.Knowledge;
import service.CourseService;
import vo.CourseVo;

import java.util.List;

public class CourseServiceImpl implements CourseService {
    private CourseDao courseDao = new CourseDaoImpl();
    private KnowledgeDao knowledgeDao = new KnowledgeDaoImpl();
    @Override
    public List<CourseVo> search(String courseName, String auditState) {
        List<CourseVo> courseVos = courseDao.searchCourses(courseName,auditState);
        for (CourseVo courseVo:courseVos){
            List<Knowledge> knowledges = knowledgeDao.getKnowledgeListByCourseId(courseVo.getId());
            courseVo.setKnowledges(knowledges);
        }
        return courseVos;
    }

    @Override
    public int audit(AuditDto auditDto) {
        return courseDao.audit(auditDto);
    }

    @Override
    public int delete(String ids) {
        knowledgeDao.deleteCourseKnow(ids);
        return courseDao.deleteCourse(ids);
    }

    @Override
    public int update(Course course) {
        return courseDao.updateCourse(course);
    }

    @Override
    public int add(String courseName) {
        return courseDao.addCourse(courseName);
    }

    @Override
    public List<Course> getArray() {
        return courseDao.getCoursesArray();
    }

}
