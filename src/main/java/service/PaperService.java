package service;

import dto.AuditDto;
import page.PageObject;
import pojo.Paper;
import vo.PaperDetailVo;
import vo.PaperVo;

import java.util.List;

public interface PaperService {
    PageObject<PaperVo> search(Paper paper);

    int audit(AuditDto auditDto);

    int delete(String ids);

    int add(Paper paper);

    int update(Paper paper);

    List<PaperVo> queryPapers(String query);

    PaperDetailVo queryConfigs(String paperId);
}
