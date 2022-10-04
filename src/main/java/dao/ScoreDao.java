package dao;

import dto.ScoreDto;
import vo.ScoreVo;

import java.util.List;

public interface ScoreDao {
    List<ScoreVo> query(ScoreDto scoreDto, Integer offset, Integer pageSize);

    int getCount(ScoreDto scoreDto);
}
