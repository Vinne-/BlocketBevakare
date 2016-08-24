package gUI.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;

/**
 * Created by simpa2k on 2016-08-24.
 */
public class MainGuiController {

    @FXML
    private TabPane tabPane;

    @FXML
    private void addScraperTab(ActionEvent event) {
        try {
            Tab tab = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/scraperTab.fxml"));
            tabPane.getTabs().add(tabPane.getTabs().size() - 1, tab);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }



}
