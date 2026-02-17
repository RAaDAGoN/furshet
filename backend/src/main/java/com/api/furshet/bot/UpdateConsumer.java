//package com.api.furshet.bot;
//
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
//import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
//import org.telegram.telegrambots.meta.generics.TelegramClient;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class UpdateConsumer implements LongPollingSingleThreadUpdateConsumer {
//    private final TelegramClient telegramClient;
//
//    public UpdateConsumer() {
//        this.telegramClient = new OkHttpTelegramClient("");
//    }
//
//    @Override
//    public void consume(Update update) {
//        if (update.hasMessage()) {
//            String messageText = update.getMessage().getText();
//            Long chatId = update.getMessage().getChatId();
//
//            if (messageText.equals("/start")) {
//                sendReplyKeyboard(chatId);
//
//            } else if (messageText.equals("Узнать id")) {
//                SendMessage message = SendMessage.builder()
//                        .text("ID: " + chatId)
//                        .chatId(update.getMessage().getChatId())
//                        .build();
//                try {
//                    telegramClient.execute(message);
//                } catch (Exception e) {
//                    System.out.printf("Ошибка отправки сообщения: %s\n", e.getMessage());
//                }
//            }
//        }
//
//    }
//
//    private void sendReplyKeyboard(Long chatId) {
//        SendMessage message = SendMessage.builder()
//                .chatId(chatId)
//                .build();
//
//        List<KeyboardRow> rows = new ArrayList<>();
//
//        KeyboardRow row = new KeyboardRow("Узнать id");
//        rows.add(row);
//
//        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(rows);
//        message.setReplyMarkup(replyKeyboardMarkup);
//
//        try {
//            telegramClient.execute(message);
//        } catch (Exception e) {
//            System.out.printf("Ошибка отправки сообщения: %s\n", e.getMessage());
//        }
//    }
//
//    public void sendOrder(String text, Long chatId) {
//        SendMessage message = SendMessage.builder()
//                .text(text)
//                .chatId(chatId)
//                .build();
//        try {
//            telegramClient.execute(message);
//        } catch (Exception e) {
//            System.out.printf("Ошибка отправки сообщения: %s\n", e.getMessage());
//        }
//    }
//}
