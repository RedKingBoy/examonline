package service.Impl;

import dao.Impl.ScoreDaoImpl;
import dao.ScoreDao;
import dto.ScoreDto;
import page.PageObject;
import service.ScoreService;
import vo.ScoreVo;

import java.util.List;

public class ScoreServiceImpl implements ScoreService {
    private ScoreDao scoreDao = new ScoreDaoImpl();
    @Override
    public PageObject<ScoreVo> query(ScoreDto scoreDto) {
        Integer currentPage = scoreDto.getCurrentPage();
        Integer pageSize = scoreDto.getPageSize();
        Integer offset = (currentPage-1) *pageSize;
        List<ScoreVo> scores = scoreDao.query(scoreDto,offset,pageSize);
        int count = scoreDao.getCount(scoreDto);
        return new PageObject<>(scores,count);
    }
}
