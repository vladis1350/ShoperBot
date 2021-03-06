package by.integratorBy.botShop.bot.client.states;

import by.integratorBy.botShop.bot.client.handlers.ClientCommandHandler;
import by.integratorBy.botShop.model.Client;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
public class ClientBotContext {
    private final ClientCommandHandler clientCommandHandler;
    private final Client client;
    private final Update update;

    private ClientBotContext(ClientCommandHandler clientCommandHandler, Client client, Update update) {
        this.clientCommandHandler = clientCommandHandler;
        this.client = client;
        this.update = update;
    }

    public static ClientBotContext of(ClientCommandHandler clientCommandHandler, Client client, Update update) {
        return new ClientBotContext(clientCommandHandler, client, update);
    }    
}
