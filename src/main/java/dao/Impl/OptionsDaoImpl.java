package dao.Impl;

import dao.OptionsDao;
import pojo.SubjectOption;
import utils.JdbcUtil;
import utils.MultiResultHandler;

import java.util.ArrayList;
import java.util.List;

public class OptionsDaoImpl implements OptionsDao {
    @Override
    public int addOptions(List<SubjectOption> options,Integer subjectId) {
        String sql = "INSERT INTO subject_options(orders,content,subject_id) VALUES";
        List<Object> params = new ArrayList<>();
        for (SubjectOption option:options){
            sql+="(?,?,?),";
            params.add(option.getOrders());
            params.add(option.getContent());
            params.add(subjectId);
        }
        sql = sql.substring(0,sql.length()-1);
        return JdbcUtil.update(sql,params.toArray());
    }

    @Override
    public List<SubjectOption> query(Integer id) {
        String sql = "SELECT id,orders,content,subject_id subjectId FROM subject_options WHERE subject_id = ? ORDER BY orders ASC";
        return JdbcUtil.query(new MultiResultHandler<>(SubjectOption.class),sql,id);
    }
    @Override
    public int deleteOptions(Integer id) {
        String sql = "DELETE FROM subject_options WHERE subject_id = ?";
        return JdbcUtil.update(sql,id);
    }
}
