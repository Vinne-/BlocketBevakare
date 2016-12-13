package gUI.controllers;

import Entites.ScrapeJob;
import Entites.SmallAd;
import Entites.SmallAddListCell;
import bevakare.BlocketScraperScheduledService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.util.Callback;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Vince on 2016-12-09.
 */
public class ScrapingController implements Initializable{

    private ObservableList<SmallAd> output = FXCollections.observableArrayList();

    @FXML
    private ListView<SmallAd> scrapeOutputListView;

    @FXML
    private Button stopScrapeingBtn;


    public void stopScrapingHandler(ActionEvent actionEvent) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        scrapeOutputListView.setItems(output);
        scrapeOutputListView.setCellFactory(new Callback<ListView<SmallAd>, ListCell<SmallAd>>() {
            @Override
            public ListCell<SmallAd> call(ListView<SmallAd> param) {
                return new SmallAddListCell();
            }
        });

        scrapeOutputListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SmallAd>() {
            @Override
            public void changed(ObservableValue<? extends SmallAd> observable, SmallAd oldValue, SmallAd newValue) {
                if (Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().browse(new URI(newValue.getUrl()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void setupScraping(List<ScrapeJob> scrapeJobs) {
        for (ScrapeJob job : scrapeJobs) {
            BlocketScraperScheduledService scraper = new BlocketScraperScheduledService(job.getUrl());
            scraper.setOnSucceeded(event -> {
                List<SmallAd> ads = (List<SmallAd>) event.getSource().getValue();
                ads.forEach(smallAd -> {
                    output.add(smallAd);
                });
            });
            scraper.setPeriod(Duration.minutes(4));
            scraper.start();
        }
    }

}
