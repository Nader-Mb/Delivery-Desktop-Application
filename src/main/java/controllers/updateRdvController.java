package controllers;

import models.Cabinet;
import models.Rdv;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.converter.LocalDateStringConverter;
import services.CabinetService;
import services.RdvService;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class updateRdvController implements Initializable {

    @FXML
    private Button btnClearRdv;

    @FXML
    private Button btnUpdateRdv;

    @FXML
    private ComboBox<String> txtUpdateCabinetRdv;

    @FXML
    private DatePicker txtUpdateDateRdv;

    @FXML
    private TextField txtUpdateEmailRdv;

    @FXML
    private TextField txtUpdateNomRdv;

    @FXML
    private TextField txtUpdateNumTelRdv;

    @FXML
    private TextField txtUpdatePrenomRdv;

    @FXML
    private AnchorPane updateRdvPane;


    private int id;

    CabinetService cs = new CabinetService();
    List<Cabinet> cabinets = cs.Show();
    private int cabId=-1;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Map<String, Integer> valuesMap = new HashMap<>();
        for(Cabinet cab : cabinets){
            txtUpdateCabinetRdv.getItems().add(cab.getNom());
            valuesMap.put(cab.getNom(),cab.getId());
        }

        txtUpdateCabinetRdv.setOnAction(event ->{
            String SelectedOption = null;
            SelectedOption = txtUpdateCabinetRdv.getValue();
            int SelectedValue = 0;
            SelectedValue = valuesMap.get(SelectedOption);
            cabId = SelectedValue;
        });


        RdvService rs = new RdvService();
        List<Rdv> rdvs = rs.Show();

        for(int i=0;i<rdvs.size();i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/itemRdv.fxml"));

            try {
                AnchorPane anchorPane = fxmlLoader.load();
                HBox hBox = (HBox) anchorPane.getChildren().get(0);
                itemRdvController itemController = fxmlLoader.getController();
                txtUpdateNomRdv.setText(itemController.getData(rdvs.get(i)).getNom());
                txtUpdatePrenomRdv.setText(itemController.getData(rdvs.get(i)).getPrenom());
                txtUpdateNumTelRdv.setText(String.valueOf(itemController.getData(rdvs.get(i)).getNumTel()));
                txtUpdateEmailRdv.setText(itemController.getData(rdvs.get(i)).getEmail());
                //txtUpdateDateRdv.setValue(itemController.getData(rdvs.get(i)).getDateRdv());
                txtUpdateCabinetRdv.setValue(String.valueOf(itemController.getData(rdvs.get(i)).getId_cabinet()));
                this.id=itemController.getData(rdvs.get(i)).getId();
            } catch (IOException ex) {
                Logger.getLogger(itemCabinetController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    void ModifierRdv(ActionEvent event) {
        if (txtUpdateNomRdv.getText().isEmpty() || txtUpdatePrenomRdv.getText().isEmpty() || txtUpdateNumTelRdv.getText().isEmpty()
                || txtUpdateEmailRdv.getText().isEmpty() || cabId==-1)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Information manquante");
            alert.setHeaderText(null);
            alert.setContentText("Vous devez remplir tous les détails concernant votre Rendez-vous.");
            Optional<ButtonType> option = alert.showAndWait();

        } else {
            modifRdv();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Modifié avec succès");
            alert.setHeaderText(null);
            alert.setContentText("Votre Rendez-vous a été modifié avec succès.");
            Optional<ButtonType> option = alert.showAndWait();

        }
    }

    @FXML
    void clearFieldsRdv(ActionEvent event) {
        txtUpdateDateRdv.getEditor().clear();
        txtUpdateCabinetRdv.getEditor().clear();
        txtUpdateEmailRdv.clear();
        txtUpdateNomRdv.clear();
        txtUpdatePrenomRdv.clear();
        txtUpdateNumTelRdv.clear();
    }


    void modifRdv(){
        String nom = txtUpdateNomRdv.getText();
        String prenom = txtUpdatePrenomRdv.getText();
        int numTel = Integer.parseInt(txtUpdateNumTelRdv.getText());
        String email = txtUpdateEmailRdv.getText();
        Date dateRdv = null;
        try {
            LocalDate localDate = txtUpdateDateRdv.getValue();
            if (localDate != null) {
                Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                dateRdv = Date.from(instant);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        int id_cab = cabId;


        Rdv r = new Rdv(
                id,
                nom, prenom, numTel, email, dateRdv, id_cab);
        RdvService rs = new RdvService();
        rs.modifier(r);
    }
}
