package bevakare;

import Entites.SmallAd;
import Interfaces.ScrapingCallback;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import java.time.LocalDateTime;
import java.util.List;

/**
 * A threaded implementation of the BlocketScraperCore methods
 * The results are returned via the callback
 *
 *
 * Created by Vincent on 2016-08-30.
 * Date: 2016-08-30
 * @author Vincent
 */
public class BlocketScraperCallbackImpl {

    private BlocketScraperCore scraper = new BlocketScraperCore();
    private final String url;

    public BlocketScraperCallbackImpl(String url) {
        this.url = url;
    }

    /**
     * Scrapes in a new tread, Result is returned via callback
     *
     * @param callback
     */
    public void scrapeNewest(ScrapingCallback callback) {

        Task<SmallAd> task = new Task<SmallAd>() {
            @Override
            protected SmallAd call() throws Exception {
                return scraper.scrapeNewest(url);
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

    /**
     * Scrapes in a new tread, Result is returned via callback
     *
     * @param callback
     */
    public void scrapeUntil(LocalDateTime date, ScrapingCallback callback) {

        Task<List<SmallAd>> task = new Task<List<SmallAd>>() {
            @Override
            protected List<SmallAd> call() throws Exception {
                return scraper.scrapeUntil(url, date);
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

    /**
     * Scrapes in a new tread, Result is returned via callback
     *
     * @param callback
     */
    public void scrapeAll(ScrapingCallback callback) {

        Task<List<SmallAd>> task = new Task<List<SmallAd>>() {
            @Override
            protected List<SmallAd> call() throws Exception {
                return scraper.scrapeAll(url);
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




}
