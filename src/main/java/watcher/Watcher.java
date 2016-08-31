package watcher;

import Entites.SmallAd;
import bevakare.BlocketScraperCallbackImpl;
import gUI.controllers.ScraperTabController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by simpa2k on 2016-08-26.
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

    public void watch() {
        BlocketScraperCallbackImpl scraper = new BlocketScraperCallbackImpl(url);

        timer = new Timer();
        timer.scheduleAtFixedRate(new Scrape(scraper), 0, scrapeInterval);
    }

    public void cancel() {
        timer.cancel();
    }

    public LocalDateTime getLastFoundSmallAdDate() {

        if(lastFoundSmallAd != null) {
            return lastFoundSmallAd.getLocalDatetime();
        }
        return null;

    }

    public void sendNotification(List<SmallAd> ads) {

        scraperTab.notify(ads);

    }

    private class Scrape extends TimerTask {

        private BlocketScraperCallbackImpl scraper;

        public Scrape(BlocketScraperCallbackImpl scraper) {
            this.scraper = scraper;
        }

        @Override
        public void run() {
            scraper.scrapeNewest(o -> {
                SmallAd smallAd = (SmallAd) o;
                System.out.println(smallAd.getLocalDatetime());

                checkAdDatetime(smallAd);
            });
        }

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

