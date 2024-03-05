package controllers;

import models.Cabinet;
import models.Rdv;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import services.CabinetService;
import services.RdvService;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


public class addRdvController implements Initializable {
    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.(com|tn)$";


    @FXML
    private AnchorPane addRdvPane;

    @FXML
    private Button btnAddRdv;

    @FXML
    private Button btnClearRdv;

    @FXML
    private ComboBox<String> txtCabinetRdv;

    @FXML
    private DatePicker txtDateRdv;

    @FXML
    private TextField txtEmailRdv;

    @FXML
    private TextField txtNomRdv;

    @FXML
    private TextField txtNumTelRdv;

    @FXML
    private TextField txtPrenomRdv;




    CabinetService cs = new CabinetService();
    List<Cabinet> cabinets = cs.Show();
    private int cabId=-1;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Map<String, Integer> valuesMap = new HashMap<>();
        for(Cabinet cab : cabinets){
            txtCabinetRdv.getItems().add(cab.getNom());
            valuesMap.put(cab.getNom(),cab.getId());
        }

        txtCabinetRdv.setOnAction(event ->{
            String SelectedOption = null;
            SelectedOption = txtCabinetRdv.getValue();
            int SelectedValue = 0;
            SelectedValue = valuesMap.get(SelectedOption);
            cabId = SelectedValue;
        });
    }


    @FXML
    private void AjoutRdv(ActionEvent event) {
        //check if not empty
        if(event.getSource() == btnAddRdv) {
            if (cabId == -1 || txtPrenomRdv.getText().isEmpty() || txtNumTelRdv.getText().isEmpty()
                    || txtNomRdv.getText().isEmpty() || txtEmailRdv.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Information manquante");
                alert.setHeaderText(null);
                alert.setContentText("Vous devez remplir tous les détails concernant votre rendez-vous.");
                Optional<ButtonType> option = alert.showAndWait();
            } else if (!this.txtEmailRdv.getText().matches(emailRegex)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Format email incorrect");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez saisir un email valide !");
                alert.showAndWait();

            } else if (!txtNumTelRdv.getText().matches(".*\\d.*")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Format du numero invalide");
                alert.setHeaderText(null);
                alert.setContentText("Format de numero invalide !");
                alert.showAndWait();

            } else if (txtNumTelRdv.getText().length()!=8 ) {


                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("numero hors de la plage valide");
                alert.setHeaderText(null);
                alert.setContentText("Le numero doit contint 8 chiffres");
                alert.showAndWait();
            } else {
                ajouterRdv();
                send_SMS();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Ajouté avec succès");
                alert.setHeaderText(null);
                alert.setContentText("Votre rendez-vous a été ajoutée avec succès.");
                Optional<ButtonType> option = alert.showAndWait();
                try {
                    Parent fxml= FXMLLoader.load(getClass().getResource("/listRdv.fxml"));
                    addRdvPane.getChildren().removeAll();
                    addRdvPane.getChildren().setAll(fxml);
                }
                catch (IOException e) {
            System.err.println(e.getMessage());
            }

                //clearFieldsRdv();
            }
        }

        if(event.getSource() == btnClearRdv){
            clearFieldsRdv();
        }
    }

    @FXML
    private void clearFieldsRdv() {
        txtPrenomRdv.clear();
        txtNumTelRdv.clear();
        txtNomRdv.clear();
        txtEmailRdv.clear();
        txtCabinetRdv.getEditor().clear();
        txtDateRdv.getEditor().clear();
    }

    private void ajouterRdv() {

        // From Formulaire
        int id_cabinet = cabId;
        Date dateRdv = null;
        try {
            LocalDate localDate = txtDateRdv.getValue();
            if (localDate != null) {
                Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                dateRdv = Date.from(instant);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        String nomRdv = txtNomRdv.getText();
        String prenomRdv = txtPrenomRdv.getText();
        int numTelRdv = Integer.parseInt(txtNumTelRdv.getText());
        String emailRdv = txtEmailRdv.getText();


        Rdv r = new Rdv(
                nomRdv, prenomRdv, numTelRdv, emailRdv, dateRdv, id_cabinet);
        RdvService rs = new RdvService();
        rs.ajouter(r);
    }


    void send_SMS (){
        // Initialisation de la bibliothèque Twilio avec les informations de votre compte
        String ACCOUNT_SID = "AC817c366095c4b5ae77a3715e0621ce98";
        String AUTH_TOKEN = "4618a20ee0cf49acaa229d23ab76e097";

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        String recipientNumber = "+21651192850";
        String message = "Bonjour Mr ,\n"
                + "Nous sommes ravis de vous informer qu'un rendez-vous a été ajouté.\n "
                + "Veuillez contactez l'administration pour plus de details.\n "
                + "Merci de votre fidélité et à bientôt chez Green Menu.\n"
                + "Cordialement,\n"
                + "Green Menu";

        Message twilioMessage = Message.creator(
                new PhoneNumber(recipientNumber),
                new PhoneNumber("+19255267563"),message).create();

        System.out.println("SMS envoyé : " + twilioMessage.getSid());
    }

    @FXML
    void openIMCCalcul(ActionEvent event) throws IOException{
        Parent fxml= FXMLLoader.load(getClass().getResource("/CalculIMC.fxml"));
        addRdvPane.getChildren().removeAll();
        addRdvPane.getChildren().setAll(fxml);
    }

//    @FXML
//    void naviguezVersAffichage() {
//        try {
//            Parent root = FXMLLoader.load(getClass().getResource("/listCabinet.fxml"));
//            txtEmailRdv.getScene().setRoot(root);
//        } catch (IOException e) {
//            System.err.println(e.getMessage());
//        }
//    }

}
