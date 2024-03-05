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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import models.Campaign;
import services.CampaignService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class DonateController {

    @FXML
    private ListView<Campaign> listView;

    @FXML
    private TextField idSearch;
    private final CampaignService campaignService = new CampaignService();
    Campaign currentCampaign;


    public void initialize() {
        ObservableList<Campaign> campaigns = FXCollections.observableArrayList();
        listView.setItems(campaigns);
        try {
            List<Campaign> campaignFromService = campaignService.recuperer();
            campaigns.addAll(campaignFromService);
            listView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number oldIndex, Number newIndex) {
                    currentCampaign = listView.getSelectionModel().getSelectedItem();
                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
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

    public void searchauto(KeyEvent keyEvent) {
        String title = idSearch.getText();

        try {
            List<Campaign> searchResults = campaignService.searchCampaignByTitle(title);

            ObservableList<Campaign> observableList = FXCollections.observableList(searchResults);

            listView.setItems(observableList);


        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    public void switchToHistory() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/History.fxml"));
            Parent newPageRoot = loader.load();

            Scene pageScene = new Scene(newPageRoot);
            Stage stage = (Stage) listView.getScene().getWindow();
            stage.setScene(pageScene);

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    void donate(ActionEvent event) {
        if(currentCampaign.getCurrent()<currentCampaign.getGoal())
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DonationAdd.fxml"));
            Parent newPageRoot = loader.load();

            AddDonationController addDonationController = loader.getController();
            addDonationController.initializeValues(0, currentCampaign.getId());
            Scene newPageScene = new Scene(newPageRoot);
            Stage currentStage = (Stage) listView.getScene().getWindow();
            currentStage.setScene(newPageScene);
            currentStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

