package servlet;

import dto.PaperConfigDto;
import pojo.PaperConfig;
import service.Impl.PaperConfigServiceImpl;
import service.Impl.PaperServiceImpl;
import service.PaperConfigService;
import service.PaperService;
import utils.RequestUtil;
import utils.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/paperConfig/add"})
public class PaperConfigServlet extends HttpServlet {
    private PaperConfigService paperConfigService = new PaperConfigServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PaperConfigDto paperConfigDto = RequestUtil.parseRequestBody(req, PaperConfigDto.class);
        int result = paperConfigService.add(paperConfigDto);
        ResponseUtil.DoResponse(resp,result);
    }
}
