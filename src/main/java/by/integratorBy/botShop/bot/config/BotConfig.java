package by.integratorBy.botShop.bot.config;

import by.integratorBy.botShop.bot.client.keyboard.inline.ClientInlineKeyboardSource;
import by.integratorBy.botShop.bot.client.keyboard.reply.ClientReplyKeyboardSource;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class BotConfig {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();

        reloadableResourceBundleMessageSource.setBasename("classpath:messages");
        reloadableResourceBundleMessageSource.setDefaultEncoding("UTF-8");

        return reloadableResourceBundleMessageSource;
    }

    @Bean
    public ClientInlineKeyboardSource clientInlineKeyboardSource() {
        return new ClientInlineKeyboardSource();
    }

    @Bean
    public ClientReplyKeyboardSource clientReplyKeyboardSource() {
        return new ClientReplyKeyboardSource();
    }
}
