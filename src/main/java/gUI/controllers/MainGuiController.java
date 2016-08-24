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
    private Button tabButton;
    @FXML
    private TabPane tabPane;

    private ArrayList<Tab> tabs = new ArrayList<Tab>();

    @FXML
    private void addScraperTab(ActionEvent event) {
       System.out.println("Clicked");

       Tab tab = new ScraperTab();
       tabPane.getTabs().add(tabPane.getTabs().size() - 1, tab);
    }



}
