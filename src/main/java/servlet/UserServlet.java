package servlet;

import com.alibaba.fastjson.JSONObject;
import dao.Impl.UserDaoImpl;
import dao.UserDao;
import dto.AssignRoleDto;
import dto.AuditDto;
import dto.UserDto;
import excel.ExcelDataRepository;
import excel.ExcelUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import page.PageObject;
import pojo.Menu;
import pojo.User;
import service.Impl.UserServiceImpl;
import service.UserService;
import utils.RequestUtil;
import utils.ResponseUtil;
import vo.UserDetailVo;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/user/login", "/user/register", "/user/username", "/user/initInfo", "/user/getUserInfo"
        , "/user/logout", "/user/query", "/user/audit", "/user/delete", "/user/upload","/user/download"
        ,"/user/assignRole","/user/queryUsers"
})
public class UserServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();
    private UserDao userDao = new UserDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUrl = RequestUtil.getRequestUrl(req);
        if (requestUrl.equals("/user/username")) {
            userExists(req, resp);
        } else if (requestUrl.equals("/user/initInfo")) {
            initInfo(req, resp);
        } else if (requestUrl.equals("/user/getUserInfo")) {
            getUserInfo(req, resp);
        } else if (requestUrl.equals("/user/query")) {
            queryUsers(req, resp);
        }else if (requestUrl.equals("/user/download")){
            download(req,resp);
        }else if (requestUrl.equals("/user/queryUsers")){//????????????????????????????????????
            String query = req.getParameter("query");
            List<UserDetailVo> users = userService.queryUsersByName(query);
            ResponseUtil.DoResponse(resp,users);
        }
    }

    private void download(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserDto userDto = RequestUtil.parseRequestParams(req, UserDto.class);
        String fileName = "???????????????.xls";
        fileName = new String(fileName.getBytes(), StandardCharsets.ISO_8859_1);
        resp.setHeader("Content-Disposition","attachment;filename="+fileName);
        ServletOutputStream os = resp.getOutputStream();
        ExcelDataRepository<User> repository = new ExcelDataRepository<User>() {
            @Override
            public int getAllDataCount() {
                return userService.exportCount(userDto);
            }

            @Override
            public List<User> getExcelDataPerPage(int sheet, int sheetSize) {
                return userService.exportUsers(userDto,sheet,sheetSize);
            }
        };
        ExcelUtil.exportExcel(os,User.class,repository);
    }

    private void queryUsers(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserDto userDto = RequestUtil.parseRequestParams(req, UserDto.class);
        PageObject<UserDetailVo> result = userService.queryUsers(userDto);
        ResponseUtil.DoResponse(resp, result);//????????????????????????????????????
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUrl = RequestUtil.getRequestUrl(req);
        if ("/user/logout".equals(requestUrl)) {
            logout(req, resp);
        } else if ("/user/audit".equals(requestUrl)) {
            auditUser(req, resp);
        }else if ("/user/assignRole".equals(requestUrl)){
            assignRole(req,resp);
        }
    }
    //?????????????????????
    private void assignRole(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        AssignRoleDto assignRoleDto = RequestUtil.parseRequestBody(req, AssignRoleDto.class);
        int result = userService.assign(assignRoleDto);
        ResponseUtil.DoResponse(resp,result);
    }

    private void auditUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        AuditDto auditDto = RequestUtil.parseRequestBody(req, AuditDto.class);
        Integer audit = userService.audit(auditDto);
        ResponseUtil.DoResponse(resp, audit);
    }

    private void getUserInfo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = (String) req.getSession().getAttribute("user");
        User user = userDao.getUser(username);
        username = user.getUsername();
        UserDetailVo userDetailVos = userService.userDetail(username);
        ResponseUtil.DoResponse(resp, userDetailVos);//???????????????????????????????????????
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUrl = RequestUtil.getRequestUrl(req);
        if (requestUrl.equals("/user/login")) {
            login(req, resp);
        } else if (requestUrl.equals("/user/register")) {
            register(req, resp);
        } else if (requestUrl.equals("/user/upload")) {
            uploadUser(req, resp);
        }
    }

    private void uploadUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int success = 1;//????????????
        if (ServletFileUpload.isMultipartContent(req)) {
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            diskFileItemFactory.setDefaultCharset("utf-8");
            ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
            servletFileUpload.setFileSizeMax(5 * (1 << 20));
            servletFileUpload.setSizeMax(50 * (1 << 20));
            try {
                List<FileItem> fileItems = servletFileUpload.parseRequest(req);
                for (FileItem fileItem : fileItems) {
                    if (!fileItem.isFormField()) {
                        InputStream is = fileItem.getInputStream();
                        ExcelUtil.parseExcel(is, User.class, userService::uploadUsers);
                    }
                }
            } catch (FileUploadException | IOException e) {
                e.printStackTrace();
                success = 0;//????????????
            }
        } else {
            success = -1;//?????????????????????
        }
        ResponseUtil.DoResponse(resp,success);
    }

    public void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = (String) req.getSession().getAttribute("user");
        if (username != null) {
            req.getSession().invalidate();
        }
        ResponseUtil.DoResponse(resp, 1);
    }

    public void login(HttpServletRequest req, HttpServletResponse resp) {
        try {
            User user = RequestUtil.parseRequestBody(req, User.class);
            int result = userService.login(user);
            User user1 = userDao.getUser(user.getUsername());
            String username = user1.getUsername();
            if (result == 1) {
                req.getSession().setAttribute("user", username);
            }
            ResponseUtil.DoResponse(resp, result);
        } catch (IOException e) {
            e.printStackTrace();
        }//??????????????????
    }

    public void register(HttpServletRequest req, HttpServletResponse resp) {
        try {
            User user = RequestUtil.parseRequestBody(req, User.class);
            int register = userService.register(user);
            ResponseUtil.DoResponse(resp, register);
        } catch (IOException e) {
            e.printStackTrace();
        }//??????????????????
    }

    public void userExists(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        int result = userService.userExists(username);
        ResponseUtil.DoResponse(resp, result);//?????????????????????????????????
    }

    public void initInfo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = (String) req.getSession().getAttribute("user");
        User user = userDao.getUser(username);
        user.setPassword("");//??????????????????????????????,????????????
        List<Menu> menus = userService.initMenu(user);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", user);
        jsonObject.put("menus", menus);
        ResponseUtil.DoResponse(resp, jsonObject);//???????????????,??????????????????????????????
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ids = req.getParameter("ids");
        int result = userService.delete(ids);
        ResponseUtil.DoResponse(resp, result);
    }
}
