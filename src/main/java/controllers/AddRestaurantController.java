package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Restaurant;
import services.RestaurantService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class AddRestaurantController {
    private final RestaurantService restaurantService = new RestaurantService();
    private String restaurantImagePath;
    public String getRestaurantImagePath() {
        return restaurantImagePath;
    }
    @FXML
    public Button dashboardbut;

    @FXML
    private TextField nameid;

    @FXML
    private TextField addressid;

    @FXML
    private TextArea descid;
    @FXML
    private ImageView image;
    @FXML
    private Button addimage;
    @FXML
    private Stage stage;


    public AddRestaurantController() throws SQLException {

        }

    @FXML
    void uploadimage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String selectedImagePath = file.getAbsolutePath(); // Get the absolute path of the selected image
            image.setImage(new javafx.scene.image.Image(file.toURI().toString())); // Display the image in the ImageView
            restaurantImagePath = selectedImagePath; // Set the restaurant image path
        }
    }

    // Method to add a restaurant
    @FXML
    void addrestau(ActionEvent event) {
        String name = nameid.getText();
        String address = addressid.getText();
        String description = descid.getText();

        if (name.isEmpty() || address.isEmpty() || description.isEmpty() || restaurantImagePath == null) {
            // Display an alert if any field is empty or if the image path is null
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all the fields and upload an image!");
            alert.showAndWait();
            return; // Exit the method if any field is empty or if the image path is null

        }

        try {
            // Create a new Restaurant object with the provided details and image path
            Restaurant restaurant = new Restaurant(name, address, description, restaurantImagePath, 3);
            restaurantService.add(restaurant); // Call the add method from RestaurantService to add the restaurant

            // Optionally, you can clear the input fields after adding the restaurant
            nameid.clear();
            addressid.clear();
            descid.clear();


            // Display an alert for successful addition
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Restaurant added successfully!");
            successAlert.showAndWait();

            // You might also display a success message or perform any other action
            System.out.println("Restaurant added successfully!");
        } catch (SQLException e) {
            // Handle any potential exceptions, such as database errors
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/showrestaurant.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            ShowRestaurantController controller = loader.getController();

            // Pass the stage to the AddReservationController

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            controller.setStage(stage);

            // Set the scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Handle or log the exception
        }
    }




    @FXML
    void cancel(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/showrestaurant.fxml"));
            Parent root = loader.load();
            ShowRestaurantController controller = loader.getController();

            // Pass the stage to the ShowRestaurantController
            controller.setStage(stage);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            controller.setStage(stage);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {

        }


    }

    private void showErrorAlert(String errorLoadingShowreservationScene, IOException e) {
    }

    private int getUserId() {

        return 1;
    }

    public void gotodashboard(ActionEvent actionEvent) {
    }

    public void gotousers(ActionEvent actionEvent) {
    }

    public void gotorecipes(ActionEvent actionEvent) {
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

    public void gotocampaign(ActionEvent actionEvent) {
    }

    public void gotologin(ActionEvent actionEvent) {
    }

    public void typename(ActionEvent actionEvent) {
    }

    public void typeaddress(ActionEvent actionEvent) {
    }

    public void setStage(Stage stage) {
    }
}