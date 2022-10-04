package servlet;

import dto.AuditDto;
import lombok.Data;
import pojo.Knowledge;
import pojo.Post;
import service.Impl.KnowledgeServiceImpl;
import service.KnowledgeService;
import utils.RequestUtil;
import utils.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/knowledge/audit", "/knowledge/delete","/knowledge/update",
        "/knowledge/add","/knowledge/queryArray"})
public class KnowledgeServlet extends HttpServlet {
    private KnowledgeService knowledgeService = new KnowledgeServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String courseId = req.getParameter("courseId");
        List<Knowledge> knowledges = knowledgeService.getArray(courseId);
        ResponseUtil.DoResponse(resp,knowledges);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUrl = RequestUtil.getRequestUrl(req);
        if ("/knowledge/audit".equals(requestUrl)) {//审核岗位
            AuditDto auditDto = RequestUtil.parseRequestBody(req, AuditDto.class);
            int result = knowledgeService.audit(auditDto);
            ResponseUtil.DoResponse(resp, result);
        } else if ("/knowledge/update".equals(requestUrl)) {//岗位添加
            Knowledge knowledge = RequestUtil.parseRequestBody(req, Knowledge.class);
            int result = knowledgeService.update(knowledge);
            ResponseUtil.DoResponse(resp, result);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ids = req.getParameter("ids");
        int result = knowledgeService.delete(ids);
        ResponseUtil.DoResponse(resp, result);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Knowledge knowledge = RequestUtil.parseRequestBody(req, Knowledge.class);
        int result = knowledgeService.add(knowledge);
        ResponseUtil.DoResponse(resp,result);
    }
}
