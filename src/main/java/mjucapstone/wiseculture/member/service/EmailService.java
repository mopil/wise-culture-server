package mjucapstone.wiseculture.member.service;


import lombok.RequiredArgsConstructor;
import mjucapstone.wiseculture.member.exception.MemberException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender emailSender;


    public String sendPasswordResetMail(String emailAddress) {
        // 임시 비밀번호 (랜덤값) 생성
        String randomToken = generateToken();

        try {
            emailSender.send(createMessage(emailAddress, randomToken));
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new MemberException("비밀번호 재설정 메일 발송 오류.");
        }
        return randomToken;
    }

    private MimeMessage createMessage(String emailAddress, String randomToken) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(Message.RecipientType.TO, emailAddress);
        message.setSubject("슬기로운 문화생활 비밀번호 재설정 메일이 도착했습니다.");
        String text="";
        text+= "<div style='margin:100px;'>";
        text+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        text+= "<h3 style='color:blue;'>임시 비밀번호 입니다.해당 비밀번호로 로그인 해주세요.</h3>";
        text+= "<div style='font-size:130%'>";
        text+= "임시 비밀번호 : <strong>";
        text+= randomToken+"</strong><div><br/> ";
        text+= "</div>";
        message.setText(text, "utf-8", "html");
        message.setFrom(new InternetAddress("teamoneauth@gmail.com","슬기로운 문화생활"));
        return message;
    }

    // 임시 비밀번호 생성기
    private String generateToken() {
        StringBuilder key = new StringBuilder();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 임시 비밀번호 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    //  A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }
        return key.toString();
    }
}
