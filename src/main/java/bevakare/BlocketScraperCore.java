package bevakare;

import Entites.SmallAd;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Core methods for performing Ad scans on www.blocket.se
 *
 * Date: 2016-08-28
 * @author Vincent
 */
public class BlocketScraperCore {


    /**
     * Scrapes the newest ad at given url.
     *
     * @param url The blocket site to be scanned
     * @return the newest ad. null if no ad was found.
     * @throws IOException at connection error to url
     */
    public SmallAd scrapeNewest(String url) throws IOException {

        Document doc = Jsoup.connect(url).get();

        List<SmallAd> ads = extractSmallAds(doc);
        if(!ads.isEmpty()) {
            return ads.get(0);
        }

        return null;

    }

    /**
     * Scrapes ads with date after the given datetime at given url.
     *
     * @param url blocket site to be scanned
     * @param stop datetime to scrape to.
     * @return ads found
     * @throws Exception at connection error to url
     */
    public List<SmallAd> scrapeUntil(String url, LocalDateTime stop) throws Exception {
        List<SmallAd> result = new ArrayList<>();
        String currentPage = url;

        boolean stopReached = false;
        while (!stopReached) {
            Document doc = Jsoup.connect(currentPage).get();
            List<SmallAd> adsOnPage = extractSmallAds(doc);
            Iterator<SmallAd> iter = adsOnPage.iterator();
            while (iter.hasNext()) {
                SmallAd ad = iter.next();
                if (ad.getLocalDatetime().isBefore(stop)) {
                    iter.remove();
                    stopReached = true;
                }
            }
            result.addAll(adsOnPage);
            if(stopReached) break;
            else {
                if (hasNextPage(doc)) {
                    currentPage = urlQueryReplacer(currentPage, extractNextPageUrlQuery(doc));
                } else {
                    throw new Exception("No more pages, stop date not reached");
                }

            }
        }

        return result;
    }

    /**
     * Scrapes all ads at given url.
     *
     * @param url blocket site to scrape
     * @return ads found
     * @throws IOException  at connection error to url
     */
    public List<SmallAd> scrapeAll(String url) throws IOException {
        List<SmallAd> result = new ArrayList<SmallAd>();
        String currentPage = url;
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


    /**
     * Extracts small ads at given document.
     *
     * @param document the document to be extracted.
     * @return ads found
     */
    private List<SmallAd> extractSmallAds(Document document) {

        List<SmallAd> result = new ArrayList<>();

        Elements articles = document.select("article.item_row");


        for (Element element : articles) {
            String id = element.id();
            String title = element.select("h1[itemprop='name']").select("a").html();
            LocalDateTime datetime = extractLocalDatetime(element.select("time[datetime]").attr("datetime"));
            int price = extractPrice(element.select("p[itemprop='price'").html());

            SmallAd ad = new SmallAd(id, title, datetime, price);
            result.add(ad);
        }

        return result;
    }

    /**
     * extracts LocalDatetime from string of pattern "uuuu-MM-dd HH:mm:ss"
     *
     * @param dateTime datetime represenation
     * @return Localdatetime
     */
    private LocalDateTime extractLocalDatetime(String dateTime) {
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss"));

        /**
         try {
         result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime);
         } catch (ParseException e) {
         e.printStackTrace();
         }
         return result != null ? result : new Date();
         **/
    }


    /**
     * Extracts the price of string. If no price could be extracted,
     * ie no price is defined or price string cannot be parsed, -1 will be returned.
     *
     * @param price the price represented as string
     * @return the price or -1 if price could not be extracted.
     */
    private int extractPrice(String price) {
        try {
            price = price.replaceAll("[^\\d.]", "");
            int i = Integer.valueOf(price);
            return i;
        } catch (NumberFormatException e) {
            return -1;
        }

    }

    /**
     * Extracts the next page url Query parameters
     *
     * @param document
     * @return
     */
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

    /**
     * Checks if the document has a next page.
     */
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

    /**
     * Replaces t url query string.
     *
     * @param url
     * @param newUrlQuery
     * @return
     */
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
}