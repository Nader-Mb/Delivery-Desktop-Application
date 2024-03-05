package controllers;

import models.Cabinet;
import models.Rdv;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import services.CabinetService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import services.RdvService;


public class itemRdvController implements Initializable {


    @FXML
    private Button btnModifierRdv;

    @FXML
    private Button btnSupprimerRdv;

    @FXML
    private AnchorPane itemRdvPane;

    @FXML
    private Label labelCabinetRdv;

    @FXML
    private Label labelDateRdv;

    @FXML
    private Label labelEmailRdv;

    @FXML
    private Label labelNomRdv;

    @FXML
    private Label labelNumTelRdv;

    @FXML
    private Label labelPrenomRdv;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    CabinetService cs=new CabinetService();
    Rdv rd;
    public void setData (Rdv rd) throws SQLException {
        this.rd = rd ;

        labelNomRdv.setText(rd.getNom());
        labelPrenomRdv.setText(rd.getPrenom());
        labelNumTelRdv.setText(String.valueOf(rd.getNumTel()));
        labelEmailRdv.setText(rd.getEmail());
        labelDateRdv.setText(String.valueOf(rd.getDateRdv()));
        labelCabinetRdv.setText(cs.getById(rd.getId_cabinet()).getNom());

    }

    public Rdv getData (Rdv r){
        this.rd = r ;
        return this.rd;

    }

    @FXML
    private void supprimerRdv(ActionEvent event) throws SQLException {
        RdvService rs = new RdvService();

        // Afficher une boîte de dialogue de confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous vraiment supprimer ce Rendez-vous ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Récupérer l'ID du Rdv sélectionnée
            int id = this.rd.getId();

            // Supprimer le rdv de la base de données
            rs.supprimer(id);

        }
    }

    @FXML
    void open_UpdateRdv()throws IOException {
        Parent fxml= FXMLLoader.load(getClass().getResource("/updateRdv.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Update Rendez-vous");
        stage.setScene(new Scene(fxml));
        stage.showAndWait();
    }
}
