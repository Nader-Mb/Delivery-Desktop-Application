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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import services.CabinetService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class updateCabinetController implements Initializable {

    @FXML
    private Button btnClearCabinet;

    @FXML
    private Button btnUpdateCabinet;

    @FXML
    private TextField txtUpdateAdresseCabinet;

    @FXML
    private TextField txtUpdateAdresseMailCabinet;

    @FXML
    private TextField txtUpdateCodePostalCabinet;

    @FXML
    private TextField txtUpdateMedecinCabinet;

    @FXML
    private TextField txtUpdateNomCabinet;

    @FXML
    private AnchorPane updateCabinetPane;

    private int id;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CabinetService cs = new CabinetService();
        List<Cabinet> cabs = cs.Show();

        for(int i=0;i<cabs.size();i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/itemCabinet.fxml"));

            try {
                AnchorPane anchorPane = fxmlLoader.load();
                HBox hBox = (HBox) anchorPane.getChildren().get(0);
                itemCabinetController itemController = fxmlLoader.getController();
                txtUpdateNomCabinet.setText(itemController.getData(cabs.get(i)).getNom());
                txtUpdateAdresseCabinet.setText(itemController.getData(cabs.get(i)).getAdresse());
                txtUpdateCodePostalCabinet.setText(String.valueOf(itemController.getData(cabs.get(i)).getCodePostal()));
                txtUpdateAdresseMailCabinet.setText(itemController.getData(cabs.get(i)).getAdresseMail());
                txtUpdateMedecinCabinet.setText(String.valueOf(itemController.getData(cabs.get(i)).getId_medecin()));
                this.id=itemController.getData(cabs.get(i)).getId();
            } catch (IOException ex) {
                Logger.getLogger(itemCabinetController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    void ModifierCabinet(ActionEvent event) {
            if (txtUpdateNomCabinet.getText().isEmpty() || txtUpdateMedecinCabinet.getText().isEmpty() || txtUpdateCodePostalCabinet.getText().isEmpty()
                    || txtUpdateAdresseMailCabinet.getText().isEmpty() || txtUpdateAdresseCabinet.getText().isEmpty())
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Information manquante");
                alert.setHeaderText(null);
                alert.setContentText("Vous devez remplir tous les détails concernant votre Cabinet.");
                Optional<ButtonType> option = alert.showAndWait();

            } else {
                modifCabinet();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Modifié avec succès");
                alert.setHeaderText(null);
                alert.setContentText("Votre Cabinet a été modifié avec succès.");
                Optional<ButtonType> option = alert.showAndWait();
            }
    }

    @FXML
    void clearFieldsCabinet(ActionEvent event) {
        txtUpdateMedecinCabinet.clear();
        txtUpdateCodePostalCabinet.clear();
        txtUpdateAdresseMailCabinet.clear();
        txtUpdateAdresseCabinet.clear();
        txtUpdateNomCabinet.clear();
    }

    void modifCabinet(){
        String nom = txtUpdateNomCabinet.getText();
        String adresse = txtUpdateAdresseCabinet.getText();
        int codePostal = Integer.parseInt(txtUpdateCodePostalCabinet.getText());
        String adresseMail = txtUpdateAdresseMailCabinet.getText();
        int id_med = Integer.parseInt(txtUpdateMedecinCabinet.getText());


        Cabinet c = new Cabinet(
                id,
                nom, adresse, codePostal, adresseMail, id_med);
        CabinetService cs = new CabinetService();
        cs.modifier(c);
    }
}
