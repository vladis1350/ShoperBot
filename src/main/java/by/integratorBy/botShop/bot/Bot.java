package by.integratorBy.botShop.bot;

import by.integratorBy.botShop.bot.client.handlers.ClientCommandHandler;
import by.integratorBy.botShop.model.User;
import by.integratorBy.botShop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Configuration
@ConfigurationProperties(prefix = "bot")
public class Bot extends TelegramLongPollingBot {

    @Autowired
    private UserService userService;
    @Autowired
    private ClientCommandHandler clientCommandHandler;

    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;

    @Override
    public void onUpdateReceived(Update update) {
        Long userId = getUserId(update);

        User user = userService.findByChatId(userId);

        if (user == null) {
            userService.createUser(userId, update);
        }
        clientCommandHandler.handle(update);
    }

    @Override
    public String getBotUsername() {
        return this.username;
    }

    @Override
    public String getBotToken() {
        return this.token;
    }

    private Long getUserId(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getFrom().getId().longValue();
        } else if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getFrom().getId().longValue();
        } else if (update.hasEditedMessage()) {
            return update.getEditedMessage().getFrom().getId().longValue();
        }
        return null;
    }
}
