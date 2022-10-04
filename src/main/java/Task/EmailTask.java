package Task;

import bo.EmailBo;
import dao.ExamDao;
import pojo.ExamUser;
import utils.EmailUtil;

import javax.mail.MessagingException;

//专门用来处理邮件的线程任务
public class EmailTask implements Runnable {
    private ExamUser examUser;
    private ExamDao examDao;

    public EmailTask() {
    }

    public EmailTask(ExamUser examUser, ExamDao examDao) {
        this.examUser = examUser;
        this.examDao = examDao;
    }

    @Override
    public void run() {
        EmailBo emailBo = examDao.getEmailBo(examUser);
        try {
            EmailUtil.sendExamEmail(emailBo);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
