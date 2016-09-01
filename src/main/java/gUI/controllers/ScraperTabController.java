package gUI.controllers;

import Entites.SmallAd;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import notification.Notification;
import org.apache.commons.validator.routines.UrlValidator;
import watcher.Watcher;

import java.awt.Desktop;
import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by simpa2k on 2016-08-24.
 */
public class ScraperTabController implements Initializable {
    private static final String BASE_URL = "https://www.blocket.se/";
    private static final String START_WATCH = "Bevaka";
    private static final String END_WATCH = "Avsluta bevakning";

    private Watcher watcher;
    private int scrapeInterval = 60 * 1000;
    private ArrayList<SmallAd> smallAds = new ArrayList<>();

    @FXML
    private Tab scraperTab;

    @FXML
    private TextField urlInput;

    @FXML
    private Button watchButton;

    private EventHandler watchHandler = new EventHandler() {
        @Override
        public void handle(Event event) {
            watchButton.setText(END_WATCH);
            String url = urlInput.getText();

            if(urlIsValid(url)) {
                watcher = new Watcher(url, ScraperTabController.this, scrapeInterval);
                watcher.watch();
            } else {
                showInvalidUrlDialog();
            }
            watchButton.setOnAction(cancelWatchHandler);
        }
    };

    private EventHandler cancelWatchHandler = new EventHandler() {
        @Override
        public void handle(Event event) {
            watcher.cancel();
            watchButton.setText(START_WATCH);
            watchButton.setOnAction(watchHandler);
        }
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        watchButton.setOnAction(watchHandler);
    }

    private boolean urlIsValid(String url) {
        boolean matchesBaseUrl = Pattern.matches(BASE_URL + ".*", url);
        UrlValidator urlValidator = new UrlValidator();

        return (urlValidator.isValid(url)) && (matchesBaseUrl);
    }

    private void showInvalidUrlDialog() {
        ButtonType buttonType = new ButtonType("Ok");
        Dialog<String> dialog = new Dialog<>();
        dialog.setContentText("Felaktig url angiven, vänligen försök igen.");
        dialog.getDialogPane().getButtonTypes().add(buttonType);

        dialog.show();
    }

    @FXML
    private void toSite() {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        String url = urlInput.getText();

        if( (desktop != null) && (desktop.isSupported(Desktop.Action.BROWSE)) && (urlIsValid(url)) ) {
            try {
                desktop.browse(new URI(url));
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void notify(List<SmallAd> ads) {
        Notification notification = new Notification(ads);

        scraperTab.setStyle("-fx-border-color:green");
        scraperTab.setStyle("-fx-background-color:green");
    }

    /*private class Scrape extends TimerTask {

        private BlocketScraper scraper;

        public Scrape(BlocketScraper scraper) {
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
            SmallAd mostRecentSmallAd = smallAds.isEmpty() ? null : smallAds.get(smallAds.size() - 1);

            if(mostRecentSmallAd == null || smallAd.getLocalDatetime().after(mostRecentSmallAd.getLocalDatetime())) {
                try {
                    scraper.scrapeUntil(smallAd.getLocalDatetime(), o -> {
                        List<SmallAd> ads = (List<SmallAd>) o;
                        ScraperTabController.this.notify(ads);
                    });
                } catch(NullPointerException e) {
                    //This might be necessary if a SocketTimoutException wasn't handled in BlocketScraper
                    e.printStackTrace();
                }
            } else {

            }
        }

    }*/

}
