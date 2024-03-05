package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import services.SendMail;
import models.Recette;

import java.io.IOException;

public class MailController {
    public MailController(){}

    @FXML
    private Button btCancel;
    @FXML
    private Button btsend;
    @FXML
    private TextField tfSendTo;

    @FXML
    private PasswordField tfPSW;
    @FXML
    private TextArea tfRecMail;

    @FXML
    private TextField tfEmail;

    @FXML
    void cancel(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherRecette4.fxml"));
            btCancel.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    public void generateMail(Recette rec){
        int id=rec.getIdRec();
        String NameRec=rec.getNomRec();
        String rating= Integer.toString(rec.getRating());
        //date=rec.getDate();
        //idU=rec.getIdUser();
        String serves=Integer.toString(rec.getServes());
        String Desc= rec.getDescription();
        //String URLimg=rec.getImageRec();
        String Category=rec.getCategoryR();
        String Difficulty=rec.getDifficulty();
        String prep=rec.getPrep();
        tfRecMail.setText("Name: " + NameRec + "\n" +
                "Category: " + Category + "\n" +
                "Difficulty: " + Difficulty + "\n" +
                "Serves: " + serves + "\n" +
                "Preparation Time: " + prep + "\n" +
                "Description: " + Desc + "\n" +
                //"Date: " + date + "\n" +
                "Rating: " + rating + "\n");
    }

    @FXML
    void SendMailAction(ActionEvent event) {
    String UN =tfEmail.getText();
    String PW = tfPSW.getText();
    String mto = tfSendTo.getText();
    String msub = "Sharing Recipe";
    String cTEXT =tfRecMail.getText();
           SendMail.Mail(UN, PW, mto, msub, cTEXT);

    }
}
