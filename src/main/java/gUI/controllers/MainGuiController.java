package gUI.controllers;

import Entites.SmallAd;
import Interfaces.ScrapingCallback;
import bevakare.BlocketScraper;
import gUI.tab.ScraperTab;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by simpa2k on 2016-08-24.
 */
public class MainGuiController {

    @FXML
    private TabPane tabPane;

    @FXML
    private void addScraperTab(ActionEvent event) {
       Tab tab = new ScraperTab();
       tabPane.getTabs().add(tabPane.getTabs().size() - 1, tab);
    }

}
