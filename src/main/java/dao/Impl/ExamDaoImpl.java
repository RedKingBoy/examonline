package dao.Impl;

import bo.EmailBo;
import dao.ExamDao;
import dto.AuditDto;
import dto.ConfigInfo;
import dto.ExamDto;
import dto.ExamSubmitDto;
import pojo.Exam;
import pojo.ExamUser;
import utils.JdbcUtil;
import utils.MultiResultHandler;
import utils.SingleResultHandler;
import vo.ExamVo;
import vo.PaperDetailVo;

import java.util.ArrayList;
import java.util.List;

public class ExamDaoImpl implements ExamDao {
    @Override
    public int getExamsCount(ExamDto examDto) {
        String sql = "SELECT COUNT(*) FROM exam WHERE 1=1 AND is_active = 0";
        List<Object> params = new ArrayList<>();
        Integer useState = examDto.getUseState();
        if (useState!=null){
            sql += " AND use_state = ?";
            params.add(useState);
        }
        Integer auditState = examDto.getAuditState();
        if (auditState!=null){
            sql += " AND audit_state = ?";
            params.add(auditState);
        }
        String start = examDto.getStart();
        if (start!=null&&!"".equals(start)){
            sql += " AND start_time >= ?";//时间可以用字符串直接比较大小
            params.add(start);
        }
        String end = examDto.getEnd();
        if (end!=null&&!"".equals(end)){
            sql += " AND start_time <= ?";
            params.add(end);
        }
        return JdbcUtil.query(new SingleResultHandler<>(Integer.class),sql,params.toArray());
    }

    @Override
    public List<ExamVo> queryExams(ExamDto examDto, Integer offset, Integer pageSize) {
        String sql = "SELECT id,start_time startTime,cost_time costTime,examiner,audit_state auditState,use_state useState,paper_a_id paperAId," +
                "paper_b_id paperBId,created_user createdUser,(SELECT title FROM paper WHERE id = paper_a_id) paperAName,(SELECT title FROM paper WHERE id = paper_b_id) paperBName FROM exam" +
                " WHERE 1=1 AND is_active = 0 ";
        List<Object> params = new ArrayList<>();
        Integer useState = examDto.getUseState();
        if (useState!=null){
            sql += " AND use_state = ?";
            params.add(useState);
        }
        Integer auditState = examDto.getAuditState();
        if (auditState!=null){
            sql += " AND audit_state = ?";
            params.add(auditState);
        }
        String start = examDto.getStart();
        if (start!=null&&!"".equals(start)){
            sql += " AND start_time >= ?";//时间可以用字符串直接比较大小
            params.add(start);
        }
        String end = examDto.getEnd();
        if (end!=null&&!"".equals(end)){
            sql += " AND start_time <= ?";
            params.add(end);
        }
        sql += " LIMIT ?,?";
        params.add(offset);
        params.add(pageSize);
        return JdbcUtil.query(new MultiResultHandler<>(ExamVo.class),sql,params.toArray());
    }

    @Override
    public int addExam(Exam exam) {
        String sql = "INSERT INTO exam(start_time,cost_time,examiner,paper_a_id,paper_b_id,created_user) VALUES (?,?,?,?,?,?)";
        Object[] params = {
                exam.getStartTime(),
                exam.getCostTime(),
                exam.getExaminer(),
                exam.getPaperAId(),
                exam.getPaperBId(),
                exam.getCreatedUser()
        };
        return JdbcUtil.update(sql,params);
    }

    @Override
    public int updateExam(Exam exam) {
        String sql = "UPDATE exam SET start_time = ?, cost_time = ?, examiner = ?, paper_a_id = ?, paper_b_id = ? WHERE id = ? AND is_active = 0 AND use_state = 0 AND audit_state != 1 ";
        Object[] params = {
                exam.getStartTime(),
                exam.getCostTime(),
                exam.getExaminer(),
                exam.getPaperAId(),
                exam.getPaperBId(),
                exam.getId()
        };
        return JdbcUtil.update(sql,params);
    }

    @Override
    public int audit(AuditDto auditDto) {
        List<Integer> ids = auditDto.getIds();
        String idsStr = ids.toString();
        idsStr = idsStr.substring(1,idsStr.length()-1);
        String sql = "UPDATE exam SET audit_state = ? WHERE audit_state = 0 AND id IN("+idsStr+")";
        return JdbcUtil.update(sql,auditDto.getAuditState());
    }

    @Override
    public int delete(String ids) {
        String sql = "UPDATE exam SET is_active = 1 WHERE id IN("+ids+")";
        return JdbcUtil.update(sql);
    }

    @Override
    public int setPaperUseState(Integer paperAId,Integer paperBId) {
        String sql = "UPDATE paper SET use_state = 1 WHERE use_state = 0 AND audit_state = 1 AND is_active = 0";
        List<Object> params = new ArrayList<>();
        if (paperAId!=null){
            sql += " AND paper_a_id = ?";
            params.add(paperAId);
        }
        if (paperBId!=null){
            sql += " AND paper_b_id = ?";
            params.add(paperBId);
        }
        return JdbcUtil.update(sql,params.toArray());
    }

    @Override
    public List<Integer> getPaperIds(Integer examId) {
        String sql = "SELECT paper_a_id FROM exam WHERE id = ? AND paper_a_id IS NOT NULL\n" +
                "UNION \n" +
                "SELECT paper_b_id FROM exam WHERE id = ? AND paper_b_id IS NOT NULL";
        return JdbcUtil.query(new MultiResultHandler<>(Integer.class),sql,examId,examId);
    }

    @Override
    public int saveExamUsers(List<ExamUser> examUsers) {
        String sql = "INSERT INTO exam_user(exam_id,username,paper_id) VALUES";
        List<Object> params = new ArrayList<>();
        for (ExamUser examUser :examUsers){
            sql += "(?,?,?),";
            params.add(examUser.getExamId());
            params.add(examUser.getUsername());
            params.add(examUser.getPaperId());
        }
        sql = sql.substring(0,sql.length()-1);
        return JdbcUtil.update(sql,params.toArray());
    }

    @Override
    public EmailBo getEmailBo(ExamUser examUser) {
        String sql = "SELECT e.start_time startTime,e.cost_time costTime,p.title examName,u.email FROM exam_user eu JOIN exam e ON e.id = eu.exam_id\n" +
                "JOIN paper p ON p.id = eu.paper_id JOIN `user` u ON u.username = eu.username  WHERE eu.exam_id = ? AND eu.username = ? AND eu.paper_id = ? ";
        Object[] params = {
                examUser.getExamId(),
                examUser.getUsername(),
                examUser.getPaperId()
        };
        return JdbcUtil.query(new SingleResultHandler<>(EmailBo.class),sql,params);
    }

    @Override
    public PaperDetailVo getExam(String examId, String username) {
        String sql = "SELECT eu.exam_id id,eu.paper_id paperId,e.start_time startTime,e.cost_time costTime,p.title,p.total_score totalScore,(SELECT dept_name FROM department d WHERE d.dept_id = p.dept_id ) deptName,\n" +
                "(SELECT post_name FROM post po WHERE po.post_id = p.post_id) postName,(SELECT score FROM score s WHERE s.exam_id = eu.exam_id AND s.paper_id = eu.paper_id) examScore\n" +
                "FROM exam_user eu JOIN exam e ON eu.exam_id = e.id \n" +
                "JOIN paper p ON p.id = eu.paper_id\n" +
                "WHERE eu.exam_id = ? AND eu.username = ?\n";
        return JdbcUtil.query(new SingleResultHandler<>(PaperDetailVo.class),sql,examId,username);
    }

    @Override
    public int submitAnswer(ExamSubmitDto examSubmitDto) {
        String sql = "INSERT INTO `paper_card` (`username`, `paper_id`, `paper_config_id`, `answer`) VALUES";
        List<ConfigInfo> configInfos = examSubmitDto.getConfigInfos();
        List<Object> params = new ArrayList<>();
        for (ConfigInfo configInfo:configInfos){
            sql += "(?,?,?,?),";
            params.add(examSubmitDto.getUsername());
            params.add(examSubmitDto.getPaperId());
            params.add(configInfo.getConfigId());
            params.add(configInfo.getAnswer());
        }
        sql = sql.substring(0,sql.length()-1);
        return JdbcUtil.update(sql,params.toArray());
    }

    @Override
    public int submitScore(ExamSubmitDto examSubmitDto) {
        String sql = "INSERT INTO `score` (`username`, `exam_id`, `paper_id`, `score`) VALUES (?, ?, ?, ?)";
        Object[] params = {
                examSubmitDto.getUsername(),
                examSubmitDto.getExamId(),
                examSubmitDto.getPaperId(),
                examSubmitDto.getTotalScore()
        };
        return JdbcUtil.update(sql,params);
    }
}
