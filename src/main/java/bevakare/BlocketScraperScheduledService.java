package bevakare;


import Entites.SmallAd;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

import java.time.LocalDateTime;
import java.util.List;

/**
 * A ScheduledService for continous SmallAd scraping of defined url.
 * It will scrape Ads newer then the timepoint of it being created
 *
 * Date: 2016-08-30
 * @author Vincent
 */
public class BlocketScraperScheduledService extends ScheduledService<List<SmallAd>> {

    private BlocketScraperCore scraper = new BlocketScraperCore();
    private final String url;
    private LocalDateTime dateTime;

    public BlocketScraperScheduledService(String url, LocalDateTime startDateTime) {
        System.out.println("in FxService Contstuctor");
        this.url = url;
        this.dateTime = startDateTime;
    }

    public BlocketScraperScheduledService(String url) {
        this(url, LocalDateTime.now());
    }

    @Override
    protected Task<List<SmallAd>> createTask() {

        return new Task<List<SmallAd>>() {
            @Override
            protected List<SmallAd> call() throws Exception {

                List<SmallAd> result = scraper.scrapeUntil(url, dateTime);
                if(!result.isEmpty()) {
                    dateTime = result.get(0).getLocalDatetime().plusSeconds(1);
                }

                return result;
            }
        };

    }

}
