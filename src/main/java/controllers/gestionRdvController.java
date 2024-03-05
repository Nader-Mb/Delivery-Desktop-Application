package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class gestionRdvController implements Initializable {

    @FXML
    private Button btnAddRdv;

    @FXML
    private Button btnListRdv;

    @FXML
    private AnchorPane gestionRdvPane;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void goToPages(ActionEvent event) throws IOException {
        if(event.getSource()== btnAddRdv){
            Parent fxml= FXMLLoader.load(getClass().getResource("/addRdv.fxml"));
            gestionRdvPane.getChildren().removeAll();
            gestionRdvPane.getChildren().setAll(fxml);
        }else if(event.getSource()==btnListRdv){
            Parent fxml= FXMLLoader.load(getClass().getResource("/listRdv.fxml"));
            gestionRdvPane.getChildren().removeAll();
            gestionRdvPane.getChildren().setAll(fxml);
        }
    }
}
