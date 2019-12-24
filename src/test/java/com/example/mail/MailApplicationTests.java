package com.example.mail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;
import java.io.File;
import java.util.Date;

@SpringBootTest
class MailApplicationTests {

    @Autowired
    JavaMailSender javaMailSender;

    @Test
    public void contextLoads() {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setSubject("这是测试邮件主题");
        msg.setText("这是测试邮件内容");
        msg.setFrom("872979493@qq.com");

        msg.setSentDate(new Date());

        msg.setTo("m13340110601@163.com");
        javaMailSender.send(msg);
    }

    @Test
    public void test1() throws MessagingException{
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg,true);
        helper.setSubject("这是测试邮件主题(带附件)");
        helper.setText("这是测试邮件内容（带附件）");
        msg.setFrom("872979493@qq.com");
        msg.setSentDate(new Date());
        helper.setTo("m13340110601@163.com");

        helper.addAttachment("yang.jpg", new File("D:\\桌面\\yang.jpg"));

        javaMailSender.send(msg);
    }

    @Test
    public void test2() throws MessagingException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg,true);
        helper.setSubject("这是测试邮件主题(带图片)");
        helper.setText("这是测试邮件内容（带图片）,这是第一张图片：<img src = 'cid:p01'/>,这是第二张图片:<img src = 'cid:p02'/>",true);
        msg.setFrom("872979493@qq.com");
        msg.setSentDate(new Date());
        helper.setTo("m13340110601@163.com");

        helper.addInline("p01",new FileSystemResource(new File("D:\\桌面\\yang.jpg")));

        helper.addInline("p02",new FileSystemResource(new File("D:\\桌面\\bao.jpeg")));

        javaMailSender.send(msg);
    }

    @Autowired
    TemplateEngine templateEngine;

    @Test
    public void test3() throws MessagingException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg,true);
        helper.setSubject("这是测试邮件主题(thymeleaf)");
        Context context = new Context();
        context.setVariable("username","杨阳");
        context.setVariable("position","java高级工程师");
        context.setVariable("dep","产品研发部");
        context.setVariable("salary",99999);
        context.setVariable("joblevel","高级工程师");
        String process = templateEngine.process("mail.html",context);
        helper.setText(process,true);

        msg.setFrom("872979493@qq.com");
        msg.setSentDate(new Date());
        helper.setTo("m13340110601@163.com");
        javaMailSender.send(msg);
    }




}
