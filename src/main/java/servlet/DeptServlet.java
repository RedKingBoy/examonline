package servlet;

import dto.AuditDto;
import pojo.Department;
import service.DeptService;
import service.Impl.DeptServiceImpl;
import utils.RequestUtil;
import utils.ResponseUtil;
import vo.DeptVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/dept/query", "/department/query","/department/audit","/department/delete",
        "/department/update","/department/add"})
public class DeptServlet extends HttpServlet {
    private DeptService deptService = new DeptServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUrl = RequestUtil.getRequestUrl(req);
        if ("/dept/query".equals(requestUrl)) {//注册时下拉列表的部门查询
            List<Department> departments = deptService.queryAll();
            ResponseUtil.DoResponse(resp, departments);
        } else if ("/department/query".equals(requestUrl)) {//部门管理的查询
            search(req, resp);
        }

    }

    private void search(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String deptName = req.getParameter("deptName");
        String auditState = req.getParameter("auditState");
        List<DeptVo> result = deptService.search(deptName, auditState);
        ResponseUtil.DoResponse(resp,result);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUrl = RequestUtil.getRequestUrl(req);
        if ("/department/audit".equals(requestUrl)){//审核部门
            AuditDto auditDto = RequestUtil.parseRequestBody(req, AuditDto.class);
            int result = deptService.audit(auditDto);
            ResponseUtil.DoResponse(resp,result);
        }else if ("/department/update".equals(requestUrl)){//修改部门信息
            DeptVo deptVo = RequestUtil.parseRequestBody(req, DeptVo.class);
            int affectedRows = deptService.update(deptVo);
            ResponseUtil.DoResponse(resp,affectedRows);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ids = req.getParameter("ids");
        int result = deptService.delete(ids);
        ResponseUtil.DoResponse(resp,result);
    }

    @Override//添加部门
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DeptVo deptVo = RequestUtil.parseRequestBody(req, DeptVo.class);
        String deptName = deptVo.getName();
        int affectedRows = deptService.add(deptName);
        ResponseUtil.DoResponse(resp,affectedRows);
    }
}
