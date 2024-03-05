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
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.Campaign;
import services.CampaignService;
import services.ExcelExporterCampaign;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CampaignController {
        @FXML
        private TextField idSearch;

    @FXML
    private Button deleteButton;

    @FXML
    private Button modifyButton;

    @FXML
    private ListView<Campaign> listView;
    private final CampaignService campaignService=new CampaignService();
    Campaign currentCampaign;
    @FXML
    private void export(ActionEvent event) {
        ExcelExporterCampaign d = new ExcelExporterCampaign();
        d.generateExcel(listView);
    }
    @FXML
    private void showStatistics(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DashboardCampaign.fxml"));
            Parent root = loader.load();
            DashboardControllerCampaign dashboardController = loader.getController();
            //  dashboardController.loadStatistics();
            // dashboardController.loadStatisticsRating();

            idSearch.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void SwitchTouser(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ListUser.fxml"));
            modifyButton.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void SwitchToRecipe(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherRecette4.fxml"));
            modifyButton.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void SwitchToResto(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/showrestaurant.fxml"));
            modifyButton.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }



    public void initialize()  {
        ObservableList<Campaign> campaigns = FXCollections.observableArrayList();
        listView.setItems(campaigns);
        try {
            List<Campaign> campaignFromService = campaignService.recuperer();
            campaigns.addAll(campaignFromService);
            listView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number oldIndex, Number newIndex) {
                    currentCampaign = listView.getSelectionModel().getSelectedItem();
//
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
    public void switchToAddCampaigns() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CampaignAdd.fxml"));
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
    public void deleteCampaign(ActionEvent actionEvent) {
        try {
            campaignService.supprimer(currentCampaign.getId());
            initialize();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void modifyCampaign() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CampaignModify.fxml"));
            Parent newPageRoot = loader.load();

            ModifyCampaignController modifyCampaignController= loader.getController();
            modifyCampaignController.initializeValues(
                    currentCampaign.getNumber(),currentCampaign.getGoal(),
                    currentCampaign.getTitle(), currentCampaign.getAssociationName(),currentCampaign.getDescription(),currentCampaign.getId());

            Scene newPageScene = new Scene(newPageRoot);
            Stage currentStage = (Stage) listView.getScene().getWindow();
            currentStage.setScene(newPageScene);
            currentStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void searchauto() {
        String title =idSearch.getText();

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

}
