package by.integratorBy.botShop.service;

import by.integratorBy.botShop.model.Client;
import by.integratorBy.botShop.model.User;
import by.integratorBy.botShop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class UserService {
    public final static Integer DEFAULT_PAGE = 1;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User findByChatId(Long chatId) {
        return userRepository.findByChatId(chatId);
    }

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void delete(User user) {
        userRepository.delete(user);
    }

    public void createUser(Long userId, Update update) {
            User user = User.builder()
                    .chatId(userId)
                    .firstname(update.getMessage().getFrom().getFirstName())
                    .lastname(update.getMessage().getFrom().getLastName())
                    .username(update.getMessage().getFrom().getUserName())
                    .botLastMessageDate(0)
                    .botLastMessageId(0)
                    .botLastMessageEditable(false)
                    .currentPage(DEFAULT_PAGE)
                    .build();
            save(user);
    }
}
