package servlet;

import dto.AssignMenuDto;
import dto.AuditDto;
import pojo.Role;
import service.Impl.RoleServiceImpl;
import service.RoleService;
import utils.RequestUtil;
import utils.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/role/search","/role/delete","/role/audit","/role/addAndUpdate","/role/assignMenus"})
public class RoleServlet extends HttpServlet {
    private RoleService roleService = new RoleServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchInfo = req.getParameter("searchInfo");
        String auditState = req.getParameter("auditState");
        String isActive = req.getParameter("isActive");
        List<Role> roles = roleService.getRoles(searchInfo,auditState,isActive);
        ResponseUtil.DoResponse(resp,roles);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ids = req.getParameter("ids");
        int result = roleService.delete(ids);
        ResponseUtil.DoResponse(resp,result);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUrl = RequestUtil.getRequestUrl(req);
        if ("/role/audit".equals(requestUrl)){
            auditRole(req,resp);
        }else if ("/role/addAndUpdate".equals(requestUrl)){
            addAndUpdate(req,resp);
        }else if ("/role/assignMenus".equals(requestUrl)){
            assignMenus(req,resp);
        }

    }

    private void assignMenus(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        AssignMenuDto assignMenuDto = RequestUtil.parseRequestBody(req, AssignMenuDto.class);
        int result = roleService.assignMenus(assignMenuDto);
        ResponseUtil.DoResponse(resp,result);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Role role = RequestUtil.parseRequestBody(req, Role.class);
        String username = (String) req.getSession().getAttribute("user");
        role.setCreateUser(username);
        int add = roleService.add(role);
        ResponseUtil.DoResponse(resp,add);
    }
    public void auditRole(HttpServletRequest request,HttpServletResponse response) throws IOException {
        AuditDto auditDto = RequestUtil.parseRequestBody(request, AuditDto.class);
        int audit = roleService.audit(auditDto);
        ResponseUtil.DoResponse(response,audit);
    }
    public void addAndUpdate(HttpServletRequest request,HttpServletResponse response) throws IOException {
        Role role = RequestUtil.parseRequestBody(request, Role.class);
        int result = roleService.update(role);
        ResponseUtil.DoResponse(response,result);
    }
}
