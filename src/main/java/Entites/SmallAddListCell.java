package Entites;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Created by Vince on 2016-12-10.
 */
public class SmallAddListCell extends ListCell<SmallAd> {

    private HBox hbox = new HBox();
    private VBox vbox = new VBox();
    private Pane spacer = new Pane();
    private Label title = new Label("title");
    private Label price = new Label("price");
    private Label date = new Label("date");

    public SmallAddListCell() {
        super();
        hbox.getChildren().addAll(title, spacer, price);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        vbox.getChildren().addAll(hbox, date);
    }

    @Override
    protected void updateItem(SmallAd item, boolean empty) {
        super.updateItem(item, empty);
        if(empty) {
            setGraphic(null);
            setText(null);
        } else {
            title.setText(item.getTitle());
            price.setText(String.valueOf(item.getPrice()));
            date.setText(item.getLocalDatetime().toString());
            setGraphic(vbox);
        }

    }
}
