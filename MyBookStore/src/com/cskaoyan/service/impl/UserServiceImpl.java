package com.cskaoyan.service.impl;

import com.cskaoyan.bean.User;
import com.cskaoyan.dao.UserDao;
import com.cskaoyan.dao.impl.UserDaoImpl;
import com.cskaoyan.service.UserService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.SQLException;
import java.util.Properties;

public class UserServiceImpl implements UserService {

    UserDao dao = new UserDaoImpl();

    @Override
    public boolean AddUserService(User user) throws SQLException {
        return dao.addUser(user);
    }

    @Override
    public boolean IsUserNameValidService(String username) throws SQLException {
        return dao.isUserNameValid(username);
    }

    @Override
    public Boolean sendMail(String email, String emailMsg) {
        String from = "wdandroid@163.com"; 				// 邮件发送人的邮件地址
        String to = email; 										// 邮件接收人的邮件地址
        final String username = "wdandroid@163.com";  	//发件人的邮件帐户
        final String password = "lanzhao1234";   					//发件人的邮件密码（不同于登录密码，需要单独设置）


        //定义Properties对象,设置环境信息
        Properties props = System.getProperties();

        //设置邮件服务器的地址
        props.setProperty("mail.smtp.host", "smtp.163.com"); // 指定的smtp服务器
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.transport.protocol", "smtp");//设置发送邮件使用的协议  smtp
        //创建Session对象,session对象表示整个邮件的环境信息
        Session session = Session.getInstance(props);
        //设置输出调试信息
        session.setDebug(true);
        try {
            //Message的实例对象表示一封电子邮件
            //text/HTML
            //img/jpeg
            MimeMessage message = new MimeMessage(session);
            //设置发件人的地址
            message.setFrom(new InternetAddress(from));
            //设置主题
            message.setSubject("webstore注册用户激活");
            //设置邮件的文本内容
            //message.setText("Welcome to JavaMail World!");
            message.setContent((emailMsg),"text/html;charset=utf-8");
            //从session的环境中获取发送邮件的对象
            Transport transport=session.getTransport();
            //连接邮件服务器
            transport.connect("smtp.163.com",25, username, password);
            //设置收件人地址,并发送消息
            transport.sendMessage(message,new Address[]{new InternetAddress(to)});
            transport.close();
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User IsUserExistsService(String username, String password) throws SQLException {
        return dao.isUserExist(username,password);
    }

    @Override
    public boolean UpdateUserService(String uid, String nickname, String password, String email, String birthday) throws SQLException {
        return dao.updateUser(uid,nickname,password,email,birthday);
    }
}
