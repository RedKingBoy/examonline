package service.Impl;

import Task.EmailTask;
import dao.ExamDao;
import dao.Impl.ExamDaoImpl;
import dao.Impl.PaperDaoImpl;
import dao.Impl.UserDaoImpl;
import dao.PaperDao;
import dao.UserDao;
import dto.*;
import page.PageObject;
import pojo.Exam;
import pojo.ExamUser;
import pojo.SubjectOption;
import service.ExamService;
import vo.ExamVo;
import vo.PaperConfigDetailVo;
import vo.PaperDetailVo;
import vo.UserVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class ExamServiceImpl implements ExamService {
    private ExamDao examDao = new ExamDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    private PaperDao paperDao = new PaperDaoImpl();
    private static final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

    @Override
    public PageObject<ExamVo> query(ExamDto examDto) {
        Integer currentPage = examDto.getCurrentPage();
        Integer pageSize = examDto.getPageSize();
        Integer offset = (currentPage - 1) * pageSize;
        int count = examDao.getExamsCount(examDto);
        List<ExamVo> examVos = examDao.queryExams(examDto, offset, pageSize);
        return new PageObject<>(examVos, count);
    }

    @Override
    public int add(Exam exam) {
        //新增一场考试就代表试卷已被启用,使用状态变为1
        int affectedRows = examDao.addExam(exam);
        if (affectedRows == 1) {
            examDao.setPaperUseState(exam.getPaperBId(), exam.getPaperBId());
        }
        return affectedRows;
    }

    @Override
    public int update(Exam exam) {
        return examDao.updateExam(exam);
    }

    @Override
    public int audit(AuditDto auditDto) {
        return examDao.audit(auditDto);
    }

    @Override
    public int delete(String ids) {
        return examDao.delete(ids);
    }

    @Override
    public int dispatch(ExamDispatchDto examDispatchDto) {
        List<ExamUser> examUsers = new ArrayList<>();
        //考虑AB卷的情况
        Integer examId = examDispatchDto.getExamId();
        List<Integer> paperIds = examDao.getPaperIds(examId);
        List<String> usernames = examDispatchDto.getUsernames();
        if (paperIds.size() == 2) {
            for (String username : usernames) {
                Random r = new Random();
                int result = r.nextInt(usernames.size());
                int index = result % 2;
                examUsers.add(new ExamUser(examId,username,paperIds.get(index)));
            }
        } else {
            for (String username : usernames) {
                examUsers.add(new ExamUser(examId,username,paperIds.get(0)));
            }
        }
        int affectedRows = examDao.saveExamUsers(examUsers);
        if (affectedRows==usernames.size()){//人员分配成功发邮件
            //邮件如果发送很多要考虑交互性能问题
            //为了时间考虑我们发送邮件的结果不需要传回前端,后端自己处理即可,考虑线程池来解决
            for (ExamUser examUser:examUsers){//针对每一个用户来处理邮件的发送,使用线程池提高性能
                //记得导入工具包时是要把工具包导入到项目的输出jar包(构件)中才能部署到服务器运行使用
                EmailTask emailTask = new EmailTask(examUser, examDao);
                //利用线程池延迟五秒执行邮件的任务
                scheduledExecutorService.schedule(emailTask,5, TimeUnit.SECONDS);
            }
        }
        return affectedRows;
    }

    @Override
    public PaperDetailVo doExam(String examId, String username) {
        PaperDetailVo exam = examDao.getExam(examId,username);
        UserVo examUser = userDao.getExamUsers(username);
        exam.setExamUser(examUser);
        List<PaperConfigDetailVo> paperConfigs = paperDao.getPaperConfigDetailVos(exam.getPaperId().toString());
        exam.setPaperConfigs(paperConfigs);
        for (PaperConfigDetailVo config : paperConfigs){
            List<SubjectDto> subjects = paperDao.getSubjects(config.getId());
            config.setSubjects(subjects);
            for (SubjectDto subject:subjects){
                List<SubjectOption> options = paperDao.getOptions(subject.getId());
                subject.setOptions(options);
            }
        }
        return exam;
    }

    @Override
    public int submitExam(ExamSubmitDto examSubmitDto) {
        //提交考试试卷首先先把试卷状态变为已使用
        int result = paperDao.setPaperUseState(examSubmitDto.getPaperId());
        if (result==1){
            //提交答案到答题卡的表中
            int submitRows = examDao.submitAnswer(examSubmitDto);
            int scoreRows = examDao.submitScore(examSubmitDto);
        }
        return result;
    }
}
