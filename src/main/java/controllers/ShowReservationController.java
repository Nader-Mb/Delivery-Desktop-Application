package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Reservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import services.ReservationService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ShowReservationController {

        @FXML
        private Button usersbut;

        @FXML
        private Button logoutbut;

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



        private final ReservationService reservationService = new ReservationService();

    public ShowReservationController() throws SQLException {
    }

    @FXML
        void initialize() {

            refreshListView();
            
        }


    @FXML
    void delete(ActionEvent event) {
        Reservation selectedReservation = (Reservation) showreservation.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Delete Reservation");
            alert.setContentText("Are you sure you want to delete this reservation?");

            ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == buttonTypeYes) {
                try {
                    reservationService.delete(selectedReservation.getReservationId());
                    showreservation.getItems().remove(selectedReservation);
                } catch (SQLException e) {
                    showErrorAlert("Error deleting reservation", e);
                }
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Reservation Selected", "Please select a reservation to delete.");
        }
    }

    @FXML
    void cancel(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/showrestaurant.fxml"));
            Parent root = loader.load();
            ShowRestaurantController controller = loader.getController();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            controller.setStage(stage);

            // Pass the selected restaurant to the next scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            showErrorAlert("Error updating restaurant", e);
        }
    }




    @FXML
    void update(ActionEvent event) {
        Reservation selectedReservation = showreservation.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/updatingreservation.fxml"));
                Parent root = loader.load();
                UpdateReservationController controller = loader.getController();
                // Pass the selected reservation to the UpdateReservationController
                controller.initData(selectedReservation);
                // Pass the stage to the UpdateReservationController
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                controller.setStage(stage);

                // Pass the selected reservation to the next scene
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                showErrorAlert("Error updating reservation", e);
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Reservation Selected", "Please select a reservation to update.");
        }
    }


    @FXML
    private void refreshListView() {
        try {
            List<Reservation> reservations = reservationService.retrieveAll();

            ObservableList<Reservation> observableReservations = FXCollections.observableArrayList(reservations);

            showreservation.setItems(observableReservations);
            showreservation.setCellFactory(new Callback<ListView<Reservation>, ListCell<Reservation>>() {
                @Override
                public ListCell<Reservation> call(ListView<Reservation> listView) {
                    return new ListCell<Reservation>() {
                        @Override
                        protected void updateItem(Reservation reservation, boolean empty) {
                            super.updateItem(reservation, empty);

                            if (reservation == null || empty) {
                                setText(null);
                                setGraphic(null);
                            } else {
                                BorderPane borderPane = new BorderPane();

                                // Create a VBox for reservation data
                                VBox vbox = new VBox();
                                vbox.setSpacing(5);

                                Text restaurantText = new Text("Restaurant Name: " + getRestaurantName(reservation.getTableId()));
                                restaurantText.setStyle("-fx-font-weight: bold;");
                                Text dateText = new Text("Reservation Date: " + reservation.getDateTime());
                                dateText.setStyle("-fx-font-weight: bold;");
                                Text numberOfPersonsText = new Text("Number of Persons: " + reservation.getNumberOfPersons());
                                numberOfPersonsText.setStyle("-fx-font-weight: bold;");

                                vbox.getChildren().addAll(restaurantText, dateText, numberOfPersonsText);

                                // Create an HBox for the "view" button
                                HBox hbox = new HBox();
                                hbox.setAlignment(Pos.CENTER);
                                hbox.setPadding(new Insets(5));

                                Button viewButton = new Button("View");
                                viewButton.setStyle("-fx-background-color: #00796b; -fx-text-fill: white;");
                                viewButton.setOnAction(event -> redirectToBeachReservation(viewButton, reservation));

                                hbox.getChildren().add(viewButton);

                                // Set the VBox with reservation data to the left of the BorderPane
                                borderPane.setLeft(vbox);

                                // Set the HBox with the "view" button to the right of the BorderPane
                                borderPane.setRight(hbox);

                                // Set the BorderPane as the graphic of the cell
                                setGraphic(borderPane);
                            }
                        }


                    };
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void redirectToBeachReservation(Button viewButton, Reservation reservation) {
        // Assuming you have a reference to the main application's stage
        Stage stage = (Stage) viewButton.getScene().getWindow();

        // Load the BeachReservation FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ticket.fxml"));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            showErrorAlert("Error loading BeachReservation.fxml", e);
            return;
        }

        // Get the controller of the BeachReservation
        BeachReservationController beachReservationController = loader.getController();

        // Pass the reservation data to the BeachReservationController
        beachReservationController.initData(reservation);

        // Set the scene with BeachReservation as the root
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void handleViewReservation(Reservation reservation) {
        try {
            // Load the beach reservation scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/beachreservation.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            BeachReservationController controller = loader.getController();

            // Pass any necessary data to the controller if needed
            controller.initData(reservation);

            // Set the stage
            Stage stage = (Stage) showreservation.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showErrorAlert("Error loading beach reservation scene", e);
        }
    }


    private String getRestaurantName(int tableId) {
        try {
            ReservationService rs = new ReservationService();
            return rs.getRestaurantNameByResId(tableId);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Unknown"; // Handle error gracefully
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
    private void showErrorAlert(String title, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText("Error: " + e.getMessage());
        alert.showAndWait();
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

        }

        @FXML
        void gotologin(ActionEvent event) {

        }


    public void setStage(Stage stage) {
    }

    public void add(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/addingreservation.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            AddReservationController controller = loader.getController();

            // Pass the stage to the AddReservationController
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            controller.setStage(stage);

            // Set the scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Handle or log the exception
        }
    }


}




