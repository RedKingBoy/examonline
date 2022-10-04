package servlet;

import dto.AuditDto;
import pojo.Course;
import pojo.Knowledge;
import service.CourseService;
import service.Impl.CourseServiceImpl;
import utils.RequestUtil;
import utils.ResponseUtil;
import vo.CourseVo;
import vo.DeptVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/course/query","/course/audit","/course/delete",
        "/course/update","/course/add","/course/array"})
public class CourseServlet extends HttpServlet {
    private CourseService courseService = new CourseServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUrl = RequestUtil.getRequestUrl(req);
        if ("/course/query".equals(requestUrl)){
            String courseName = req.getParameter("name");
            String auditState = req.getParameter("auditState");
            List<CourseVo> courseVos = courseService.search(courseName, auditState);
            ResponseUtil.DoResponse(resp,courseVos);
        }else if ("/course/array".equals(requestUrl)){//为试题管理中的查询框中的课程下拉列表项
            List<Course> courses = courseService.getArray();
            ResponseUtil.DoResponse(resp,courses);
        }

    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUrl = RequestUtil.getRequestUrl(req);
        if ("/course/audit".equals(requestUrl)){//审核课程
            AuditDto auditDto = RequestUtil.parseRequestBody(req, AuditDto.class);
            int result = courseService.audit(auditDto);
            ResponseUtil.DoResponse(resp,result);
        } else if ("/course/update".equals(requestUrl)){//修改课程信息
            Course course = RequestUtil.parseRequestBody(req, Course.class);
            int affectedRows = courseService.update(course);
            ResponseUtil.DoResponse(resp,affectedRows);
        }
    }
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ids = req.getParameter("ids");
        int result = courseService.delete(ids);
        ResponseUtil.DoResponse(resp,result);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Course course = RequestUtil.parseRequestBody(req, Course.class);
        String courseName = course.getName();
        int affectedRows = courseService.add(courseName);
        ResponseUtil.DoResponse(resp,affectedRows);
    }
}
