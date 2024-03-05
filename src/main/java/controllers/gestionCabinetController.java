package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;


public class gestionCabinetController implements Initializable {
    @FXML
    private Button btnAddCabinet;

    @FXML
    private Button btnListCabinet;

    @FXML
    private AnchorPane gestionCabinetPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void goToPages(ActionEvent event) throws IOException {
        if(event.getSource()== btnAddCabinet){
            Parent fxml= FXMLLoader.load(getClass().getResource("/addCabinet.fxml"));
            gestionCabinetPane.getChildren().removeAll();
            gestionCabinetPane.getChildren().setAll(fxml);
        }else if(event.getSource()==btnListCabinet){
            Parent fxml= FXMLLoader.load(getClass().getResource("/listCabinet.fxml"));
            gestionCabinetPane.getChildren().removeAll();
            gestionCabinetPane.getChildren().setAll(fxml);
        }
    }
}
