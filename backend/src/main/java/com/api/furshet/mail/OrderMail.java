package com.api.furshet.mail;

import com.api.furshet.domain.entity.Order;
import com.api.furshet.domain.entity.OrderItem;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderMail {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendOrderEmail(List<String> to, Order order, List<OrderItem> items, double total) {
        // Подготавливаем контекст для Thymeleaf
        Context context = new Context();
        context.setVariable("order", order);
        context.setVariable("orderItems", items);
        context.setVariable("total", total);

        // Генерируем тело письма из шаблона
        String body = templateEngine.process("order-email", context);

        // Создаём MIME сообщение
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("furshetnotificaton@yandex.ru");
            helper.setTo(to.toArray(new String[0]));
            helper.setSubject("Новый заказ #" + order.getId());
            helper.setText(body, true); // true = HTML
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Ошибка при отправке email", e);
        }
    }
}
