package bevakare;

import Entites.SmallAd;
import Interfaces.ScrapingCallback;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Date;

/**
 * Created by Vincent on 2016-08-24.
 */
public class BlocketScraper {

    private String mUrl;


    public BlocketScraper(String url) {
        mUrl = url;
    }


    public void scrapeNewest(final ScrapingCallback callback) {

        Task<SmallAd> test = new Task<SmallAd>() {
            @Override
            protected SmallAd call() throws Exception {

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
                        String title = el.select("h1[itemprop='name']").select("a").html();
                        Date datetime = extractDate(el.select("time[datetime]").attr("datetime"));
                        int price = extractPrice(el.select("p[itemprop='price'").html());
                        SmallAd ad = new SmallAd(id, title, datetime, price);
                        System.out.println(ad);
                        try {
                            Thread.sleep(2500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return ad;
                    }

                }

                return null;
            }


        };
        test.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            public void handle(WorkerStateEvent event) {
                callback.onScrapingCallback(event.getSource().getValue());
            }
        });
        Thread th = new Thread(test);
        th.setDaemon(true);
        th.start();

        /**
        class ScrapeThread implements Runnable {

            ScrapingCallback callback;

            public ScrapeThread(ScrapingCallback callback) {
                this.callback = callback;
            }

            public void run() {



            }

        }
        **/

        //new ScrapeThread(callback).run();

    }

    private Date extractDate(String dateTime) {
        System.out.println(dateTime);
        return new Date();
    }

    private int extractPrice(String price) {

        price = price.replaceAll("[^\\d.]", "");
        return Integer.valueOf(price);
    }


}
