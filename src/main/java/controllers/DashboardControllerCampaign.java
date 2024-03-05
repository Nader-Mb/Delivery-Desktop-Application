package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import models.Campaign;
import services.CampaignService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class DashboardControllerCampaign implements Initializable {

    private CampaignService campaignService = new CampaignService();

    @FXML
    private PieChart pieChart;
    @FXML
    private PieChart pieChart2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadStatistics();
        loadStatisticsRating();
    }
    public void switchToDisplayAllCampaigns() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Campaign.fxml"));
            Parent newPageRoot = loader.load();


            Scene pageScene = new Scene(newPageRoot);
            Stage stage = (Stage) pieChart.getScene().getWindow();
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
            Stage stage = (Stage) pieChart.getScene().getWindow();
            stage.setScene(pageScene);

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }
    }

    public void loadStatisticsRating() {
        try {
            List<Campaign> campaigns = campaignService.recuperer();

            // Initialize counts for different rating ranges
            int excellentCount = 0;
            int goodCount = 0;
            int averageCount = 0;
            int poorCount = 0;

            // Count occurrences of each rating range
            for (Campaign campaign : campaigns) {
                float current = campaign.getCurrent();
                int goal = campaign.getGoal();
                double progress = (double) current / goal;
                if (progress >= 0.9) {
                    excellentCount++;
                } else if (progress >= 0.75) {
                    goodCount++;
                } else if (progress >= 0.5) {
                    averageCount++;
                } else {
                    poorCount++;
                }
            }

            // Create pie chart data
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Excellent (90%+)", excellentCount),
                    new PieChart.Data("Good (75%-89%)", goodCount),
                    new PieChart.Data("Average (50%-74%)", averageCount),
                    new PieChart.Data("Poor (<50%)", poorCount)
            );

            // Set pie chart data
            pieChart2.setData(pieChartData);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadStatistics() {
        try {
            List<Campaign> campaigns = campaignService.recuperer();

            // Initialize a map to store campaign type counts
            Map<String, Integer> typeCounts = new HashMap<>();

            // Count occurrences of each campaign type
            for (Campaign campaign : campaigns) {
                String type = campaign.getCampaignType().toLowerCase();
                typeCounts.put(type, typeCounts.getOrDefault(type, 0) + 1);
            }

            // Create pie chart data
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

            // Add data for each campaign type to the pie chart
            for (Map.Entry<String, Integer> entry : typeCounts.entrySet()) {
                pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
            }

            // Set pie chart data
            pieChart.setData(pieChartData);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
