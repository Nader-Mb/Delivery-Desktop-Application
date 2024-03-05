package controllers;

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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Reservation;
import models.Restaurant;
import services.ReservationService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ShowRestaurantReservationController {
    @FXML
    private Stage stage;


    @FXML
    private Button add;

    @FXML
    private Button usersbut;

    @FXML
    private Button logoutbut;

    @FXML
    private Text restaurantname;

    @FXML
    private Button dashboardbut;

    @FXML
    private Button restaubut;

    @FXML
    private Button campaignbut;

    @FXML
    private Button recipesbut;

    @FXML
    private Button update;

    @FXML
    private ListView<Reservation> showreservation;

    @FXML
    private Button delete;

    @FXML
    private Button cancelall;
    @FXML
    private Restaurant selectedRestaurant;
    @FXML
    public void initData(Restaurant restaurant) {
        this.selectedRestaurant = restaurant;
        restaurantname.setText(selectedRestaurant.getName());
        refreshListView();
    }

    @FXML
    private void refreshListView() {
        try {
            ReservationService reservationService = new ReservationService();
            List<Reservation> reservations = reservationService.getReservationsByRestaurant(selectedRestaurant.getRestaurantId());
            ObservableList<Reservation> observableReservations = FXCollections.observableArrayList(reservations);
            showreservation.setItems(observableReservations);
        } catch (SQLException e) {
            showErrorAlert("Error loading reservations", e);
        }
    }

    @FXML
    private void showErrorAlert(String title, SQLException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText("Error: " + e.getMessage());
        alert.showAndWait();
    }

    @FXML
    void gotodashboard(ActionEvent event) {
        // Implement the method logic
    }

    @FXML
    void gotousers(ActionEvent event) {
        // Implement the method logic
    }

    @FXML
    void gotorecipes(ActionEvent event) {
        // Implement the method logic
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
    void gotocampaign(ActionEvent event) {
        // Implement the method logic
    }

    @FXML
    void gotologin(ActionEvent event) {
        // Implement the method logic
    }

    @FXML
    void delete(ActionEvent event) {
        // Implement the method logic
    }

    @FXML
    void cancel(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/showreservation.fxml"));
            Parent root = loader.load();
            // Get the controller instance
            ShowReservationController controller = loader.getController();
            // Pass the stage to the ShowReservationController
            Stage stage = (Stage) cancelall.getScene().getWindow();
            controller.setStage(stage);
            // Set the scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showIOErrorAlert("Error loading showreservation.fxml", e);
        }
    }

    private void showIOErrorAlert(String title, IOException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText("Error: " + e.getMessage());
        alert.showAndWait();
    }

    @FXML
    void update(ActionEvent event) {
        // Implement the method logic
    }

    @FXML
    void add(ActionEvent event) {
        // Implement the method logic
    }

    @FXML
    public void setStage(Stage stage) {
        this.stage=stage;
    }
}
