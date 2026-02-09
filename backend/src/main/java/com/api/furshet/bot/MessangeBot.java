package com.api.furshet.bot;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;

@Component
@AllArgsConstructor
public class MessangeBot implements SpringLongPollingBot{
    private final UpdateConsumer updateConsumer;

    @Override
    public String getBotToken() {
        return "8371785741:AAGdnbUtvTnQjSKk0VXKyVbnHuZdiqsTL4Q";
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return updateConsumer;
    }
}
