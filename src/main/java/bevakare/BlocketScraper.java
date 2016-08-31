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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *
 * Created by Vincent on 2016-08-24.
 */
public class BlocketScraper {

    private String mUrl;


    public BlocketScraper(String url) {
        mUrl = url;
    }


    /**
     * Scrapes the newest small ad. And returns it as a SmallAd object to onScrapingSuccess() callback.
     *
     * @param callback The callback to delegate the resulting SmallAd to.
     */
    public void scrapeNewest(final ScrapingCallback callback) {

        Task<SmallAd> task = new Task<SmallAd>() {
            @Override
            protected SmallAd call() throws Exception {

                Document doc = Jsoup.connect(mUrl).get();

                if (doc != null) {
                    List<SmallAd> ads = extractSmallAds(doc);
                    if(!ads.isEmpty()) {
                        return ads.get(0);
                    }
                }

                return null;
            }


        };

        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            public void handle(WorkerStateEvent event) {
                callback.onScrapingCallback(event.getSource().getValue());
            }
        });

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();

    }

    /**
     * Scrapes SmallAds created later then the given date paramater. Returns
     *
     * @param date
     * @param callback
     */
    public void scrapeUntil(Date date, ScrapingCallback callback) {

        Task<List<SmallAd>> task = new Task<List<SmallAd>>() {
            @Override
            protected List<SmallAd> call() throws Exception {

                Document doc = Jsoup.connect(mUrl).get();

                List<SmallAd> ads = extractSmallAds(doc);
                Iterator<SmallAd> iter = ads.iterator();
                boolean oldAdsRemoved = false;
                while(iter.hasNext()) {
                    SmallAd ad = iter.next();
                    if (ad.getDatetime().before(date)) {
                        iter.remove();
                        oldAdsRemoved = true;
                    }
                }
                if (oldAdsRemoved) {
                    return ads;
                } else {
                    //TODO no ads older then date found, Change to the next page of ads and continue the search.
                }


                return null;
            }
        };

        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                callback.onScrapingCallback(event.getSource().getValue());
            }
        });

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();

    }

    public void scrapeAll(ScrapingCallback callback) {

        Task<List<SmallAd>> task = new Task<List<SmallAd>>() {
            @Override
            protected List<SmallAd> call() throws Exception {

                List<SmallAd> result = new ArrayList<SmallAd>();
                String currentPage = mUrl;
                while (true) {
                    Document doc = Jsoup.connect(currentPage).get();
                    List<SmallAd> adsOnPage = extractSmallAds(doc);
                    result.addAll(adsOnPage);
                    if(hasNextPage(doc)) {
                        currentPage = urlQueryReplacer(currentPage, extractNextPageUrlQuery(doc));
                    } else {
                        break;
                    }
                }

                return result;
            }
        };

        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                callback.onScrapingCallback(event.getSource().getValue());
            }
        });

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();

    }


    private List<SmallAd> extractSmallAds(Document document) {

        List<SmallAd> result = new ArrayList<SmallAd>();

        Elements articles = document.select("article.item_row");


        for (Element element : articles) {
         String id = element.id();
         String title = element.select("h1[itemprop='name']").select("a").html();
         Date datetime = extractDate(element.select("time[datetime]").attr("datetime"));
         int price = extractPrice(element.select("p[itemprop='price'").html());

         SmallAd ad = new SmallAd(id, title, datetime, price);
         result.add(ad);
        }

        return result;
    }

    private Date extractDate(String dateTime) {
        Date result = null;
        try {
            result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result != null ? result : new Date();
    }

    private int extractPrice(String price) {
        price = price.replaceAll("[^\\d.]", "");
        return price.isEmpty() ? -1 : Integer.valueOf(price);
    }

    private boolean hasNextPage(Document document) {
        Elements navLinks = document.select("a.page_nav");
        for (Element link : navLinks) {
            String linkText = link.text();
            if (linkText.equals("Nästa sida »")) {
                return true;
            }
        }
        return false;
    }

    private String urlQueryReplacer(String url, String newUrlQuery) {
        String result = "";
        for (char c : url.toCharArray()) {
            if (c == '?') {
                result += newUrlQuery;
                break;
            }
            else {
                result += c;
            }
        }
        return result;
    }

    private String extractNextPageUrlQuery(Document document) {
        Elements navLinks = document.select("a.page_nav");
        for (Element link : navLinks) {
            String linkText = link.text();
            if (linkText.equals("Nästa sida »")) {
                return link.attr("href");
            }
        }
        return null;
    }


}
