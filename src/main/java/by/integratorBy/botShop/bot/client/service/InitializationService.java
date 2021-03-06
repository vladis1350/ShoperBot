package by.integratorBy.botShop.bot.client.service;

import by.integratorBy.botShop.model.Koton;
import by.integratorBy.botShop.parsers.ParserKotonService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
public class InitializationService {

    @Autowired private ParserKotonService parserKotonService;
    private List<Koton> kotonList;

    public void init() {
        kotonList = parserKotonService.parse();
    }
}
