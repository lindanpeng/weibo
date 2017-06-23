package com.alphaking.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.alphaking.constant.CommonConstant;
import com.alphaking.model.User;
/**
 * 邮箱工具
 * @author lindanpeng
 *
 */
public class EmailUtil {
private static final String MY_EMAIL_ACCOUNT="lindanpeng@126.com";
private static final String MY_EMAIL_PASSWORD="q382510864";
private static final String MY_EMAIL_STMP_HOST="smtp.126.com";
public static void sendMail(User user) throws Exception{
	  // 1. 创建参数配置, 用于连接邮件服务器的参数配置
    Properties props = new Properties();                    // 参数配置
    props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
    props.setProperty("mail.host", MY_EMAIL_STMP_HOST);        // 发件人的邮箱的 SMTP 服务器地址
    props.setProperty("mail.smtp.auth", "true");            // 请求认证，参数名称与具体实现有关

    // 2. 根据配置创建会话对象, 用于和邮件服务器交互
    Session session = Session.getDefaultInstance(props);
    session.setDebug(true);                                 // 设置为debug模式, 可以查看详细的发送 log

    // 3. 创建一封邮件
    MimeMessage message = createMimeMessage(session, MY_EMAIL_ACCOUNT,user);

    // 4. 根据 Session 获取邮件传输对象
    Transport transport = session.getTransport();

    // 5. 使用 邮箱账号 和 密码 连接邮件服务器
    //    这里认证的邮箱必须与 message 中的发件人邮箱一致，否则报错
    transport.connect(MY_EMAIL_ACCOUNT, MY_EMAIL_PASSWORD);

    // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
    transport.sendMessage(message, message.getAllRecipients());

    // 7. 关闭连接
    transport.close();
}
/**
 * 创建一封只包含文本的简单邮件
 * @param session
 * @param sendMail
 * @param user
 * @return
 * @throws Exception
 */
public static MimeMessage createMimeMessage(Session session, String sendMail,User user) throws Exception {
    // 1. 创建一封邮件
    MimeMessage message = new MimeMessage(session);

    // 2. From: 发件人
    message.setFrom(new InternetAddress(sendMail, "小微网", "UTF-8"));

    // 3. To: 收件人（可以增加多个收件人、抄送、密送）
    message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(user.getUserOpenId(), user.getNickname(), "UTF-8"));

    // 4. Subject: 邮件主题
    message.setSubject("小微用户邮箱激活", "UTF-8");

    // 5. Content: 邮件正文（可以使用html标签）
    String content="点击链接激活账号:http://"+
    CommonConstant.DOMAIN_NAME+"/"+CommonConstant.PROJECT_NAME+
    "/common/finishActivation?email="+user.getUserOpenId()+"&activationCode="+
    user.getActivationCode();  
    message.setContent(content,"text/html;charset=UTF-8");

    // 6. 设置发件时间
    message.setSentDate(new Date());

    // 7. 保存设置
    message.saveChanges();

    return message;
}
}
