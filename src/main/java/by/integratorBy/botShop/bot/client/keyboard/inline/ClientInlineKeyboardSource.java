package by.integratorBy.botShop.bot.client.keyboard.inline;

import by.integratorBy.botShop.model.Koton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class ClientInlineKeyboardSource {

    public InlineKeyboardMarkup getInlineButtons(Integer page) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonNext = new InlineKeyboardButton().setText("Дальше");
        InlineKeyboardButton buttonBack = new InlineKeyboardButton().setText("Назад");

        buttonNext.setCallbackData("buttonNext");
        buttonBack.setCallbackData("buttonBack");

        List<InlineKeyboardButton> firstKeyboardButtonRow = new ArrayList<>();
        if (page > 0) {
            firstKeyboardButtonRow.add(buttonBack);
        }
        if (page <= 10) {
            firstKeyboardButtonRow.add(buttonNext);
        }

        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();

        keyboardRows.add(firstKeyboardButtonRow);

        inlineKeyboardMarkup.setKeyboard(keyboardRows);

        return inlineKeyboardMarkup;
    }
}
