package controllers;

import models.Cabinet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import services.CabinetService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class itemCabinetController implements Initializable {

    @FXML
    private Button btnModifierCabinet;

    @FXML
    private Button btnSupprimerCabinet;

    @FXML
    private AnchorPane itemCabinetPane;

    @FXML
    private Label labelAdresseCabinet;

    @FXML
    private Label labelAdresseMailCabinet;

    @FXML
    private Label labelCodePostalCabinet;

    @FXML
    private Label labelMedecinCabinet;

    @FXML
    private Label labelNomCabinet;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    Cabinet cab;
    public void setData (Cabinet cabinet){
        this.cab = cabinet ;

        labelAdresseCabinet.setText(cabinet.getAdresse());
        labelAdresseMailCabinet.setText(cabinet.getAdresseMail());
        labelCodePostalCabinet.setText(String.valueOf(cabinet.getCodePostal()));
        labelMedecinCabinet.setText(String.valueOf(cabinet.getId_medecin()));
        labelNomCabinet.setText(cabinet.getNom());

    }


    public Cabinet getData (Cabinet cabinet){
        this.cab = cabinet ;
        return this.cab;

    }

    @FXML
    private void supprimerCabinet(ActionEvent event) throws SQLException {
        CabinetService as = new CabinetService();

        // Afficher une boîte de dialogue de confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous vraiment supprimer ce Cabinet ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Récupérer l'ID du Cabinet sélectionnée
            int id = this.cab.getId();

            // Supprimer le cabinet de la base de données
            as.supprimer(id);
            try {
                Parent fxml= FXMLLoader.load(getClass().getResource("/addRdv.fxml"));
                itemCabinetPane.getChildren().removeAll();
                itemCabinetPane.getChildren().setAll(fxml);
            }
            catch (IOException e) {
                System.err.println(e.getMessage());
            }





    }}

    @FXML
    void open_UpdateCabinet()throws IOException{
        Parent fxml= FXMLLoader.load(getClass().getResource("/updateCabinet.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Update Cabinet");
        stage.setScene(new Scene(fxml));
        stage.showAndWait();
    }

}
