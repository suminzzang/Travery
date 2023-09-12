package com.ssafy.travery.model.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ssafy.travery.model.MailCheckDto;
import com.ssafy.travery.model.mapper.MailCheckMapper;

@Service
public class MailCheckServiceImpl implements MailCheckService{
	
	private MailCheckMapper mailCheckMapper;
	
	private JavaMailSender sender = getSender();
	
	public MailCheckServiceImpl(MailCheckMapper mailCheckMapper) {
		this.mailCheckMapper = mailCheckMapper;
	}

	@Override
	public String getValue(Map<String, String> map) throws Exception {
		return mailCheckMapper.getValue(map);
	}

	@Override
	public int setKey(MailCheckDto mailCheckDto) throws Exception {
		return mailCheckMapper.setKey(mailCheckDto);
	}

	@Override
	public int removeKey(String token) throws Exception {
		return mailCheckMapper.removeKey(token);
	}

	@Override
	public String getSalt(String mailKey) throws Exception {
		return mailCheckMapper.getSalt(mailKey);
	}

	@Override
	public void setSalt(Map<String, String> map) throws Exception {
		mailCheckMapper.setSalt(map);
	}

	@Override
	public void sendMail(String email, String key) {
	    MimeMessage msg = sender.createMimeMessage();
	    MimeMessageHelper helper;
	    try {
	        helper = new MimeMessageHelper(msg, true, "UTF-8");
	        helper.setSubject("[Travery] 회원가입 인증 메일입니다.");
	        helper.setTo(new InternetAddress(email));
	        helper.setFrom("noreply@travery.com");
	        helper.setText(mailContent(key), true);
	        sender.send(msg);
	    } catch (MessagingException e) {
	        e.printStackTrace();
	    }
	}
	
	@Override
	public void sendFindIdMail(String email, String userId) {
	    MimeMessage msg = sender.createMimeMessage();
	    MimeMessageHelper helper;
	    try {
	        helper = new MimeMessageHelper(msg, true, "UTF-8");
	        helper.setSubject("[Travery] 아이디 정보 메일입니다.");
	        helper.setTo(new InternetAddress(email));
	        helper.setFrom("noreply@travery.com");
	        helper.setText(mailContentFindId(userId), true);
	        sender.send(msg);
	    } catch (MessagingException e) {
	        e.printStackTrace();
	    }
	}
	
	@Override
	public void sendFindPwMail(String email, String key) {
	    MimeMessage msg = sender.createMimeMessage();
	    MimeMessageHelper helper;
	    try {
	        helper = new MimeMessageHelper(msg, true, "UTF-8");
	        helper.setSubject("[Travery] 비밀번호 찾기 인증 메일입니다.");
	        helper.setTo(new InternetAddress(email));
	        helper.setFrom("noreply@travery.com");
	        helper.setText(mailContent(key), true);
	        sender.send(msg);
	    } catch (MessagingException e) {
	        e.printStackTrace();
	    }
	}
	
	private JavaMailSenderImpl getSender() {
		InputStream inputStream = MailCheckServiceImpl.class.getResourceAsStream("/email.txt");
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost("smtp.gmail.com");
		sender.setPort(587);
		try {
			sender.setUsername(reader.readLine());
			sender.setPassword(reader.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	    Properties props = new Properties();
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "false");
	    props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
	    props.put("mail.smtp.ssl.protocols", "TLSv1.2");
	    sender.setJavaMailProperties(props);
	    
		return sender;
	}
	
	private String mailContent(String key) {
		StringBuilder content = new StringBuilder();
		content.append("<h2>Travery</h2></br></br>")
		.append("인증 번호는 ").append(key).append(" 입니다.");
		
		return content.toString();
	}
	
	private String mailContentFindId(String userId) {
		StringBuilder content = new StringBuilder();
		content.append("<h2>Travery</h2></br></br>")
		.append("아이디: ").append(userId);
		
		return content.toString();
	}
}
