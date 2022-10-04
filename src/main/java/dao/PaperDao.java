package dao;

import dto.AuditDto;
import dto.SubjectDto;
import pojo.Paper;
import pojo.SubjectOption;
import vo.PaperConfigDetailVo;
import vo.PaperDetailVo;
import vo.PaperVo;

import java.util.List;

public interface PaperDao {
    int getPapersCount(Paper paper);

    List<PaperVo> getPapers(Paper paper, Integer offset, Integer pageSize);

    int auditPaper(AuditDto auditDto);

    int deletePapers(String ids);

    int addPapers(Paper paper);

    int updatePapers(Paper paper);

    List<PaperVo> queryPapers(String query);

    PaperDetailVo getPaperDetailVo(String paperId);

    List<PaperConfigDetailVo> getPaperConfigDetailVos(String paperId);

    List<SubjectDto> getSubjects(Integer id);

    List<SubjectOption> getOptions(Integer id);

    int setPaperUseState(Integer paperId);
}
