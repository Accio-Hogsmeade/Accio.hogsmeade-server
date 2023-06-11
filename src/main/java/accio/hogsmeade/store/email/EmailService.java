package accio.hogsmeade.store.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    public String sendMail(EmailMessage emailMessage, String type) {
        String authNum = createCode();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        if (type.equals("loginPw")) {
            // TODO: 2023/06/11 AH-13 임시비밀번호 발급시 회원 비밀번호 업데이트 기능 추가
        }

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(emailMessage.getTo()); //메일 수신자
            mimeMessageHelper.setSubject(emailMessage.getSubject()); //메일 제목
            mimeMessageHelper.setText(setContext(authNum, type), true); //메일 본문 내용, HTML 여부
            javaMailSender.send(mimeMessage);

            log.info("mail send success");

            return authNum;
        } catch (MessagingException e) {
            log.info("mail send fail");
            throw new RuntimeException(e);
        }
    }

    //인증번호 및 임시 비밀번호 생성 메서드
    private String createCode() {
        Random random = new Random();
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(4);
            switch (index) {
                case 0:
                    key.append((char) (random.nextInt(26) + 97));
                    break;
                case 1:
                    key.append((char) (random.nextInt(26) + 65));
                    break;
                default:
                    key.append(random.nextInt(9));
            }
        }
        return key.toString();
    }

    //thymeleaf를 통한 html 적용
    private String setContext(String code, String type) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process(type, context);
    }
}
