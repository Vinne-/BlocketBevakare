package gUI.controllers;

import bevakare.BlocketScraper;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;

/**
 * Created by simpa2k on 2016-08-24.
 */
public class ScraperTabController {

    @FXML
    TextField urlInput;

    @FXML
    private void watch() {
        String url = urlInput.getText();
        if(url.trim().length() == 0) {
            ButtonType buttonType = new ButtonType("Ok");
            Dialog<String> dialog = new Dialog<>();
            dialog.setContentText("Ingen url angiven, vänligen försök igen");
            dialog.getDialogPane().getButtonTypes().add(buttonType);

            dialog.show();
        } else {
            BlocketScraper scraper = new BlocketScraper(url);
            /*SmallAd latestAd = scraper.scrapeNewest();*/
        }
    }

    @FXML
    private void toSite() {

    }

}
