package servlet;

import dto.AuditDto;
import dto.ExamDispatchDto;
import dto.ExamDto;
import dto.ExamSubmitDto;
import page.PageObject;
import pojo.Exam;
import service.ExamService;
import service.Impl.ExamServiceImpl;
import utils.RequestUtil;
import utils.ResponseUtil;
import vo.ExamVo;
import vo.PaperDetailVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

//因为两个exam的存在
@WebServlet(urlPatterns = {"/exam/query","/exam/addAndUpdate","/exam/audit","/delete","/exam/dispatch",
        "/exam/start","/exam/submit"})
public class ExamServlet extends HttpServlet {
    private ExamService examService = new ExamServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUrl = RequestUtil.getRequestUrl(req);
        if ("/query".equals(requestUrl)){
            ExamDto examDto = RequestUtil.parseRequestParams(req, ExamDto.class);
            PageObject<ExamVo> exams = examService.query(examDto);
            ResponseUtil.DoResponse(resp,exams);
        }else if ("/start".equals(requestUrl)){
            String examId = req.getParameter("examId");
            String username = (String) req.getSession().getAttribute("user");
            PaperDetailVo exam = examService.doExam(examId,username);
            ResponseUtil.DoResponse(resp,exam);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUrl = RequestUtil.getRequestUrl(req);
        if ("/addAndUpdate".equals(requestUrl)){
            Exam exam = RequestUtil.parseRequestBody(req, Exam.class);
            String username = (String) req.getSession().getAttribute("user");
            exam.setCreatedUser(username);
            int result = examService.add(exam);
            ResponseUtil.DoResponse(resp,result);
        }else if ("/dispatch".equals(requestUrl)){
            ExamDispatchDto examDispatchDto = RequestUtil.parseRequestBody(req, ExamDispatchDto.class);
            int result = examService.dispatch(examDispatchDto);
            ResponseUtil.DoResponse(resp,result);
        }else if ("/submit".equals(requestUrl)){
            ExamSubmitDto examSubmitDto = RequestUtil.parseRequestBody(req, ExamSubmitDto.class);
            String username = (String) req.getSession().getAttribute("user");
            examSubmitDto.setUsername(username);
            int result = examService.submitExam(examSubmitDto);
            ResponseUtil.DoResponse(resp,result);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUrl = RequestUtil.getRequestUrl(req);
        if ("/addAndUpdate".equals(requestUrl)){//因为传过来的请求有两个exam都会替换为空
            Exam exam = RequestUtil.parseRequestBody(req, Exam.class);
            int result = examService.update(exam);
            ResponseUtil.DoResponse(resp,result);
        }else if ("/audit".equals(requestUrl)){//因为传过来的请求有两个exam都会替换为空
            AuditDto auditDto = RequestUtil.parseRequestBody(req, AuditDto.class);
            int result = examService.audit(auditDto);
            ResponseUtil.DoResponse(resp,result);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ids = req.getParameter("ids");
        int result = examService.delete(ids);
        ResponseUtil.DoResponse(resp,result);
    }
}
