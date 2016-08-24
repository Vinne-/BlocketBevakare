package gUI.tab;

import Entites.SmallAd;
import bevakare.BlocketScraper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.WeakHashMap;

/**
 * Created by simpa2k on 2016-08-24.
 */
public class ScraperTab extends Tab {

    private TextField urlInput = new TextField();

    public ScraperTab() {
        super("Ny bevakning");
        GridPane gp = new GridPane();

        VBox urlAndSounds = new VBox();
        gp.setConstraints(urlAndSounds, 0, 0);
        gp.getChildren().add(urlAndSounds);

        ComboBox<String> sounds = new ComboBox<String>();
        urlAndSounds.getChildren().add(new Text("Address"));
        urlAndSounds.getChildren().add(urlInput);
        urlAndSounds.getChildren().add(new Text("Ljud"));
        urlAndSounds.getChildren().add(sounds);

        Button watchButton = new Button("Bevaka");

        watchButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                watch();
            }
        });

        gp.setConstraints(watchButton, 0, 2);
        gp.getChildren().add(watchButton);

        Button toSiteButton = new Button("Till sidan");

        toSiteButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                toSite();
            }
        });

        gp.setConstraints(toSiteButton, 1, 2);
        gp.getChildren().add(toSiteButton);

        setContent(gp);

    }

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
