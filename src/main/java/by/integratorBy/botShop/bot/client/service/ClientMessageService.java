package by.integratorBy.botShop.bot.client.service;

import by.integratorBy.botShop.bot.client.keyboard.inline.ClientInlineKeyboardSource;
import by.integratorBy.botShop.bot.client.keyboard.reply.ClientReplyKeyboardSource;
import by.integratorBy.botShop.model.Client;
import by.integratorBy.botShop.model.Koton;
import by.integratorBy.botShop.service.MessageService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.cached.InlineQueryResultCachedPhoto;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ClientMessageService {

    @Autowired
    private ClientLocaleMessageService clientLocaleMessageService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private ClientReplyKeyboardSource clientReplyKeyboardSource;
    @Autowired
    private ClientInlineKeyboardSource clientInlineKeyboardSource;
    @Autowired
    private InitializationService initializationService;

    @SneakyThrows
    public void sendClientMainMenu(Client client) {
        SendMessage sendMessage = new SendMessage();

        sendMessage.setChatId(client.getUser().getChatId());
        sendMessage.setText("Главное меню");
        sendMessage.setReplyMarkup(clientReplyKeyboardSource.getAdminMainMenu());
        sendMessage.setParseMode(MessageService.DEFAULT_PARSE_MODE);

        Message message = messageService.sendMessage(sendMessage);

        messageService.setMessageIdAndDate(client.getUser(), message);
    }


    @SneakyThrows
    public void sendClientGoodList(Client client) {
        Integer page = client.getUser().getCurrentPage();
        List<Koton> kotons = initializationService.getKotonList();
        if (client.getUser().getBotLastMessageEditable() && client.getUser().hasLastBotMessage()
                && !messageService.isMessageExpired(client.getUser().getBotLastMessageDate())) {
//            messageService.deleteBotLastMessage(client.getUser());
            EditMessageMedia editMessageMedia = new EditMessageMedia();
            InputMedia<InputMediaPhoto> inputMedia = new InputMediaPhoto();
            inputMedia.setMedia(kotons.get(page).getUrl());
            inputMedia.setCaption(kotons.get(page).getTitle() + ": " + kotons.get(page).getPrice());
            editMessageMedia.setMedia(inputMedia);
            editMessageMedia.setChatId(client.getUser().getChatId());
            editMessageMedia.setMessageId(client.getUser().getBotLastMessageId());
            editMessageMedia.setReplyMarkup(clientInlineKeyboardSource.getInlineButtons(page));
            Message message = messageService.editMessageMedia(editMessageMedia, client.getUser().getBotLastMessageDate());
            messageService.setMessageIdAndDate(client.getUser(), message);
        } else {
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setPhoto(kotons.get(page).getUrl());
            sendPhoto.setChatId(client.getUser().getChatId());
            sendPhoto.setCaption(kotons.get(page).getTitle());
            sendPhoto.setReplyMarkup(clientInlineKeyboardSource.getInlineButtons(page));
            Message message = messageService.sendPhoto(sendPhoto);
            messageService.setMessageIdAndDate(client.getUser(), message);
        }
//            messageService.setMessageIdAndDate(client.getUser(), message);
    }
}
