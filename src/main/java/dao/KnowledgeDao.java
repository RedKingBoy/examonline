package dao;

import dto.AuditDto;
import pojo.Knowledge;

import java.util.List;

public interface KnowledgeDao {
    List<Knowledge> getKnowledgeListByCourseId(Integer id);

    int auditKnowledge(AuditDto auditDto);

    void deleteCourseKnow(String ids);

    int deleteKnow(String ids);

    int updateKnow(Knowledge knowledge);

    int addKnow(Knowledge knowledge);

    List<Knowledge> getKnowArray(String courseId);
}
