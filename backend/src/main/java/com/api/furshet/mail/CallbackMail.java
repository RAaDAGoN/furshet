package com.api.furshet.mail;

import com.api.furshet.domain.entity.CallbackRequest;
import com.api.furshet.domain.entity.Feedback;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CallbackMail {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendOrderEmail(List<String> to, CallbackRequest callback) {
        // Подготавливаем контекст для Thymeleaf
        Context context = new Context();
        context.setVariable("callback", callback);

        // Генерируем тело письма из шаблона
        String body = templateEngine.process("callback-email", context);

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("furshetnotificaton@yandex.ru");
            helper.setTo(to.toArray(new String[0]));
            helper.setSubject("Новая заявка на звонок");
            helper.setText(body, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Ошибка при отправке email", e);
        }
    }
}
