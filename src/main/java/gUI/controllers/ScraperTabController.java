package gUI.controllers;

import bevakare.BlocketScraper;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import org.apache.commons.validator.routines.UrlValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by simpa2k on 2016-08-24.
 */
public class ScraperTabController {
    private static final String baseUrl = "https://www.blocket.se/";

    @FXML
    TextField urlInput;

    @FXML
    private void watch() {
        String url = urlInput.getText();
        if(urlIsValid(url)) {
            BlocketScraper scraper = new BlocketScraper(url);
        } else {
            showNoUrlDialog();
            /*SmallAd latestAd = scraper.scrapeNewest();*/
        }
    }

    private boolean urlIsValid(String url) {
        boolean matchesBaseUrl = Pattern.matches(baseUrl + ".*", url);

        UrlValidator urlValidator = new UrlValidator();
        return (urlValidator.isValid(url)) && (matchesBaseUrl);
    }

    private void showNoUrlDialog() {
        ButtonType buttonType = new ButtonType("Ok");
        Dialog<String> dialog = new Dialog<>();
        dialog.setContentText("Felaktig url angiven, vänligen försök igen.");
        dialog.getDialogPane().getButtonTypes().add(buttonType);

        dialog.show();
    }

    @FXML
    private void toSite() {

    }

}
