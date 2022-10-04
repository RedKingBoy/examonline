package service.Impl;

import dao.Impl.PaperDaoImpl;
import dao.PaperDao;
import dto.AuditDto;
import dto.SubjectDto;
import page.PageObject;
import pojo.Paper;
import pojo.SubjectOption;
import service.PaperService;
import vo.PaperConfigDetailVo;
import vo.PaperDetailVo;
import vo.PaperVo;

import java.util.List;

public class PaperServiceImpl implements PaperService {
    private PaperDao paperDao = new PaperDaoImpl();
    @Override
    public PageObject<PaperVo> search(Paper paper) {
        Integer currentPage = paper.getCurrentPage();
        Integer pageSize = paper.getPageSize();
        Integer offset = (currentPage-1)*pageSize;
        Integer total = paperDao.getPapersCount(paper);
        List<PaperVo> paperVoList = paperDao.getPapers(paper,offset,pageSize);
        PageObject<PaperVo> papers = new PageObject<>();
        papers.setObjectTotalCount(total);
        papers.setObjectList(paperVoList);
        return papers;
    }

    @Override
    public int audit(AuditDto auditDto) {
        return paperDao.auditPaper(auditDto);
    }

    @Override
    public int delete(String ids) {
        return paperDao.deletePapers(ids);
    }

    @Override
    public int add(Paper paper) {
        return paperDao.addPapers(paper);
    }

    @Override
    public int update(Paper paper) {
        return paperDao.updatePapers(paper);
    }

    @Override
    public List<PaperVo> queryPapers(String query) {
        return paperDao.queryPapers(query);//考试设置的查询试卷
    }

    @Override
    public PaperDetailVo queryConfigs(String paperId) {
        PaperDetailVo paper = paperDao.getPaperDetailVo(paperId);
        List<PaperConfigDetailVo> paperConfigs = paperDao.getPaperConfigDetailVos(paperId);
        paper.setPaperConfigs(paperConfigs);
        for (PaperConfigDetailVo config : paperConfigs){
            List<SubjectDto> subjects = paperDao.getSubjects(config.getId());
            config.setSubjects(subjects);
            for (SubjectDto subject:subjects){
                List<SubjectOption> options = paperDao.getOptions(subject.getId());
                subject.setOptions(options);
            }
        }
        return paper;
    }
}
