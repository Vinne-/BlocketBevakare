package bevakare;

import Interfaces.ScrapingCallback;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Date;

/**
 * Created by Vincent on 2016-08-24.
 */
public class BlocketScraper {

    private String mUrl;


    public BlocketScraper(String url) {
        mUrl = url;
    }


    public void scrapeNewest() {
        Document doc = null;
        try {
            doc = Jsoup.connect(mUrl).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (doc != null) {

            Elements articles = doc.select("article.item_row");
            if (!articles.isEmpty()) {
                Element el = articles.first();
                String id = el.id();
                String title = el.select("a[title]").attr("title");
                //Date dateTime = el.select("time[datetime]");
                //int price = ;
            }

        }

    }

    private Date extractDate(String dateTime) {

        return new Date();
    }



}
