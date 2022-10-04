package dao.Impl;

import dao.ScoreDao;
import dto.ScoreDto;
import utils.JdbcUtil;
import utils.MultiResultHandler;
import utils.SingleResultHandler;
import vo.ScoreVo;

import java.util.ArrayList;
import java.util.List;

public class ScoreDaoImpl implements ScoreDao {
    @Override
    public List<ScoreVo> query(ScoreDto scoreDto, Integer offset, Integer pageSize) {
        String sql = "SELECT s.exam_id examId,e.start_time examTime, p.title examName,p.total_score totalScore,(SELECT dept_name FROM department d WHERE d.dept_id = p.dept_id) deptName,(SELECT post_name FROM post po WHERE po.post_id = p.post_id) postName,u.name examUserName,s.score FROM score s JOIN exam e ON e.id = s.exam_id JOIN paper p ON p.id = s.paper_id JOIN `user` u ON u.username = s.username WHERE 1=1";
        List<Object> params = new ArrayList<>();
        String name = scoreDto.getName();
        if (name!=null&&!"".equals(name)){
            sql += " AND u.name LIKE ?";
            params.add("%"+name+"%");
        }
        String paperName = scoreDto.getPaperName();
        if (paperName!=null&&!"".equals(paperName)){
            sql += " AND p.title LIKE ?";
            params.add("%"+paperName+"%");
        }
        String start = scoreDto.getStart();
        if (start!=null&&!"".equals(start)){
            sql += " AND e.start_time >= ?";
            params.add(start);
        }
        String end = scoreDto.getEnd();
        if (end!=null&&!"".equals(end)){
            sql += " AND e.start_time <= ?";
            params.add(end);
        }
        sql += " LIMIT ?,?";
        params.add(offset);
        params.add(pageSize);
        return JdbcUtil.query(new MultiResultHandler<>(ScoreVo.class),sql,params.toArray());
    }

    @Override
    public int getCount(ScoreDto scoreDto) {
        String sql = "SELECT COUNT(*) FROM score s JOIN exam e ON e.id = s.exam_id JOIN paper p ON p.id = s.paper_id JOIN `user` u ON u.username = s.username WHERE 1=1";
        List<Object> params = new ArrayList<>();
        String name = scoreDto.getName();
        if (name!=null&&!"".equals(name)){
            sql += " AND u.name LIKE ?";
            params.add("%"+name+"%");
        }
        String paperName = scoreDto.getPaperName();
        if (paperName!=null&&!"".equals(paperName)){
            sql += " AND p.title LIKE ?";
            params.add("%"+paperName+"%");
        }
        String start = scoreDto.getStart();
        if (start!=null&&!"".equals(start)){
            sql += " AND e.start_time >= ?";
            params.add(start);
        }
        String end = scoreDto.getEnd();
        if (end!=null&&!"".equals(end)){
            sql += " AND e.start_time <= ?";
            params.add(end);
        }
        return JdbcUtil.query(new SingleResultHandler<>(int.class),sql,params.toArray());
    }
}
