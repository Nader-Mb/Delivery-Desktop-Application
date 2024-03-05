package controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import models.Reservation;
import services.ReservationService;
import services.RestaurantService;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsController {
    @FXML
    private WebView mapWebView;

    @FXML
    private Pane mapPane;

    @FXML
    private Button usersbut;

    @FXML
    private Button logoutbut;

    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private Button dashboardbut;

    @FXML
    private Button restaubut;

    @FXML
    private Button campaignbut;

    @FXML
    private Button recipesbut;
    @FXML
    private ListView<String> addressListView;


    private final ReservationService reservationService;
    private final RestaurantService restaurantService;

    public StatisticsController() throws SQLException {
        this.reservationService = new ReservationService();
        this.restaurantService = new RestaurantService();
    }


    @FXML
    public void initialize() {
        loadOpenStreetMap();

        populateAddressListView();
        // Populate Bar Chart
        populateBarChart();
    }
    private void loadOpenStreetMap() {
        WebEngine webEngine = mapWebView.getEngine();
        webEngine.load("https://www.openstreetmap.org/export/embed.html?bbox=-180,-90,180,90&layer=mapnik");
    }

    private void populateAddressListView() {
        try {
            List<String> addresses = restaurantService.getAllAddresses();
            addressListView.getItems().addAll(addresses);

            addressListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    showAddressOnMap(newValue);
                }
            });
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }

    private void showAddressOnMap(String address) {
        // You may need to format the address if it's not in a suitable format for OpenStreetMap
        // For example, replace spaces with '+', encode special characters, etc.
        String formattedAddress = formatAddressForMap(address);

        // Load the formatted address in the mapWebView
        WebEngine webEngine = mapWebView.getEngine();
        webEngine.load("https://www.openstreetmap.org/search?query=" + formattedAddress);
    }

    private String formatAddressForMap(String address) {
        // Implement address formatting as per your requirements
        // For example, replace spaces with '+', encode special characters, etc.
        // This can vary based on the expected format by OpenStreetMap
        return address.replace(" ", "+");
    }
    @FXML
    private void populateBarChart() {
        try {
            List<Reservation> reservations = reservationService.retrieveAll();

            // Map to store the number of persons for each date
            Map<LocalDate, Integer> personsPerDate = new HashMap<>();

            // Calculate the number of persons for each date
            for (Reservation reservation : reservations) {
                LocalDateTime dateTime = reservation.getDateTime();
                LocalDate date = dateTime.toLocalDate();

                int numberOfPersons = reservation.getNumberOfPersons();

                personsPerDate.put(date, personsPerDate.getOrDefault(date, 0) + numberOfPersons);
            }

            // Create a series for the bar chart
            XYChart.Series<String, Integer> series = new XYChart.Series<>();

            // Add data to the series
            for (Map.Entry<LocalDate, Integer> entry : personsPerDate.entrySet()) {
                series.getData().add(new XYChart.Data<>(entry.getKey().toString(), entry.getValue()));
            }

            // Set background color of the bar chart
            barChart.setStyle("-fx-background-color: white;");

            // Add the series to the bar chart
            barChart.getData().add(series);

            for (XYChart.Data<String, Integer> data : series.getData()) {
                Node node = data.getNode();
                if (node != null) {
                    node.setStyle("-fx-bar-fill: #00796b;");
                }
            }

            // Create a custom data formatter for integer values
            DecimalFormat df = new DecimalFormat("#");

            // Create a custom data label formatter
            series.getData().forEach(data -> {
                data.nodeProperty().addListener((ov, oldNode, node) -> {
                    if (node != null) {
                        Label dataLabel = new Label(df.format(data.getYValue()));
                        dataLabel.setStyle("-fx-text-fill: white;"); // Set label text color
                        dataLabel.setTranslateY(-5); // Adjust label position
                        ((Group) node.getParent()).getChildren().add(dataLabel);
                    }
                });
            });

            // Remove the legend (optional)
            barChart.setLegendVisible(false);

        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }
    @FXML
    void gotorestau(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/homerestaurant.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    @FXML
    void gotodashboard(ActionEvent event) {
    }

    @FXML
    void gotousers(ActionEvent event) {
    }

    @FXML
    void gotorecipes(ActionEvent event) {
    }



    @FXML
    void gotocampaign(ActionEvent event) {
    }

    @FXML
    void gotologin(ActionEvent event) {
    }
}
