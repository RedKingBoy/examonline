package dao;

import dto.AuditDto;
import dto.SubjectDto;
import pojo.Subject;
import vo.SubjectVo;

import java.util.List;

public interface SubjectDao {
    int addSubject(SubjectDto subjectDto);

    Integer getLastInsertId();

    List<SubjectVo> query(Subject subject, Integer offset, Integer pageSize);

    int getSubjectsCount(Subject subject);

    int updateSubject(SubjectDto subjectDto);

    int audit(AuditDto auditDto);

    int deleteSubject(String id);
}
