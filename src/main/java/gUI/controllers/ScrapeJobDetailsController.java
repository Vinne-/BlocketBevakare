package gUI.controllers;

import Entites.ScrapeJob;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Vince on 2016-12-07.
 */
public class ScrapeJobDetailsController implements Initializable{

    private Callback callback;

    @FXML
    private TextField urlTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private ColorPicker jobColorPicker;

    @FXML
    private Button saveBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    void onDeleteHandler(ActionEvent event) {

    }

    @FXML
    void onSaveHandler(ActionEvent event) {
        callback.call(new ScrapeJob(nameTextField.getText(), urlTextField.getText(), jobColorPicker.getValue()));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void initData(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        jobColorPicker.setValue(Color.BLACK);
    }
}
