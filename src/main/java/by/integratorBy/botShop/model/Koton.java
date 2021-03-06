package by.integratorBy.botShop.model;

import lombok.Builder;
import lombok.Data;

@Data
public class Koton {
    private String url;
    private String title;
    private String price;

    public Koton(String url, String title, String price) {
        this.url = url;
        this.title = title;
        this.price = price;
    }
}
