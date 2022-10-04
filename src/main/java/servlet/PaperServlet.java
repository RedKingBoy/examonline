package servlet;

import dto.AuditDto;
import page.PageObject;
import pojo.Paper;
import service.Impl.PaperServiceImpl;
import service.PaperService;
import utils.RequestUtil;
import utils.ResponseUtil;
import vo.PaperDetailVo;
import vo.PaperVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/paper/query","/paper/queryPapers","/paper/audit","/paper/delete",
        "/paper/addAndUpdate","/paper/view"})
public class PaperServlet extends HttpServlet {
    private PaperService paperService = new PaperServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUrl = RequestUtil.getRequestUrl(req);
        if ("/paper/query".equals(requestUrl)){
            Paper paper = RequestUtil.parseRequestParams(req, Paper.class);
            PageObject<PaperVo> papers = paperService.search(paper);
            ResponseUtil.DoResponse(resp,papers);
        }else if ("/paper/queryPapers".equals(requestUrl)){//考试管理添加时查询试卷
            String query = req.getParameter("query");
            List<PaperVo> papers = paperService.queryPapers(query);
            ResponseUtil.DoResponse(resp,papers);
        }else if ("/paper/view".equals(requestUrl)){
            String paperId = req.getParameter("paperId");
            PaperDetailVo paper = paperService.queryConfigs(paperId);
            ResponseUtil.DoResponse(resp,paper);
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUrl = RequestUtil.getRequestUrl(req);
        if ("/paper/audit".equals(requestUrl)){
            AuditDto auditDto = RequestUtil.parseRequestBody(req, AuditDto.class);
            int result = paperService.audit(auditDto);
            ResponseUtil.DoResponse(resp,result);
        }else if ("/paper/addAndUpdate".equals(requestUrl)){
            Paper paper = RequestUtil.parseRequestBody(req, Paper.class);
            int result = paperService.update(paper);
            ResponseUtil.DoResponse(resp,result);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ids = req.getParameter("ids");
        int result = paperService.delete(ids);
        ResponseUtil.DoResponse(resp,result);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Paper paper = RequestUtil.parseRequestBody(req, Paper.class);
        String username = (String) req.getSession().getAttribute("user");
        paper.setCreatedUser(username);
        int result = paperService.add(paper);
        ResponseUtil.DoResponse(resp,result);
    }
}
