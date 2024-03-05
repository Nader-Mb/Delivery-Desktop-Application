package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Restaurant;
import services.RestaurantService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class UpdateRestaurantController {

    @FXML
    private TextArea descid;

    @FXML
    private Button save;

    @FXML
    private TextField nameid;

    @FXML
    private TextField addressid;

    @FXML
    private Button cancelall;
    @FXML
    private Restaurant selectedRestaurant;
    @FXML
    private RestaurantService restaurantService;
    @FXML
    private Button updateimage;

    @FXML
    private ImageView imageUpdated;
    @FXML
    private Stage stage;
    // Constructor
    public UpdateRestaurantController() throws SQLException {
        this.restaurantService = new RestaurantService(); // Initialize the service
    }
    @FXML
    void uploadUpdatedimage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String selectedImagePath = file.getAbsolutePath(); // Get the absolute path of the selected image
            imageUpdated.setImage(new javafx.scene.image.Image(file.toURI().toString())); // Display the image in the ImageView
            selectedRestaurant.setImagePath(selectedImagePath); // Update the restaurant image path
        }
    }


    @FXML
    public void initData(Restaurant selectedRestaurant) {
        this.selectedRestaurant = selectedRestaurant;
        // Populate the fields with the selected restaurant data
        nameid.setText(selectedRestaurant.getName());
        addressid.setText(selectedRestaurant.getAddress());
        descid.setText(selectedRestaurant.getDescription());

        // Load and display the old image
        String oldImagePath = selectedRestaurant.getImagePath();
        if (oldImagePath != null && !oldImagePath.isEmpty()) {
            javafx.scene.image.Image oldImage = new javafx.scene.image.Image(new File(oldImagePath).toURI().toString());
            imageUpdated.setImage(oldImage);
        }

    }

    @FXML
    void saveupdate(ActionEvent event) {
        // Validate input
        String newName = nameid.getText().trim();
        String newAddress = addressid.getText().trim();
        String newDescription = descid.getText().trim();

        if (newName.isEmpty() || newAddress.isEmpty() || newDescription.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please fill in all fields.");
            return;
        }

        // Check if selectedRestaurant is not null
        if (this.selectedRestaurant != null) {
            try {
                // Update the restaurant's data
                this.selectedRestaurant.setName(newName);
                this.selectedRestaurant.setAddress(newAddress);
                this.selectedRestaurant.setDescription(newDescription);

                // Perform update operation in the database
                restaurantService.update(selectedRestaurant);

                showAlert(Alert.AlertType.INFORMATION, "Update Successful", "Restaurant details updated successfully.");
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Update Error", "Failed to update restaurant details: " + e.getMessage());
            }
        } else {
            System.err.println("Error: selectedRestaurant is null");
            // Optionally, you can show an alert here to inform the user that no restaurant is selected.
        }
        // After successful update, switch scene back to ShowRestaurant
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/showrestaurant.fxml"));
            Parent root = loader.load();
            ShowRestaurantController controller = loader.getController();

            // Pass the stage to the ShowRestaurantController
            controller.setStage(stage);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showErrorAlert("Error loading showrestaurant scene", e);
        }
    }

    private void showErrorAlert(String errorLoadingShowrestaurantScene, IOException e) {
    }

    @FXML
    void cancel(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/showrestaurant.fxml"));
            Parent root = loader.load();
            ShowRestaurantController controller = loader.getController();

            // Pass the stage to the ShowReservationController
            controller.setStage(stage);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showErrorAlert("Error loading show restaurant scene", e);
        }
    }

    @FXML
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void typename(ActionEvent event) {

    }

    @FXML
    void typeaddress(ActionEvent event) {

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
    public void gotocampaign(ActionEvent actionEvent) {
    }
    @FXML
    public void gotologin(ActionEvent actionEvent) {
    }
    @FXML
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}







