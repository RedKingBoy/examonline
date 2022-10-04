package dao.Impl;

import dao.KnowledgeDao;
import dto.AuditDto;
import pojo.Knowledge;
import pojo.Post;
import utils.JdbcUtil;
import utils.MultiResultHandler;

import java.util.List;

public class KnowledgeDaoImpl implements KnowledgeDao {
    @Override
    public List<Knowledge> getKnowledgeListByCourseId(Integer id) {
        String sql = "SELECT id,name,audit_state auditState,course_id courseId FROM knowledge WHERE is_active = 0 AND course_id = ?";
        return JdbcUtil.query(new MultiResultHandler<>(Knowledge.class), sql, id);
    }

    @Override
    public int auditKnowledge(AuditDto auditDto) {
        String sql = "UPDATE knowledge SET audit_state = ? WHERE is_active = 0 AND id IN(";
        List<Integer> ids = auditDto.getIds();
        String idsStr = ids.toString();
        idsStr = idsStr.substring(1, idsStr.length() - 1);
        sql += idsStr + ")";
        return JdbcUtil.update(sql, auditDto.getAuditState());
    }

    @Override
    public void deleteCourseKnow(String ids) {
        String sql = "UPDATE knowledge SET is_active = 1 WHERE is_active = 0 AND course_id IN (" + ids + ")";
        JdbcUtil.update(sql);
    }
    @Override
    public int deleteKnow(String ids) {
        String sql = "UPDATE knowledge SET is_active = 1 WHERE id IN (" + ids + ")";
        return JdbcUtil.update(sql);
    }

    @Override
    public int updateKnow(Knowledge knowledge) {
        String sql = "UPDATE knowledge SET name = ? WHERE id = ?";
        return JdbcUtil.update(sql,knowledge.getName(),knowledge.getId());
    }

    @Override
    public int addKnow(Knowledge knowledge) {
        String sql = "INSERT INTO knowledge(name,course_id) VALUES (?,?)";
        return JdbcUtil.update(sql,knowledge.getName(),knowledge.getCourseId());
    }

    @Override
    public List<Knowledge> getKnowArray(String courseId) {
        String sql = "SELECT id,name FROM knowledge WHERE is_active = 0 AND audit_state = 1 AND course_id = ?";
        return JdbcUtil.query(new MultiResultHandler<>(Knowledge.class),sql,courseId);
    }
}
