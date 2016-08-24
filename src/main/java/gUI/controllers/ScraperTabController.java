package gUI.controllers;

import Entites.SmallAd;
import bevakare.BlocketScraper;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import notification.Notification;
import org.apache.commons.validator.routines.UrlValidator;

import java.awt.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

/**
 * Created by simpa2k on 2016-08-24.
 */
public class ScraperTabController {
    private static final String BASE_URL = "https://www.blocket.se/";
    private boolean run = false;
    private int scrapeInterval = 60;

    //private ArrayList<Notification> notifications = new ArrayList<>();
    private ArrayList<SmallAd> smallAds = new ArrayList<>();

    @FXML
    private TextField urlInput;

    @FXML
    private void watch() {
        String url = urlInput.getText();
        if(urlIsValid(url)) {
            run = true;

            BlocketScraper scraper = new BlocketScraper(url);

            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    scraper.scrapeNewest(o -> {
                        SmallAd smallAd = (SmallAd) o;
                        System.out.println(smallAd.getDatetime());
                        checkAdDateTime(smallAd);
                    });
                }
            }, 0, 60 * 1000);
        } else {
            showInvalidUrlDialog();
        }
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

    private void checkAdDateTime(SmallAd smallAd) {

        if(smallAds.isEmpty() || smallAd.compareTo(smallAds.get(smallAds.size() - 1)) > 0) {
            //scraper.scrapeUntil(smallAd.getDatetime());
        }
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

}
