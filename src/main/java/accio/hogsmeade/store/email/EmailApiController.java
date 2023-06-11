package accio.hogsmeade.store.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/send-mail")
public class EmailApiController {

    private final EmailService emailService;

    @PostMapping("/loginPw")
    public ResponseEntity<?> sendLoginPwMail(@Valid @RequestBody EmailPostRequest request) {
        EmailMessage emailMessage = EmailMessage.builder()
                .to(request.getEmail())
                .subject("[Hogsmeade] 임시 비밀번호 발급")
                .build();
        String loginPw = emailService.sendMail(emailMessage, "loginPw");
        log.debug("loginPw={}", loginPw);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/email")
    public ResponseEntity<?> sendSignupMail(@Valid @RequestBody EmailPostRequest request) {
        EmailMessage emailMessage = EmailMessage.builder()
                .to(request.getEmail())
                .subject("[Hogsmeade] 이메일 인증을 위한 인증 코드 발송")
                .build();

        String code = emailService.sendMail(emailMessage, "email");
        log.debug("code={}", code);
        return ResponseEntity.ok().build();
    }
}
