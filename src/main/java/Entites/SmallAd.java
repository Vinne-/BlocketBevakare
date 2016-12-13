package Entites;

import java.time.LocalDateTime;

/**
 * Object represeneting a undetailed blocket add.
 *
 * Created by Vincent on 2016-08-24.
 */
public class SmallAd {

    private String id;
    private String title;
    private String url;
    private LocalDateTime datetime;
    private int price;

    public SmallAd(String id, String title, String url, LocalDateTime dateTime, int price) {

        this.id = id;
        this.title = title;
        this.setUrl(url);
        this.datetime = dateTime;
        this.price = price;

    }


    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getLocalDatetime() {
        return datetime;
    }

    public int getPrice() {
        return price;
    }


    @Override
    public String toString() {
        return String.format("id: %s, title: %s, datetime: %s, price: %2d", id, title, datetime.toString(), price);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}