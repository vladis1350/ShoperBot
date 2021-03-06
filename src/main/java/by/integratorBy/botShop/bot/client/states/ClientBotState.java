package by.integratorBy.botShop.bot.client.states;

import by.integratorBy.botShop.bot.client.service.ClientMessageService;
import by.integratorBy.botShop.interfaces.BotState;
import by.integratorBy.botShop.model.Client;
import com.vdurmont.emoji.EmojiParser;
import lombok.Setter;

public enum ClientBotState implements BotState<ClientBotState, ClientBotContext> {
    MainMenu(true) {
        ClientBotState clientBotState = null;

        @Override
        public void enter(ClientBotContext clientBotContext) {
            clientMessageService.sendClientMainMenu(clientBotContext.getClient());
        }

        @Override
        public void handleInput(ClientBotContext clientBotContext) {
            switch (EmojiParser.removeAllEmojis(clientBotContext.getUpdate().getMessage().getText())) {
                case "Товары":
                    clientBotState = GoodList;
                    break;
                default:
                    clientBotState = null;
            }
        }

        @Override
        public ClientBotState nextState() {
            return clientBotState;
        }
    },

    GoodList(true) {
        ClientBotState clientBotState = null;

        @Override
        public void enter(ClientBotContext clientBotContext) {
            clientMessageService.sendClientGoodList(clientBotContext.getClient());
        }

        @Override
        public void handleCallbackQuery(ClientBotContext clientBotContext) {
            String userAnswer = clientBotContext.getUpdate().getCallbackQuery().getData();
            Client client = clientBotContext.getClient();
            Integer page = client.getUser().getCurrentPage();
            if (userAnswer.equals("buttonNext")) {
                client.getUser().setCurrentPage(++page);
            } else if (userAnswer.equals("buttonBack")) {
                if (page > 0) {
                    client.getUser().setCurrentPage(--page);
                }
            }
            clientBotState = GoodList;
        }

        @Override
        public ClientBotState nextState() {
            return clientBotState;
        }
    };

    @Setter
    private static ClientMessageService clientMessageService;

    private final Boolean isInputNeeded;

    ClientBotState(Boolean isInputNeeded) {
        this.isInputNeeded = isInputNeeded;
    }

    public static ClientBotState getInitialState() {
        return MainMenu;
    }

    public Boolean getIsInputNeeded() {
        return isInputNeeded;
    }

    public void handleInput(ClientBotContext clientBotContext) {
    }

    public void handleCallbackQuery(ClientBotContext clientBotContext) {
    }

    public void handleContact(ClientBotContext clientBotContext) {
    }

    public void handlePhoto(ClientBotContext clientBotContext) {
    }

    public void handleVideo(ClientBotContext clientBotContext) {
    }

    public void handleVideoNote(ClientBotContext clientBotContext) {
    }

    public void handleVoice(ClientBotContext clientBotContext) {
    }

    public void handleDocument(ClientBotContext clientBotContext) {
    }

    public abstract void enter(ClientBotContext clientBotContext);

    public abstract ClientBotState nextState();
}
