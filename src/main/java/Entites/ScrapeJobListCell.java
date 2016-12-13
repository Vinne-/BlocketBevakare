package Entites;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Created by Vince on 2016-12-08.
 */
public class ScrapeJobListCell extends ListCell<ScrapeJob> {

    private VBox vbox = new VBox();
    private Label name = new Label("empty");
    private Label url = new Label("empty");


    public ScrapeJobListCell() {
        super();
        vbox.getChildren().addAll(name, url);
    }

    @Override
    protected void updateItem(ScrapeJob item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
            setText(null);
        } else {
            name.setText(item.getName());
            url.setText(item.getUrl());
            name.setTextFill(item.getColor());
            url.setTextFill(item.getColor());
            setGraphic(vbox);
        }

    }


}
