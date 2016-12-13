package gUI.controllers;

import Entites.ScrapeJob;
import Entites.ScrapeJobListCell;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;

/**
 * Created by Vince on 2016-12-06.
 */
public class ScrapeJobOverviewController implements Initializable {

    private ObservableList<ScrapeJob> scrapejobs = FXCollections.observableArrayList();
    private ObservableList<String> stringlist = FXCollections.observableArrayList();

    @FXML
    private ListView<ScrapeJob> scrapeJobList;

    @FXML
    private Button scrapeJobBtn;

    @FXML
    void onAddScrapeJobHandler(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(("FXML/scrapeJobDetails.fxml")));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Scrape Job Details");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)event.getSource()).getScene().getWindow());

            ScrapeJobDetailsController controller = loader.<ScrapeJobDetailsController>getController();
            controller.initData(new Callback() {
                @Override
                public Object call(Object param) {
                    if (param instanceof ScrapeJob) {
                        scrapejobs.add((ScrapeJob) param);
                    }
                    return null;
                }
            });

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrapeJobList.setItems(scrapejobs);
        //scrapeJobList.setItems(stringlist);

        scrapeJobList.setCellFactory(new Callback<ListView<ScrapeJob>, ListCell<ScrapeJob>>() {

            @Override
            public ListCell<ScrapeJob> call(ListView<ScrapeJob> param) {
                return new ScrapeJobListCell();
            }

        });

        scrapeJobList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ScrapeJob>() {
            @Override
            public void changed(ObservableValue<? extends ScrapeJob> observable, ScrapeJob oldValue, ScrapeJob newValue) {
                ScrapeJob selectedItem = scrapeJobList.getSelectionModel().getSelectedItem();
                System.out.println(selectedItem);
            }
        });
        scrapeJobList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        });

        /*
        scrapeJobList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new XCell();
            }
        });
         */

    }

    public void startScrapingHandler(ActionEvent actionEvent) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(("FXML/scrapeing.fxml")));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Scraping");
            ScrapingController controller = loader.<ScrapingController>getController();
            controller.setupScraping(scrapejobs);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class XCell extends ListCell<String> {

        private HBox hbox = new HBox();
        private Label label = new Label("(empty)");
        private Pane pane = new Pane(new VBox(new Label("TEst"), new Label("test 2")));
        private Button button = new Button("(>)");
        private String lastItem;

        public XCell() {
            super();
            hbox.getChildren().addAll(label, pane, button);
            HBox.setHgrow(pane, Priority.ALWAYS);
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println(lastItem + " : " + event);
                }
            });
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);  // No text in label of super class
            if (empty) {
                lastItem = null;
                setGraphic(null);
            } else {
                lastItem = item;
                label.setText(item!=null ? item : "<null>");
                setGraphic(hbox);
            }
        }

    }

}
