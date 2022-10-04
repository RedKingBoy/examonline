package servlet;

import dto.AuditDto;
import pojo.Post;
import service.Impl.PostServiceImpl;
import service.PostService;
import utils.RequestUtil;
import utils.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/post/query","/post/audit","/post/delete","/post/update","/post/add"})
public class PostServlet extends HttpServlet {
    private PostService postService = new PostServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String deptId = req.getParameter("deptId");
        List<Post> posts = postService.queryByDept(deptId);
        ResponseUtil.DoResponse(resp,posts);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUrl = RequestUtil.getRequestUrl(req);
        if ("/post/audit".equals(requestUrl)){//审核岗位
            AuditDto auditDto = RequestUtil.parseRequestBody(req, AuditDto.class);
            int result = postService.audit(auditDto);
            ResponseUtil.DoResponse(resp,result);
        }else if ("/post/update".equals(requestUrl)){//岗位添加
            Post post = RequestUtil.parseRequestBody(req, Post.class);
            int result = postService.update(post);
            ResponseUtil.DoResponse(resp,result);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ids = req.getParameter("ids");
        int result = postService.delete(ids);
        ResponseUtil.DoResponse(resp,result);
    }

    @Override//添加部门
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Post post = RequestUtil.parseRequestBody(req, Post.class);
        int result = postService.add(post);
        ResponseUtil.DoResponse(resp,result);
    }
}
