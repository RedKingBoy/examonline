package servlet;

import com.alibaba.fastjson.JSONObject;
import service.Impl.MenuServiceImpl;
import service.MenuService;
import utils.ResponseUtil;
import vo.MenuVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/menu/query"})
public class MenuServlet extends HttpServlet {
    private MenuService menuService = new MenuServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String roleId = req.getParameter("roleId");
        List<MenuVo> allMenus = menuService.getAllMenus();
        List<Integer> menuIds = menuService.getMenuIds(roleId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("allMenus",allMenus);
        jsonObject.put("menuIds",menuIds);
        ResponseUtil.DoResponse(resp,jsonObject);
    }
}
