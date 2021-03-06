package by.integratorBy.botShop.service;

import by.integratorBy.botShop.bot.Bot;
import by.integratorBy.botShop.model.User;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Service
public class MessageService {
    public final static Long MESSAGE_ACTION_EXPIRATION = 172800L;
    public final static String DEFAULT_PARSE_MODE = "HTML";
    public final static String FIRSTNAME_PLACEHOLDER = "#{name}";
    public final static String EVENT_TRIAL_FIRSTNAME_PLACEHOLDER = "{name}";

    @Autowired
    private Bot bot;

    public Message sendMessage(SendMessage sendMessage) throws TelegramApiException {
        return bot.execute(sendMessage);
    }

    public Message sendPhoto(SendPhoto sendPhoto) throws TelegramApiException {
        return bot.execute(sendPhoto);
    }

    public Message sendDocument(SendDocument sendDocument) throws TelegramApiException {
        return bot.execute(sendDocument);
    }

    public Message sendVoice(SendVoice sendVoice) throws TelegramApiException {
        return bot.execute(sendVoice);
    }

    public Message sendVideo(SendVideo sendVideo) throws TelegramApiException {
        return bot.execute(sendVideo);
    }

    public Message sendVideoNote(SendVideoNote sendVideoNote) throws TelegramApiException {
        return bot.execute(sendVideoNote);
    }

    public List<Message> sendMediaGroup(SendMediaGroup sendMediaGroup) throws TelegramApiException {
        return bot.execute(sendMediaGroup);
    }

    @SneakyThrows
    public void removeInlineKeyboard(User user, Integer messageId, Integer messageDate) {
        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();

        editMessageReplyMarkup.setChatId(user.getChatId());
        editMessageReplyMarkup.setMessageId(messageId);
        editMessageReplyMarkup.setReplyMarkup(null);

        editMessageReplyMarkup(editMessageReplyMarkup, messageDate);
    }

    @SneakyThrows
    public void editInlineKeyboard(User user, InlineKeyboardMarkup inlineKeyboardMarkup, Integer messageId, Integer messageDate) {
        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();

        editMessageReplyMarkup.setChatId(user.getChatId());
        editMessageReplyMarkup.setMessageId(messageId);
        editMessageReplyMarkup.setReplyMarkup(inlineKeyboardMarkup);

        editMessageReplyMarkup(editMessageReplyMarkup, messageDate);
    }

    public void setMessageIdAndDate(User user, Message message) {
        user.setBotLastMessageId(message.getMessageId());
        user.setBotLastMessageDate(message.getDate());
        user.setBotLastMessageEditable(message.getReplyMarkup() != null);
    }

    public Boolean isMessageExpired(Integer messageDate) {
        return Instant.now().getEpochSecond() - messageDate >= MESSAGE_ACTION_EXPIRATION;
    }

    public Message editMessageText(EditMessageText editMessageText, Integer messageDate) throws TelegramApiException {
        if (Instant.now().getEpochSecond() - messageDate < MESSAGE_ACTION_EXPIRATION) {
            Serializable result = bot.execute(editMessageText);

            if (result instanceof Message) {
                return (Message) result;
            }
        }
        return null;
    }

    public Message editMessageMedia(EditMessageMedia editMessageMedia, Integer messageDate) throws TelegramApiException {
        if (Instant.now().getEpochSecond() - messageDate < MESSAGE_ACTION_EXPIRATION) {
            Serializable result = bot.execute(editMessageMedia);

            if (result instanceof Message) {
                return (Message)result;
            }
        }
        return null;
    }

    public Message editMessageReplyMarkup(EditMessageReplyMarkup editMessageReplyMarkup, Integer messageDate) throws TelegramApiException {
        if (Instant.now().getEpochSecond() - messageDate < MESSAGE_ACTION_EXPIRATION) {
            Serializable result = bot.execute(editMessageReplyMarkup);

            if (result instanceof Message) {
                return (Message) result;
            }
        }
        return null;
    }

    public Boolean deleteMessage(DeleteMessage deleteMessage, Integer messageDate) throws TelegramApiException {
        if (Instant.now().getEpochSecond() - messageDate < MESSAGE_ACTION_EXPIRATION) {
            return bot.execute(deleteMessage);
        }
        return false;
    }

    @SneakyThrows
    public void deleteBotLastMessage(User user) {
        if (!isMessageExpired(user.getBotLastMessageDate())) {
            DeleteMessage deleteMessage = new DeleteMessage();

            deleteMessage.setChatId(user.getChatId());
            deleteMessage.setMessageId(user.getBotLastMessageId());

            if (deleteMessage(deleteMessage, user.getBotLastMessageDate())) {
                user.setBotLastMessageId(null);
                user.setBotLastMessageDate(null);
            }
        }
    }
  
    @SneakyThrows
    public void sendBanMessage(Long userId) {
        SendMessage sendMessage = new SendMessage();

        sendMessage.setText("<b>Доступ запрещён!</b>");
        sendMessage.setChatId(userId);
        sendMessage.setParseMode("HTML");

        bot.execute(sendMessage);
    }

    @SneakyThrows
    public void sendEnterName(Long userId) {
        SendMessage sendMessage = new SendMessage();

        sendMessage.setText("<b>Введите ваше имя!</b>");
        sendMessage.setChatId(userId);
        sendMessage.setParseMode("HTML");

        bot.execute(sendMessage);
    }

    public Boolean hasFirstname(String message) {
        return message.contains(FIRSTNAME_PLACEHOLDER);
    }

    public Boolean hasEventTrialFirstname(String message) {
        return message.contains(EVENT_TRIAL_FIRSTNAME_PLACEHOLDER);
    }

}
