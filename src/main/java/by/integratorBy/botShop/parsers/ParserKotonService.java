package by.integratorBy.botShop.parsers;

import by.integratorBy.botShop.model.Koton;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ParserKotonService {

    public List<Koton> parse() {
        List<Koton> kotons = new ArrayList<>();

        try {
            Document document = Jsoup.connect("https://www.koton.com/en/women/new-season/c/M01-C02-G130?text=&q=%3Arelevance%3AtopCategories%3AAK112&AK112=on#").get();

            Elements divElements = document.getElementsByAttributeValue("class", "wrapper");
            divElements.forEach(element -> {
                try {
                    Element figure = element.child(0);
                    Element aElement = figure.child(0);
                    Element imageContainer = aElement.child(0);
                    Element divImage = imageContainer.child(0);
                    Element item = divImage.child(0);
                    Element swiperWrapper = item.child(0);
                    Element swiperSlider = swiperWrapper.child(1);
                    Element img = swiperSlider.child(0);

                    String imageLink = img.attr("data-src");
                    String title = aElement.attr("title");
                    String price = figure.child(2).child(1).text();
                    kotons.add(new Koton(imageLink, title, price));
                } catch (IndexOutOfBoundsException e) {
                    log.error("Error index");
                }
            });
        } catch (IOException e) {
            log.error("Error connection: " + e.getMessage());
        }
        return kotons;
    }
}
