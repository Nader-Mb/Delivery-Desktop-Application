package controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import models.SMSSender;
import models.User;
import services.userService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.(com|tn)$";

    userService uService = new userService();

    @FXML
    private TextField lastName;

    @FXML
    private Button idButtonLogIn;

    @FXML
    private ComboBox<String> idRole;

    @FXML
    private TextField SignUpFirstName;

    @FXML
    private TextField SignUpPassword;

    @FXML
    private TextField SignUpEmail;

    @FXML
    private TextField idNumber;

    @FXML
    private TextField idAddress;

    private final String[] Role = {"Admin", "Client", "Nutritionist", "Restau Owner", "Delivery Man"};


    @Override
    public void initialize(URL url, ResourceBundle rb) {


        idRole.setItems(FXCollections.observableArrayList(Role));



    }

    public void signUpWithEmail(String firstName, String lastName, String email, String address, int number, String role, String password) throws SQLException {
        // Implement sign-up logic with email
        // Insert user details into the database
        //ajouter(new User(firstName, lastName, email, address, number, role, password));
    }

    public void signUpWithFacebook(String facebookAccessToken) {
        // Implement sign-up logic with Facebook API
        // Authenticate with Facebook using the access token
        // Retrieve user details from Facebook and insert into the database
    }

    public void signUpWithApple(String appleIdToken) {
        // Implement sign-up logic with Apple API
        // Authenticate with Apple using the id token
        // Retrieve user details from Apple and insert into the database
    }


    @FXML
    void signUpWithEmail() {
        // Call the sign-up method with email from userService
       // userService.signUpWithEmail(SignUpFirstName.getText(), lastName.getText(), SignUpEmail.getText(), idAddress.getText(), Integer.parseInt(idNumber.getText()), idRole.getValue(), SignUpPassword.getText());
    }

    @FXML
    void signUpWithFacebook() {
        // Call the sign-up method with Facebook from userService
       // userService.signUpWithFacebook(facebookAccessToken);
    }

    @FXML
    void signUpWithApple() {
        // Call the sign-up method with Apple from userService
       // userService.signUpWithApple(appleIdToken);
    }
    public void test(){
    if(this.idRole.getValue().isEmpty() ||this.SignUpFirstName.getText().isEmpty() || this.lastName.getText().isEmpty() || this.SignUpEmail.getText().isEmpty() || this.idAddress.getText().isEmpty() || this.idNumber.getText().isEmpty() || this.SignUpPassword.getText().isEmpty()){

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Champs manquants");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez remplir tous les champs !");
        alert.showAndWait();
    }
        else if (!this.SignUpEmail.getText().matches(emailRegex)){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Format email incorrect");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez saisir un email valide !");
        alert.showAndWait();
    }else if (uService.checkEmailExists(SignUpEmail.getText())) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Email existe déjà");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez saisir un email différent !");

    }
        else if (SignUpPassword.getText().length() < 8) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attention");
        alert.setHeaderText(null);
        alert.setContentText("Le mot de passe doit avoir au moins 8 caractères.");
        alert.showAndWait();
    }   else if (!SignUpPassword.getText().matches(".*[A-Z].*") || !SignUpPassword.getText().matches(".*\\d.*")) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attention");
        alert.setHeaderText(null);
        alert.setContentText("Le mot de passe doit contenir au moins une lettre majuscule et un chiffre.");
        alert.showAndWait();
    } else{
        ajouterUSer();
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("succeded");
        alert.setHeaderText(null);
        alert.setContentText("signed up successfully");
        alert.showAndWait();
    }
}




    @FXML
    void ajouterUSer() {

        SMSSender smsSender=new SMSSender();
        smsSender.send_SMS(idNumber.getText(),SignUpFirstName.getText(),lastName.getText());

        try {
            uService.ajouter(new User(SignUpFirstName.getText(), lastName.getText(), SignUpEmail.getText(), idAddress.getText(), 0, idRole.getValue(), Integer.parseInt(idNumber.getText()), SignUpPassword.getText()));
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/SignIn.fxml"));
            idRole.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }




}
