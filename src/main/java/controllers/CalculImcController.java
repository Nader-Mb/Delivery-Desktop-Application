package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CalculImcController implements Initializable {

    @FXML
    private TextField txtPoidsIMC;

    @FXML
    private AnchorPane calculerIMCPane;

    @FXML
    private Button btnAddIMC;

    @FXML
    private TextField txtTailleIMC;

    @FXML
    private Button btnClearIMC;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void clearFieldsIMC() {
        txtPoidsIMC.clear();
        txtTailleIMC.clear();
    }

    @FXML
    void CalculerIMC(ActionEvent event) {
        if(event.getSource() == btnAddIMC) {
            if (txtTailleIMC.getText().isEmpty() || txtPoidsIMC.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Information manquante");
                alert.setHeaderText(null);
                alert.setContentText("Vous devez remplir tous les détails pour calculer votre IMC.");
                Optional<ButtonType> option = alert.showAndWait();
            } else {
                float poids = Float.parseFloat(txtPoidsIMC.getText());
                float taille = Float.parseFloat(txtTailleIMC.getText());
                float imc = poids/(taille*taille);
                if(imc<18.5){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("IMC Résultat !");
                    alert.setHeaderText(null);
                    alert.setContentText("Vous êtes Maigre. Votre IMC = "+imc);
                    Optional<ButtonType> option = alert.showAndWait();
                } else if((18.5<imc)&&(imc<25)){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("IMC Résultat !");
                    alert.setHeaderText(null);
                    alert.setContentText("Vous êtes Normal. Votre IMC = "+imc);
                    Optional<ButtonType> option = alert.showAndWait();
                } else if((25<imc)&&(imc<30)){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("IMC Résultat !");
                    alert.setHeaderText(null);
                    alert.setContentText("Vous êtes Surpoids. Votre IMC = "+imc);
                    Optional<ButtonType> option = alert.showAndWait();
                } else if((30<imc)){


                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("IMC Résultat !");
                    alert.setHeaderText(null);
                    alert.setContentText("Vous êtes Obese. Votre IMC = "+imc);
                    Optional<ButtonType> option = alert.showAndWait();
                }


            }
        }

        if(event.getSource() == btnClearIMC){
            clearFieldsIMC();
        }
    }
}
