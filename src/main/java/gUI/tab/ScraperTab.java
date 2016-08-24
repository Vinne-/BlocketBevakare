package gUI.tab;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

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
        //Scraper scraper = new Scraper();
    }

    @FXML
    private void toSite() {

    }
}
