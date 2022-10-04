package service;

import dto.AuditDto;
import pojo.Knowledge;

import java.util.List;

public interface KnowledgeService {
    int audit(AuditDto auditDto);

    int delete(String ids);

    int update(Knowledge knowledge);

    int add(Knowledge knowledge);

    List<Knowledge> getArray(String courseId);
}
