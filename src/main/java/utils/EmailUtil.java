package utils;

import bo.EmailBo;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EmailUtil {
    private static final Properties properties = new Properties();

    static {
        InputStream is = EmailUtil.class.getResourceAsStream("/email.properties");
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final Authenticator authenticator = new Authenticator() {
        @Override
        //密码验证也就是账户和授权码认证 授权码:vavtemfvmnthjedh
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(properties.getProperty("mail.sender"), properties.getProperty("mail.authentication"));
        }
    };
//    public static void main(String[] args) throws MessagingException {
//        EmailBo emailBo = new EmailBo();
//        emailBo.setCostTime("120");
//        emailBo.setEmail("1378153933@qq.com");
//        emailBo.setStartTime("2022-09-20 19:00:00");
//        emailBo.setExamName("JAVA入职考试");
////        //配置服务器
////        properties.put("mail.smtp.host","smtp.qq.com");
////        //配置认证信息
////        properties.put("mail.smtp.auth","true");
////        //ssl验证
////        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
////        //ssl端口号
////        properties.put("mail.smtp.socketFactory.port","465");
//        //配置验证信息
//        sendExamEmail(emailBo);
//    }

    public static void sendExamEmail(EmailBo emailBo) throws MessagingException {
        //连接服务器,取得一次会话连接
        Session session = Session.getInstance(properties, authenticator);
        //创建邮件信息
        //mime 是 多用途互联网邮件扩展类型(multi internet mail extend)
        MimeMessage message = new MimeMessage(session);
        //设置发件人
        message.setFrom(new InternetAddress(properties.getProperty("mail.sender")));
        //设置邮件主题
        message.setSubject("考试信息");
        //设置收件人
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailBo.getEmail()));
        String content = emailBo.getExamName() + "将于" + emailBo.getStartTime() + "开始,考试时长" + emailBo.getCostTime() + "分钟,请准时参加";
        //设置邮件内容
        message.setText(content);
        //发送邮件
        Transport.send(message);
    }
}
