package service.Impl;

import dao.Impl.OptionsDaoImpl;
import dao.Impl.SubjectDaoImpl;
import dao.OptionsDao;
import dao.SubjectDao;
import dto.AuditDto;
import dto.SubjectDto;
import page.PageObject;
import pojo.Subject;
import pojo.SubjectOption;
import service.SubjectService;
import vo.SubjectVo;

import java.util.List;

public class SubjectServiceImpl implements SubjectService {
    private SubjectDao subjectDao = new SubjectDaoImpl();
    private OptionsDao optionsDao = new OptionsDaoImpl();
    @Override
    public int add(SubjectDto subjectDto) {
        int result = subjectDao.addSubject(subjectDto);
        if (result==1){//试题存储成功
            if (subjectDto.getOptions()!=null&&subjectDto.getOptions().size()>0){
                Integer lastInsertId = subjectDao.getLastInsertId();
                int affectedRows = optionsDao.addOptions(subjectDto.getOptions(),lastInsertId);
                if (affectedRows == subjectDto.getOptions().size()){
                    return result;//选项成功添加
                }else {
                    return -1;//选项添加失败
                }
            }
            return result;//无选项试题添加成功
        }else {
            return -2;//试题添加失败
        }
    }

    @Override
    public PageObject<SubjectVo> query(Subject subject) {
        Integer pageSize = subject.getPageSize();
        Integer currentPage = subject.getCurrentPage();
        Integer offset = (currentPage-1)*pageSize;
        List<SubjectVo> subjectVoList = subjectDao.query(subject,offset,pageSize);
        for (SubjectVo subjectVo:subjectVoList){
            if (subjectVo.getSubjectTypeId()<4){//单选,多选,判断题需要查询选项
                Integer id = subjectVo.getId();
                List<SubjectOption> options = optionsDao.query(id);
                subjectVo.setOptions(options);
            }
        }
        Integer count = subjectDao.getSubjectsCount(subject);
        PageObject<SubjectVo> subjects = new PageObject<>();
        subjects.setObjectList(subjectVoList);
        subjects.setObjectTotalCount(count);
        return subjects;
    }

    @Override
    public int update(SubjectDto subjectDto) {
        int result = subjectDao.updateSubject(subjectDto);
        if (result ==1){//修改选项应该先删除选项再添加,因为不确定之前是否有选项
            int resultCount = optionsDao.deleteOptions(subjectDto.getId());
            List<SubjectOption> options = subjectDto.getOptions();
            if (options!=null&&options.size()>0){
                int affectedRows = optionsDao.addOptions(options,subjectDto.getId());
                if (affectedRows == options.size()){
                    return result;//选项成功添加
                }else {
                    return -1;//选项添加失败
                }
            }else {
                return result;
            }
        }else{
            return -2;
        }
    }

    @Override
    public int audit(AuditDto auditDto) {
        return subjectDao.audit(auditDto);
    }

    @Override
    public int delete(String id) {
        int affectedRows = subjectDao.deleteSubject(id);
        if (affectedRows ==1){//删除试卷
            int subjectId = Integer.parseInt(id);
            int result = optionsDao.deleteOptions(subjectId);//无论有没有都该删除选项
        }
        return affectedRows;
    }
}
