package watcher;

import Entites.SmallAd;
import bevakare.BlocketScraperCallbackImpl;
import gUI.controllers.ScraperTabController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Represents one period of watching a particular url.
 * Continously makes new scrapes at fixed intervals.
 *
 * Created by simpa2k on 2016-08-26.
 * @author Simon
 */
public class Watcher {

    private String url;
    private ScraperTabController scraperTab;
    private SmallAd lastFoundSmallAd;
    private Timer timer;
    private int scrapeInterval;

    public Watcher(String url, ScraperTabController scraperTab, int scrapeInterval) {

        this.url = url;
        this.scraperTab = scraperTab;
        this.scrapeInterval = scrapeInterval;

    }

    /**
     * Continously makes scrapes by using a timer.
     */

    public void watch() {
        BlocketScraperCallbackImpl scraper = new BlocketScraperCallbackImpl(url);

        timer = new Timer();
        timer.scheduleAtFixedRate(new Scrape(scraper), 0, scrapeInterval);
    }

    /**
     * Cancels the timer.
     */

    /*
     * Should probably be handling an exception in the case
     * that it gets called without the timer being instantiated.
     */

    public void cancel() {
        timer.cancel();
    }

    public LocalDateTime getLastFoundSmallAdDate() {

        if(lastFoundSmallAd != null) {
            return lastFoundSmallAd.getLocalDatetime();
        }
        return null;

    }

    /**
     * Notifies the scraper tab that instantiated the watcher
     * that new ads have been found.
     *
     * @param ads A list of SmallAds returned by the BlocketScraperCallbackImpl
     */

    public void sendNotification(List<SmallAd> ads) {

        scraperTab.notify(ads);

    }

    /**
     * Represents a single scrape of the given url.
     */

    private class Scrape extends TimerTask {

        private BlocketScraperCallbackImpl scraper;

        public Scrape(BlocketScraperCallbackImpl scraper) {
            this.scraper = scraper;
        }

        /**
         * Gets the latest SmallAd and calls checkAdDatetime with it.
         */

        @Override
        public void run() {
            scraper.scrapeNewest(o -> {
                SmallAd smallAd = (SmallAd) o;
                System.out.println(smallAd.getLocalDatetime());

                checkAdDatetime(smallAd);
            });
        }

        /**
         * Checks the date of a newly found SmallAd and compares
         * it with the SmallAd that was last found.
         * If the new SmallAd was posted at a later date
         * it requests all SmallAds up to that date.
         *
         * @param smallAd A SmallAd, the date of which needs to be examined
         */

        private void checkAdDatetime(SmallAd smallAd) {

            if(lastFoundSmallAd == null || smallAd.getLocalDatetime().isAfter(lastFoundSmallAd.getLocalDatetime())) {
                try {
                    scraper.scrapeUntil(smallAd.getLocalDatetime(), o -> {
                        List<SmallAd> ads = (List<SmallAd>) o;
                        Watcher.this.sendNotification(ads);
                    });
                } catch(NullPointerException e) {
                    //This might be necessary if a SocketTimoutException wasn't handled in BlocketScraper
                    e.printStackTrace();
                }
            } else {
                //Handle case where the found ad is also the most recent one

            }
        }

    }

}

