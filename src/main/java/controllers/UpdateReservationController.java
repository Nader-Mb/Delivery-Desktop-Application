package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Reservation;
import models.User;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import services.ReservationService;
import services.userService;

public class UpdateReservationController {
    @FXML
    private Stage stage;

    @FXML
    private DatePicker date;

    @FXML
    private TextField numberofpersons;

    @FXML
    private ChoiceBox<Integer> hour;

    @FXML
    private ChoiceBox<Integer> min;

    @FXML
    private Button updatereserv;

    @FXML
    private Button cancelall;

    @FXML
    private Text restautitle;
    @FXML
    private ReservationService reservationService;
    @FXML
    private Reservation selectedReservation;

    // Constructor
    public UpdateReservationController() throws SQLException {
        this.reservationService = new ReservationService();
    }
    // Twilio credentials
    private static final String ACCOUNT_SID = "AC1ab8e9fe026fe87a99280cd929de0cc6";
    private static final String AUTH_TOKEN = "7a306131f294964792f932f9d5cf0841";
    private static final String TWILIO_PHONE_NUMBER = "+16235526203";
    @FXML
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @FXML
    public void initialize() {
        // Initialize hour and minute items
        ObservableList<Integer> hourItems = FXCollections.observableArrayList();
        ObservableList<Integer> minuteItems = FXCollections.observableArrayList();
        for (int i = 8; i <= 22; i++) {
            hourItems.add(i);
        }
        for (int i = 0; i < 60; i += 15) {
            minuteItems.add(i);
        }

        // Set items for hour and minute ChoiceBoxes
        hour.setItems(hourItems);
        min.setItems(minuteItems);

        // Set default values
        hour.setValue(8);
        min.setValue(0);

        // Ensure that hour and minute ChoiceBoxes are modifiable
        hour.setDisable(false);
        min.setDisable(false);

    }
    @FXML
    private void sendSmsNotification(Reservation reservation) throws SQLException {
        // Initialize Twilio client
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // Prepare SMS message
        String message = "Your reservation has been successfully updated for " +
                reservation.getDateTime() + ". Thank you for choosing our restaurant!";
        // Retrieve the userId associated with the reservation
        int userId = reservation.getUserId();
        // Retrieve the User object associated with the userId using UserService
        userService userService = new userService(); // Instantiate UserService
        User user = userService.getUserById(userId); // Call non-static method
        // If the user is found, send the SMS
        if (user != null) {
            // Get the user's phone number
            String userPhoneNumber = "+216" + String.valueOf(user.getNumber());

            // Send SMS using Twilio
            Message.creator(
                            new PhoneNumber(userPhoneNumber),
                            new PhoneNumber(TWILIO_PHONE_NUMBER),
                            message)
                    .create();
        } else {
            System.out.println("User not found for reservation with userId: " + userId);
        }
    }




    @FXML
    void update(ActionEvent event) {
        if (selectedReservation != null) {
            try {
                // Get date and time from date picker and choice boxes
                LocalDate selectedDate = date.getValue();
                int selectedHour = hour.getValue();
                int selectedMinute = min.getValue();

                // Check if the selected date is in the past
                if (selectedDate.isBefore(LocalDate.now())) {
                    showAlert(Alert.AlertType.WARNING, "Invalid Date", "Please select a current or future date.");
                    return;
                }

                LocalDateTime dateTime = LocalDateTime.of(selectedDate, LocalTime.of(selectedHour, selectedMinute));

                // Get number of persons from text field
                int numberOfPersons;

                // Check if number of persons is provided
                String inputText = numberofpersons.getText().trim();
                if (inputText.isEmpty()) {
                    showAlert("Missing Information", "Please enter the number of persons.");
                    return;
                }

                try {
                    numberOfPersons = Integer.parseInt(inputText);
                    if (numberOfPersons <= 0 || numberOfPersons > 10) {
                        showAlert("Invalid Number of Persons", "Number of persons must be between 1 and 10.");
                        return;
                    }
                } catch (NumberFormatException e) {
                    showAlert("Invalid Input", "Please enter a valid number for number of persons.");
                    return;
                }


                // Update the reservation's data
                selectedReservation.setDateTime(dateTime);
                selectedReservation.setNumberOfPersons(numberOfPersons);

                // Perform update operation in the database
                reservationService.update(selectedReservation);
                // Call sendSmsNotification method after updating the reservation
                sendSmsNotification(selectedReservation);




                showAlert(Alert.AlertType.INFORMATION, "Update Successful", "Reservation details updated successfully.");
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Update Error", "Failed to update reservation details: " + e.getMessage());
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter valid numerical values for the number of persons.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Reservation Selected", "Please select a reservation to update.");
        }
        // After successful update, switch scene back to ShowReservation
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/showreservation.fxml"));
            Parent root = loader.load();
            ShowReservationController controller = loader.getController();

            // Pass the stage to the ShowRestaurantController

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            //stage.show();
        } catch (IOException e) {
            showErrorAlert("Error loading showrestaurant scene", e);
        }
    }

    @FXML
    private void showErrorAlert(String errorLoadingShowrestaurantScene, IOException e) {
    }
    @FXML
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    void cancel(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/showreservation.fxml"));
            Parent root = loader.load();
            ShowReservationController controller = loader.getController();

            // Pass the stage to the ShowReservationController
            controller.setStage(stage);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showErrorAlert("Error loading showreservation scene", e);
        }

    }
    @FXML
    public void initData(Reservation selectedReservation) {
        this.selectedReservation = selectedReservation;
        // Populate the fields with the selected reservation data
        date.setValue(selectedReservation.getDateTime().toLocalDate());
        hour.setValue(selectedReservation.getDateTime().getHour());
        min.setValue(selectedReservation.getDateTime().getMinute());
        numberofpersons.setText(String.valueOf(selectedReservation.getNumberOfPersons()));
    }
    @FXML
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
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
}
