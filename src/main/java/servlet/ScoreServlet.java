package servlet;

import dto.ScoreDto;
import page.PageObject;
import service.Impl.ScoreServiceImpl;
import service.ScoreService;
import utils.RequestUtil;
import utils.ResponseUtil;
import vo.ScoreVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/score"})
public class ScoreServlet extends HttpServlet {
    private ScoreService scoreService = new ScoreServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ScoreDto scoreDto = RequestUtil.parseRequestParams(req, ScoreDto.class);
        PageObject<ScoreVo> scores = scoreService.query(scoreDto);
        ResponseUtil.DoResponse(resp,scores);
    }
}
