package service;

import dto.AuditDto;
import dto.SubjectDto;
import page.PageObject;
import pojo.Subject;
import vo.SubjectVo;

public interface SubjectService {
    int add(SubjectDto subjectDto);

    PageObject<SubjectVo> query(Subject subject);

    int update(SubjectDto subjectDto);

    int audit(AuditDto auditDto);

    int delete(String id);
}
