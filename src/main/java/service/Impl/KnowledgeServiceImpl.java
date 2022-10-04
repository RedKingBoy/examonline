package service.Impl;

import dao.Impl.KnowledgeDaoImpl;
import dao.KnowledgeDao;
import dto.AuditDto;
import pojo.Knowledge;
import service.KnowledgeService;

import java.util.List;

public class KnowledgeServiceImpl implements KnowledgeService {
    private KnowledgeDao knowledgeDao = new KnowledgeDaoImpl();
    @Override
    public int audit(AuditDto auditDto) {
        return knowledgeDao.auditKnowledge(auditDto);
    }

    @Override
    public int delete(String ids) {
        return knowledgeDao.deleteKnow(ids);
    }

    @Override
    public int update(Knowledge knowledge) {
        return knowledgeDao.updateKnow(knowledge);
    }

    @Override
    public int add(Knowledge knowledge) {
        return knowledgeDao.addKnow(knowledge);
    }

    @Override
    public List<Knowledge> getArray(String courseId) {
        return knowledgeDao.getKnowArray(courseId);
    }
}
