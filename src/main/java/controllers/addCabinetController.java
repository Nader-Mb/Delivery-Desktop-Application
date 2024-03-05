package controllers;

import models.Cabinet;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import services.CabinetService;
import utils.MyDatabase;


public class addCabinetController implements Initializable {

    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.(com|tn)$";
    String codePostal="[0-9]";

    @FXML
    private AnchorPane addCabinetPane;

    @FXML
    private Button btnAddCabinet;

    @FXML
    private Button btnClearCabinet;

    @FXML
    private TextField txtAdresseCabinet;

    @FXML
    private TextField txtAdresseMailCabinet;

    @FXML
    private TextField txtCodePostalCabinet;

    @FXML
    private TextField txtMedecinCabinet;

    @FXML
    private TextField txtNomCabinet;

    @FXML
    private Label numberCodePostalInputError;
    int numberCodePostal = 0;


    private static final int MAX_DIGITS = 5;
    @FXML
    void numberCodePostalTypedInput(KeyEvent event) {
        String numberText = ((TextField) event.getSource()).getText();
        if (!numberText.matches("-?\\d+")) {
            numberCodePostalInputError.setText("Le nombre doit être valide.");
            numberCodePostal = 0;
        } else {
            int number = Integer.parseInt(numberText);
            if (number < 0) {
                numberCodePostalInputError.setText("Le nombre doit être positive.");
                numberCodePostal = 0;
            } else {
                if(((TextField) event.getSource()).getText().length() >= MAX_DIGITS) {
                    numberCodePostalInputError.setText("Le nombre ne doit pas passer 4 chiffres.");
                    numberCodePostal = 0;
                }else{
                    numberCodePostalInputError.setText(" ");
                    numberCodePostal = 1;
                }
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        numberCodePostalInputError.setText("");
    }


    @FXML
    private void AjoutCabinet(ActionEvent event) {
        //check if not empty
        if(event.getSource() == btnAddCabinet){
            if (txtMedecinCabinet.getText().isEmpty() || txtCodePostalCabinet.getText().isEmpty() || txtAdresseMailCabinet.getText().isEmpty()
                    || txtAdresseCabinet.getText().isEmpty() || txtNomCabinet.getText().isEmpty())
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Information manquante");
                alert.setHeaderText(null);
                alert.setContentText("Vous devez remplir tous les détails concernant votre Cabinet.");
                Optional<ButtonType> option = alert.showAndWait();

            } else if (!this.txtAdresseMailCabinet.getText().matches(emailRegex)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Format email incorrect");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir un email valide !");
            alert.showAndWait();

        }
            else if(!txtCodePostalCabinet.getText().matches("\\d{4}")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Format du code invalide");
                alert.setHeaderText(null);
                alert.setContentText("Format de code postal invalide !");
                alert.showAndWait();
            }

            else {
                ajouterCabinet();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Ajouté avec succès");
                alert.setHeaderText(null);
                alert.setContentText("Votre Cabinet a été ajoutée avec succès.");
                Optional<ButtonType> option = alert.showAndWait();

                try {
                    Parent fxml= FXMLLoader.load(getClass().getResource("/listCabinet.fxml"));
                    addCabinetPane.getChildren().removeAll();
                    addCabinetPane.getChildren().setAll(fxml);
                }
                catch (IOException e) {
                    System.err.println(e.getMessage());
                }

                clearFieldsCabinet();

            }
        }
        if(event.getSource() == btnClearCabinet){
            clearFieldsCabinet();
        }
    }

    @FXML
    private void clearFieldsCabinet() {
        txtMedecinCabinet.clear();
        txtCodePostalCabinet.clear();
        txtAdresseMailCabinet.clear();
        txtAdresseCabinet.clear();
        txtNomCabinet.clear();
    }

    private void ajouterCabinet() {

        // From Formulaire
        String nomCab = txtNomCabinet.getText();
        String adresseCab = txtAdresseCabinet.getText();
        int codePostalCab = Integer.parseInt(txtCodePostalCabinet.getText());
        String adresseMailCab = txtAdresseMailCabinet.getText();
        int id_medCab = Integer.parseInt(txtMedecinCabinet.getText());


        Cabinet cab = new Cabinet(
                nomCab, adresseCab, codePostalCab, adresseMailCab, id_medCab);
        CabinetService as = new CabinetService();
        as.ajouter(cab);
    }


//    @FXML
//    void naviguezVersAffichage() {
//        try {
//            Parent root = FXMLLoader.load(getClass().getResource("/gui/listCabinet.fxml"));
//            txtNomCabinet.getScene().setRoot(root);
//        } catch (IOException e) {
//            System.err.println(e.getMessage());
//        }
//    }
}
