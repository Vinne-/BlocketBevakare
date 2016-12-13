package Entites;

import javafx.scene.paint.Color;

/**
 * Created by Vince on 2016-12-07.
 */
public class ScrapeJob {

    private String name;
    private String url;
    private Color color;

    public ScrapeJob(String name, String url, Color color) {

        this.setName(name);
        this.setUrl(url);
        this.setColor(color != null ? color : Color.BLACK);

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return name + ", " + url + ", " + color;
    }

}
