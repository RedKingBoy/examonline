package dao.Impl;

import dao.PaperConfigDao;
import dto.PaperConfigDto;
import pojo.PaperConfig;
import utils.JdbcUtil;

import java.util.ArrayList;
import java.util.List;

public class PaperConfigDaoImpl implements PaperConfigDao {
    @Override
    public int add(PaperConfigDto paperConfigDto) {
        String sql = "INSERT INTO paper_config(title,subject_score,paper_id,subject_ids) VALUES";
        List<PaperConfig> configs = paperConfigDto.getConfigs();
        List<Object> params = new ArrayList<>();
        for (PaperConfig config:configs){
            sql += "(?,?,?,?),";
            params.add(config.getTitle());
            params.add(config.getSubjectScore());
            params.add(config.getPaperId());
            params.add(config.getSubjectIds());
        }
        sql = sql.substring(0,sql.length()-1);
        return JdbcUtil.update(sql,params.toArray());
    }
}
