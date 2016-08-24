package gUI.controllers;

import gUI.tab.ScraperTab;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.util.ArrayList;

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
