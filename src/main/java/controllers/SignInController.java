package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import services.userService;

import java.io.IOException;
import java.util.logging.Logger;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
public class SignInController {
    public static int id_modif ;

    userService uService = new userService();
    @FXML
    private Button idAppleLogIn;

    @FXML
    private Button idButtonLogIn;

    @FXML
    private TextField LogInPassword;

    @FXML
    private CheckBox idRemeberMeLogIn;

    @FXML
    private Button idFbLogIn;

    @FXML
    private TextField LogInEmail;

    @FXML
    private Button idGoogleLogIn;

    @FXML
    private void oublier(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ForgotPassword.fxml"));
            LogInEmail.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
//    @FXML
//    private void inscri(ActionEvent event) {
//
//        try {
//            Parent root = FXMLLoader.load(getClass().getResource("inscriptionUser.fxml"));
//            Scene scene = new Scene(root);
//            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            stage.setScene(scene);
//            stage.show();} catch (IOException ex) {
//            Logger.getLogger(InscriptionUserController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }


    @FXML
    private void cnx(ActionEvent event) {
        //String page = "";
        String email = LogInEmail.getText();
        String password = LogInPassword.getText();
        String Role =null;
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        if (email.isEmpty() || password.isEmpty()) {
            // Afficher un message d'alerte
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs manquants");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs !");
            alert.showAndWait();
        } else if (!email.matches(emailRegex)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Format email incorrect");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir un email valide !");
            alert.showAndWait();
        } else {
            Role = uService.authentification(email, password);

            if (Role == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Infos incorrectes");
                alert.setHeaderText(null);
                alert.setContentText("Email ou Mot de passe incorrecte !");
                alert.showAndWait();
            } else if(Role.equals("Admin")){
                naviguezVersAffichageSignIn();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Infos incorrectes");
                alert.setHeaderText(null);
                alert.setContentText("vous n'étes pas un admin !");
                alert.showAndWait();
            }
                // Check if the password is correct using BCrypt.checkpw
                // Password is correct
               /* sessionManager.setCurrentUser(uService.readById(id));
                id_modif = id;
                String role = uService.readById(id).getRole();
                switch (role) {
                    case "[\"Admin\"]":
                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        alert.setContentText("Admin connecté");
                        alert.show();
                        page = "AllAdmin.fxml";
                        break;

                    case "[\"Delivery M\"]":
                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        alert.setContentText("Client connecté");
                        alert.show();
                        page = "HomeUser.fxml";
                        break;


                }

                try {
                    Parent page1 = FXMLLoader.load(getClass().getResource(page));
                    Scene scene = new Scene(page1);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }*/
        }
        //naviguezVersAffichageSignIn();

    }

    @FXML
    void naviguezVersAffichageSignIn() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ListUser.fxml"));
            LogInEmail.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
