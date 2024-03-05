package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Donation;
import services.DonationService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class HistoryController {

    @FXML
    private Button deleteButton;

    @FXML
    private ListView<Donation> listView;

    @FXML
    private TextField idSearch;


    private final DonationService donationService = new DonationService();

    Donation currentDonation;

    public void initialize() {
        ObservableList<Donation> donationObservableList = FXCollections.observableArrayList();
        listView.setItems(donationObservableList);
        try {
            List<Donation> donationFromService = donationService.recuperer();
            donationObservableList.addAll(donationFromService);
            listView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number oldIndex, Number newIndex) {
                    currentDonation = listView.getSelectionModel().getSelectedItem();
                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void switchToDisplayAllDonations() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Donate.fxml"));
            Parent newPageRoot = loader.load();


            Scene pageScene = new Scene(newPageRoot);
            Stage stage = (Stage) listView.getScene().getWindow();
            stage.setScene(pageScene);

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }
    }
    public void switchToDisplayAllCampaigns() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Campaign.fxml"));
            Parent newPageRoot = loader.load();


            Scene pageScene = new Scene(newPageRoot);
            Stage stage = (Stage) listView.getScene().getWindow();
            stage.setScene(pageScene);

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }
    }


    @FXML
    void searchauto(ActionEvent event) {


    }


    public void deleteDonation(ActionEvent actionEvent) {
        try {
            donationService.supprimer(currentDonation.getIdDon());
            initialize();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
