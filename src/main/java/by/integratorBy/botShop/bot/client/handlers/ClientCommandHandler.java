package by.integratorBy.botShop.bot.client.handlers;

import by.integratorBy.botShop.bot.client.service.ClientService;
import by.integratorBy.botShop.bot.client.states.ClientBotContext;
import by.integratorBy.botShop.bot.client.states.ClientBotState;
import by.integratorBy.botShop.model.Client;
import by.integratorBy.botShop.model.User;
import by.integratorBy.botShop.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class ClientCommandHandler {
    private final static Logger logger = LoggerFactory.getLogger(ClientCommandHandler.class);

    @Autowired
    private UserService userService;
    @Autowired
    private ClientService clientService;

    public void handle(Update update) {
        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                processInputMessage(update);
            } 
            else if (update.getMessage().hasContact()) {
                processContactMessage(update);
            } 
            else if (update.getMessage().hasPhoto()) {
                processPhoto(update);
            }
        } 
        else if (update.hasCallbackQuery()) {
            processCallbackQuery(update);
        } 
    }

    private void processInputMessage(Update update) {
        final Long chatId = update.getMessage().getChatId();
        ClientBotContext botContext = null;
        ClientBotState botState = null;

        User user = userService.findByChatId(chatId);
        Client client = user.getClient();

        if (client == null) {
            client = clientService.createClient(user);

            botContext = ClientBotContext.of(this, client, update);
            botState = client.getClientBotState();

            botState.enter(botContext);

            while(!botState.getIsInputNeeded()) {
                if (botState.nextState() != null) {
                    botState = botState.nextState();
                    botState.enter(botContext);
                }
            }
        }
        else {
            botContext = ClientBotContext.of(this, client, update);
            botState = client.getClientBotState();

            logger.info("Update received from admin: " + chatId + ", in state: " + botState + ", with text: " + update.getMessage().getText());

            botState.handleInput(botContext);

            do {
                if (botState.nextState() != null) {
                    botState = botState.nextState();
                    botState.enter(botContext);
                }
                else {
                    break;
                }
            } while (!botState.getIsInputNeeded());
        }

        user.getClient().setClientBotState(botState);
        userService.save(user);
    }

    private void processContactMessage(Update update) {

    }

    private void processPhoto(Update update) {

    }

    private void processCallbackQuery(Update update) {
        final Long chatId = update.getCallbackQuery().getFrom().getId().longValue();
        ClientBotContext botContext = null;
        ClientBotState botState = null;

        User user = userService.findByChatId(chatId);
        Client client = user.getClient();

        if (client == null) {
            client = clientService.createClient(user);

            botContext = ClientBotContext.of(this, client, update);
            botState = client.getClientBotState();

            botState.enter(botContext);

            while(!botState.getIsInputNeeded()) {
                if (botState.nextState() != null) {
                    botState = botState.nextState();
                    botState.enter(botContext);
                }
            }
        }
        else {
            botContext = ClientBotContext.of(this, client, update);
            botState = client.getClientBotState();

            logger.info("CallbackQuery received from admin: " + chatId + ", in state: " + botState + ", with data: " + update.getCallbackQuery().getData());

            botState.handleCallbackQuery(botContext);

            do {
                if (botState.nextState() != null) {
                    botState = botState.nextState();
                    botState.enter(botContext);
                }
                else {
                    break;
                }
            } while (!botState.getIsInputNeeded());
        }

        user.getClient().setClientBotState(botState);
        userService.save(user);
    }
}
