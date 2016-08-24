package Entites;

import java.util.Date;

/**
 * Created by Vincent on 2016-08-24.
 */
public class SmallAd {

    private String id;
    private String title;
    private Date dateTime;
    private int price;

    public SmallAd(String id, String title, Date dateTime, int price) {

        this.id = id;
        this.title = title;
        this.dateTime = dateTime;
        this.price = price;

    }


    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public int getPrice() {
        return price;
    }

}
