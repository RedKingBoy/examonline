package service;

import dto.ScoreDto;
import page.PageObject;
import vo.ScoreVo;

import java.util.List;

public interface ScoreService {
    PageObject<ScoreVo> query(ScoreDto scoreDto);
}

