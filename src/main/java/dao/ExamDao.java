package dao;

import bo.EmailBo;
import dto.AuditDto;
import dto.ExamDto;
import dto.ExamSubmitDto;
import pojo.Exam;
import pojo.ExamUser;
import vo.ExamVo;
import vo.PaperDetailVo;

import java.util.List;

public interface ExamDao {
    int getExamsCount(ExamDto examDto);

    List<ExamVo> queryExams(ExamDto examDto, Integer offset, Integer pageSize);

    int addExam(Exam exam);

    int updateExam(Exam exam);

    int audit(AuditDto auditDto);

    int delete(String ids);

    int setPaperUseState(Integer paperAId,Integer paperBId);

    List<Integer> getPaperIds(Integer examId);

    int saveExamUsers(List<ExamUser> examUsers);

    EmailBo getEmailBo(ExamUser examUser);

    PaperDetailVo getExam(String examId, String username);

    int submitAnswer(ExamSubmitDto examSubmitDto);

    int submitScore(ExamSubmitDto examSubmitDto);
}
