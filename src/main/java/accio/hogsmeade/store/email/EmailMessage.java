package accio.hogsmeade.store.email;

import lombok.Builder;
import lombok.Data;

@Data
public class EmailMessage {

    private String to; //수신자
    private String subject; //제목
    private String message; //내용

    @Builder
    public EmailMessage(String to, String subject, String message) {
        this.to = to;
        this.subject = subject;
        this.message = message;
    }
}
