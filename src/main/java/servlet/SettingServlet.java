package servlet;

import dto.SettingDto;
import service.Impl.SettingServiceImpl;
import service.SettingService;
import utils.RequestUtil;
import utils.ResponseUtil;
import vo.SettingVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/setting","/setting/update","/setting/add"})
public class SettingServlet extends HttpServlet {
    private SettingService settingService = new SettingServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tableName = req.getParameter("tableName");
        List<SettingVo> search = settingService.search(tableName);
        ResponseUtil.DoResponse(resp,search);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tableName = req.getParameter("tableName");
        String id = req.getParameter("id");
        int result = settingService.delete(tableName,id);
        ResponseUtil.DoResponse(resp,result);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SettingDto settingDto = RequestUtil.parseRequestBody(req, SettingDto.class);
        int result = settingService.add(settingDto);
        ResponseUtil.DoResponse(resp,result);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SettingDto settingDto = RequestUtil.parseRequestBody(req, SettingDto.class);
        int result = settingService.update(settingDto);
        ResponseUtil.DoResponse(resp,result);
    }
}
