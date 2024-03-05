package controllers;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class HomeRestaurantController {
    @FXML
    private StackPane stack;

    @FXML
    private Pane statisticsPane;
    @FXML
    private Pane restaurantPane;
    @FXML
    private Pane reservationPane;

    @FXML
    private Button usersbut;

    @FXML
    private Button logoutbut;

    @FXML
    private Button dashboardbut;

    @FXML
    private Button reservations;

    @FXML
    private Button restaubut;

    @FXML
    private Button campaignbut;

    @FXML
    private Button recipesbut;

    @FXML
    private Button restaurants;

    @FXML
    private Button statistics;




    @FXML
    void showrestau(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/showrestaurant.fxml"));
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
    void showreserv(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/showreservation.fxml"));
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
    void showstat(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/STATS.fxml"));
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
    public void gotodashboard(ActionEvent actionEvent) {
    }
    @FXML
    public void gotousers(ActionEvent actionEvent) {
    }
    @FXML
    public void gotorecipes(ActionEvent actionEvent) {
    }
    @FXML
    public void gotocampaign(ActionEvent actionEvent) {
    }
    @FXML
    public void gotologin(ActionEvent actionEvent) {
    }
}
