package by.integratorBy.botShop.bot.client.keyboard.reply;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class ClientReplyKeyboardSource {

    public ReplyKeyboard getAdminMainMenu() {
        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow KeyboardRowOne = new KeyboardRow();
//        KeyboardRow KeyboardRowTwo = new KeyboardRow();
//        KeyboardRow KeyboardRowThree = new KeyboardRow();

        KeyboardRowOne.add(new KeyboardButton("Товары"));
//        KeyboardRowTwo.add(new KeyboardButton(""));
//        KeyboardRowThree.add(new KeyboardButton("Отчётность о работе"));

        keyboardRows.add(KeyboardRowOne);
//        keyboardRows.add(KeyboardRowTwo);
//        keyboardRows.add(KeyboardRowThree);

        replyKeyboardMarkup.setKeyboard(keyboardRows);

        return replyKeyboardMarkup;
    }

}
