package by.integratorBy.botShop.bot.client.service;

import by.integratorBy.botShop.bot.client.repository.ClientRepository;
import by.integratorBy.botShop.bot.client.states.ClientBotState;
import by.integratorBy.botShop.model.Client;
import by.integratorBy.botShop.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Transactional
    public void save(Client client) {
        clientRepository.save(client);
    }

    @Transactional
    public void delete(Client client) {
        clientRepository.delete(client);
    }

    public Client createClient(User user) {
        Client client = Client.builder()
                .firstname(user.getFirstname())
                .user(user)
                .clientBotState(ClientBotState.getInitialState())
                .surname(user.getLastname())
                .build();

        user.setClient(client);

        return client;
    }
}

