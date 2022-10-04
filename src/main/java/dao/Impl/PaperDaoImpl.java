package dao.Impl;

import dao.PaperDao;
import dto.AuditDto;
import dto.SubjectDto;
import pojo.Paper;
import pojo.SubjectOption;
import utils.JdbcUtil;
import utils.MultiResultHandler;
import utils.SingleResultHandler;
import vo.PaperConfigDetailVo;
import vo.PaperDetailVo;
import vo.PaperVo;

import java.sql.JDBCType;
import java.util.ArrayList;
import java.util.List;

public class PaperDaoImpl implements PaperDao {
    @Override
    public int getPapersCount(Paper paper) {
        String sql = "SELECT COUNT(*) FROM paper WHERE 1=1 AND is_active = 0";
        List<Object> params = new ArrayList<>();
        String title = paper.getTitle();
        if (title!=null&&!"".equals(title)){
            sql += " AND title LIKE ?";
            params.add("%"+params+"%");
        }
        Integer deptId = paper.getDeptId();
        if (deptId!=null){
            sql += " AND dept_id = ?";
            params.add(deptId);
        }
        Integer postId = paper.getPostId();
        if (postId!=null){
            sql += " AND post_id = ?";
            params.add(postId);
        }
        Integer useState = paper.getUseState();
        if (useState!=null){
            sql += " AND use_state = ?";
            params.add(useState);
        }
        Integer auditState = paper.getAuditState();
        if (auditState!=null){
            sql += " AND audit_state = ?";
            params.add(auditState);
        }
        return JdbcUtil.query(new SingleResultHandler<>(Integer.class),sql,params.toArray());
    }

    @Override
    public List<PaperVo> getPapers(Paper paper, Integer offset, Integer pageSize) {
        String sql = "SELECT p.id, p.title, p.total_score totalScore, p.audit_state auditState, p.use_state useState, p.dept_id deptId,d.dept_name deptName, p.post_id postId,po.post_name postName, p.created_time createdTime, p.created_user createdUser\n" +
                "FROM paper p LEFT JOIN department d ON p.dept_id = d.dept_id\n" +
                "LEFT JOIN post po ON p.post_id = po.post_id WHERE 1=1 AND p.is_active = 0";
        List<Object> params = new ArrayList<>();
        String title = paper.getTitle();
        if (title!=null&&!"".equals(title)){
            sql += " AND title LIKE ?";
            params.add("%"+params+"%");
        }
        Integer deptId = paper.getDeptId();
        if (deptId!=null){
            sql += " AND dept_id = ?";
            params.add(deptId);
        }
        Integer postId = paper.getPostId();
        if (postId!=null){
            sql += " AND post_id = ?";
            params.add(postId);
        }
        Integer useState = paper.getUseState();
        if (useState!=null){
            sql += " AND use_state = ?";
            params.add(useState);
        }
        Integer auditState = paper.getAuditState();
        if (auditState!=null){
            sql += " AND audit_state = ?";
            params.add(auditState);
        }
        sql += " LIMIT ?,?";
        params.add(offset);
        params.add(pageSize);
        return JdbcUtil.query(new MultiResultHandler<>(PaperVo.class),sql,params.toArray());
    }

    @Override
    public int auditPaper(AuditDto auditDto) {
        String ids = auditDto.getIds().toString();
        ids = ids.substring(1,ids.length()-1);
        String sql = "UPDATE paper SET audit_state = ? WHERE audit_state = 0 AND is_active = 0 AND id IN("+ids+")";
        return JdbcUtil.update(sql,auditDto.getAuditState());
    }

    @Override
    public int deletePapers(String ids) {
        String sql = "UPDATE paper SET is_active = 1 WHERE use_state = 0 AND id IN("+ids+")";
        return JdbcUtil.update(sql);
    }

    @Override
    public int addPapers(Paper paper) {
        String sql = "INSERT INTO paper(dept_id,post_id,title,created_user,total_score) VALUES (?,?,?,?,?)";
        Object[] params = {
                paper.getDeptId(),
                paper.getPostId(),
                paper.getTitle(),
                paper.getCreatedUser(),
                paper.getTotalScore()
        };
        return JdbcUtil.update(sql,params);
    }

    @Override
    public int updatePapers(Paper paper) {
        String sql = "UPDATE paper SET post_id =?,dept_id = ?,title = ?,total_score = ? WHERE id = ? AND use_state = 0";
        return JdbcUtil.update(sql,paper.getPostId(),paper.getDeptId(),paper.getTitle(),paper.getTotalScore(),paper.getId());
    }

    @Override
    public List<PaperVo> queryPapers(String query) {
        String sql = "SELECT id , title FROM paper WHERE 1=1 AND is_active = 0 AND audit_state = 1";
        List<Object> params = new ArrayList<>();
        if (query!=null&&!"".equals(query)){
            sql += " AND title LIKE ?";
            params.add("%"+query+"%");
        }
        return JdbcUtil.query(new MultiResultHandler<>(PaperVo.class),sql,params.toArray());
    }

    @Override
    public PaperDetailVo getPaperDetailVo(String paperId) {
        String sql = "SELECT id,title,(SELECT dept_name FROM department d WHERE  d.dept_id = p.dept_id) deptName,(SELECT post_name FROM post po WHERE po.post_id = p.post_id) postName\n" +
                "FROM paper p WHERE id = ?";
        return JdbcUtil.query(new SingleResultHandler<>(PaperDetailVo.class),sql,paperId);
    }

    @Override
    public List<PaperConfigDetailVo> getPaperConfigDetailVos(String paperId) {
        String sql = "SELECT id,title,subject_score subjectScore FROM paper_config WHERE paper_id = ?";
        return JdbcUtil.query(new MultiResultHandler<>(PaperConfigDetailVo.class),sql,paperId);
    }

    @Override
    public List<SubjectDto> getSubjects(Integer id) {
        String sql = "SELECT id,title,answer FROM `subject` s WHERE FIND_IN_SET(id,(SELECT subject_ids FROM paper_config pc WHERE pc.id = ? ))";
        return JdbcUtil.query(new MultiResultHandler<>(SubjectDto.class),sql,id);
    }

    @Override
    public List<SubjectOption> getOptions(Integer id) {
        String sql = "SELECT content FROM subject_options WHERE subject_id = ? ORDER BY orders ASC";
        return JdbcUtil.query(new MultiResultHandler<>(SubjectOption.class),sql,id);
    }

    @Override
    public int setPaperUseState(Integer paperId) {
        String sql = "UPDATE paper SET use_state = 2 WHERE use_state = 1 AND id = ?";
        return JdbcUtil.update(sql,paperId);
    }
}
