package controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import services.*;
import models.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import models.User;


public class ModifyUserController implements Initializable {
    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.(com|tn)$";

    userService uService = new userService();
    @FXML
    private TextField idUFirstName;

    @FXML
    private TextField idEmail;

    @FXML
    private TextField idNumber;

    @FXML
    private ComboBox<String> idRole;


    @FXML
    private Button idButton1;

    @FXML
    private TextField idPassword;

    @FXML
    private TextField idLastName;


    @FXML
    private Button idButton;

    String email;



    @FXML
    private TextField idAddress;
    private final String[] Role = {"Admin", "Client", "Nutritionist", "Restau Owner", "Delivery Man"};
    @Override
    public void initialize(URL url, ResourceBundle rb) {


        idRole.setItems(FXCollections.observableArrayList(Role));

    }

    public void populateFields(User user) {
        // Populate form fields with user's information
        idUFirstName.setText(user.getFirstName());
        idLastName.setText(user.getLastName());
        idEmail.setText(user.getEmail());
        email=user.getEmail();
        idAddress.setText(user.getAddress());
        idPassword.setText(user.getPassword());
        idRole.setValue(user.getRole());
        idNumber.setText(String.valueOf(user.getNumber()));



    }



    @FXML
        public void testSaisis(){
            if(this.idUFirstName.getText().isEmpty() || this.idLastName.getText().isEmpty() || this.idEmail.getText().isEmpty() || this.idAddress.getText().isEmpty() || this.idNumber.getText().isEmpty() || this.idPassword.getText().isEmpty()){

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ther is empty field");
                alert.setHeaderText(null);
                alert.setContentText("fill all the fields");
                alert.showAndWait();
            }
            else if (!this.idEmail.getText().matches(emailRegex)){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Format email incorrect");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez saisir un email valide !");
                alert.showAndWait();
//            }else if (uService.checkEmailExists(idEmail.getText())) {
//
//                Alert alert = new Alert(Alert.AlertType.WARNING);
//                alert.setTitle("Email existe déjà");
//                alert.setHeaderText(null);
//                alert.setContentText("Veuillez saisir un email différent !");
//                alert.showAndWait();
            }else if(!idNumber.getText().matches(".*\\d.*")){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("invalid phone  number");
                alert.setHeaderText(null);
                alert.setContentText("phone number mus");
                alert.showAndWait();

            }else if (idNumber.getText().length()!=8) {


                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("numero hors de la plage valide");
                alert.setHeaderText(null);
                alert.setContentText("Le numero doit contint 8 chiffres");
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
                saveUser();
            }
        }

    @FXML
    void saveUser() {
//        if (idRole.getValue().equals("Delivery Man")) {
//            try {
//                uDService.ajouter(new DeliveryMan(idUFirstName.getText(), idLastName.getText(), idEmail.getText(), idAddress.getText(), 0, idRole.getValue(), Integer.parseInt(idNumber.getText()), idPassword.getText(), idVeicle.getText(), idAvailability.getText(), idSector.getValue()));
//            } catch (SQLException e) {
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Error");
//                alert.setContentText(e.getMessage());
//                alert.showAndWait();
//            }
//        } else {
            try {
                uService.modifierByEmail(new User(idUFirstName.getText(), idLastName.getText(), idEmail.getText(), idAddress.getText(), 0, idRole.getValue(), Integer.parseInt(idNumber.getText()), idPassword.getText()),email);
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        //}

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
