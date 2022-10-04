package service;

import dto.AuditDto;
import dto.ExamDispatchDto;
import dto.ExamDto;
import dto.ExamSubmitDto;
import page.PageObject;
import pojo.Exam;
import vo.ExamVo;
import vo.PaperDetailVo;

import java.util.List;

public interface ExamService {
    PageObject<ExamVo> query(ExamDto examDto);

    int add(Exam exam);

    int update(Exam exam);

    int audit(AuditDto auditDto);

    int delete(String ids);

    int dispatch(ExamDispatchDto examDispatchDto);

    PaperDetailVo doExam(String examId, String username);

    int submitExam(ExamSubmitDto examSubmitDto);
}
