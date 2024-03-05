package controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import models.DeliveryMan;
import models.User;
import services.*;
import java.io.IOException;
import java.sql.SQLException;

public class AjouterPersonneController implements Initializable {

    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.(com|tn)$";

    userService uService = new userService();

    deliveryManService uDService = new deliveryManService();


    @FXML
    private Label nameCS;
    @FXML
    private TextField idPassword;

    @FXML
    private TextField idUFirstName;

    @FXML
    private TextField idEmail;

    @FXML
    private TextField idNumber;

    @FXML
    private ComboBox<String> idRole;


    @FXML
    private TextField idLastName;



    @FXML
    private Button idButton;

    @FXML
    private TextField idAddress;
    @FXML
    private Button idAfficherAdd;


    private final String[] Role = {"Admin", "Client", "Nutritionist", "Restau Owner", "Delivery Man"};
    //private final String[] Sector = {"Manouba", "Ariana", "Marsa", "Megrine", "Zahra", "Charguia"};


    @Override
    public void initialize(URL url, ResourceBundle rb) {


        idRole.setItems(FXCollections.observableArrayList(Role));



    }

    public void testSaisis(){
        if(this.idUFirstName.getText().isEmpty() || this.idLastName.getText().isEmpty() || this.idEmail.getText().isEmpty() || this.idAddress.getText().isEmpty() || this.idNumber.getText().isEmpty() || this.idPassword.getText().isEmpty()){

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs manquants");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs !");
            alert.showAndWait();
        }
        else if (!this.idEmail.getText().matches(emailRegex)){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Format email incorrect");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir un email valide !");
            alert.showAndWait();
            nameCS.setVisible(true);
        }else if (uService.checkEmailExists(idEmail.getText())) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Email existe déjà");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir un email différent !");
            alert.showAndWait();
        }else if(!idNumber.getText().matches(".*\\d.*")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Format du numero invalide");
            alert.setHeaderText(null);
            alert.setContentText("Format de numero invalide !");
            alert.showAndWait();

        }else if (Integer.parseInt(idNumber.getText()) < 10000000 || Integer.parseInt(idNumber.getText()) > 99999999) {


        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("numero hors de la plage valide");
        alert.setHeaderText(null);
        alert.setContentText("Le numero doit contint 9 chiffres");
        alert.showAndWait();
        }
        else if (idPassword.getText().length() < 8) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attention");
            alert.setHeaderText(null);
            alert.setContentText("Le mot de passe doit avoir au moins 8 caractères.");
            alert.showAndWait();
        }   else if (!idPassword.getText().matches(".*[A-Z].*") || !idPassword.getText().matches(".*\\d.*")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attention");
            alert.setHeaderText(null);
            alert.setContentText("Le mot de passe doit contenir au moins une lettre majuscule et un chiffre.");
            alert.showAndWait();
        }
        else{
            ajouterUSer();
        }
    }



    @FXML
    void ajouterUSer() {

            try {
                uService.ajouter(new User(idUFirstName.getText(), idLastName.getText(), idEmail.getText(), idAddress.getText(), 0, idRole.getValue(), Integer.parseInt(idNumber.getText()), idPassword.getText()));
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ListUser.fxml"));
            idRole.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }







    @FXML
    void naviguezVersAffichage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ListUser.fxml"));
            idRole.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }






}

