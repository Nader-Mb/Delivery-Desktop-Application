package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class HelloController {
    @FXML
    private AnchorPane view_pages;

    @FXML
    private Button btnCabinet;

    @FXML
    private Button btnRdv;

    @FXML
    public void switchForm(ActionEvent event) throws IOException {
        if(event.getSource()== btnCabinet){
            Parent fxml= FXMLLoader.load(getClass().getResource("/gestionCabinet.fxml"));
            view_pages.getChildren().removeAll();
            view_pages.getChildren().setAll(fxml);
        }else if(event.getSource()==btnRdv){
            Parent fxml= FXMLLoader.load(getClass().getResource("/gestionRdv.fxml"));
            view_pages.getChildren().removeAll();
            view_pages.getChildren().setAll(fxml);
        }
    }
}