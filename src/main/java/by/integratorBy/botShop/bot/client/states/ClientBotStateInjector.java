package by.integratorBy.botShop.bot.client.states;

import by.integratorBy.botShop.bot.client.service.ClientMessageService;
import by.integratorBy.botShop.interfaces.BotStateInjector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ClientBotStateInjector implements BotStateInjector<ClientBotState, ClientBotContext> {

    @Autowired
    private ClientMessageService clientMessageService;

    @Override
    @PostConstruct
    public void inject() {
        ClientBotState.setClientMessageService(clientMessageService);
    }
}
