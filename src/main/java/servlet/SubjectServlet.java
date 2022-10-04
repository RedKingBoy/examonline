package servlet;

import dto.AuditDto;
import dto.SubjectDto;
import page.PageObject;
import pojo.Subject;
import service.Impl.SubjectServiceImpl;
import service.SubjectService;
import utils.RequestUtil;
import utils.ResponseUtil;
import vo.SubjectVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/subject/add","/subject/query","/subject/update","/subject/audit","/subject/delete"})
public class SubjectServlet extends HttpServlet {
    private SubjectService subjectService = new SubjectServiceImpl();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SubjectDto subjectDto = RequestUtil.parseRequestBody(req, SubjectDto.class);
        String username = (String) req.getSession().getAttribute("user");//获得试题创建人姓名
        subjectDto.setCreatedUser(username);
        int result = subjectService.add(subjectDto);
        ResponseUtil.DoResponse(resp,result);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Subject subject = RequestUtil.parseRequestParams(req, Subject.class);
        PageObject<SubjectVo> subjects = subjectService.query(subject);
        ResponseUtil.DoResponse(resp,subjects);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUrl = RequestUtil.getRequestUrl(req);
        if ("/subject/update".equals(requestUrl)){
            SubjectDto subjectDto = RequestUtil.parseRequestBody(req, SubjectDto.class);
            int result = subjectService.update(subjectDto);
            ResponseUtil.DoResponse(resp,result);
        }else {
            AuditDto auditDto = RequestUtil.parseRequestBody(req, AuditDto.class);
            int result = subjectService.audit(auditDto);
            ResponseUtil.DoResponse(resp,result);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        int result = subjectService.delete(id);
        ResponseUtil.DoResponse(resp,result);
    }
}
