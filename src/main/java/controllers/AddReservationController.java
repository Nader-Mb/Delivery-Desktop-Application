package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import models.Reservation;
import services.ReservationService;
import services.RestaurantTableService;
import services.userService;

public class AddReservationController {
    // Twilio credentials
    private static final String ACCOUNT_SID = "AC1ab8e9fe026fe87a99280cd929de0cc6";
    private static final String AUTH_TOKEN = "7a306131f294964792f932f9d5cf0841";
    private static final String TWILIO_PHONE_NUMBER = "+16235526203";


    @FXML
    private DatePicker date;

    @FXML
    private Button logoutbut;

    @FXML
    private Button campaignbut;

    @FXML
    private Button restaubut;

    @FXML
    private Button recipesbut;

    @FXML
    private Button addreserv;

    @FXML
    private TextField numberofpersons;

    @FXML
    private Button cancelall;

    @FXML
    private Button usersbut;

    @FXML
    private ChoiceBox<Integer> min;

    @FXML
    private ChoiceBox<Integer> hour;

    @FXML
    private Button dashboardbut;
    @FXML
    private Stage stage;
    @FXML
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML

    private ObservableList<Integer> hourItems = FXCollections.observableArrayList();
    @FXML
    private ObservableList<Integer> minuteItems = FXCollections.observableArrayList();
    @FXML
    public void initialize() {
        // Initialize hour and minute items
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
    }
    @FXML
    void typenumber(ActionEvent event) {
        // Validate number of persons
        String inputText = numberofpersons.getText().trim();
        try {
            int numberOfPersons = Integer.parseInt(inputText);
            if (numberOfPersons > 10) {
                showAlert(Alert.AlertType.WARNING, "Invalid Number of Persons",
                        "Number of persons must be less than or equal to 10. Please reserve again.");
                numberofpersons.clear();
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input",
                    "Invalid input. Please enter a valid number.");
            numberofpersons.clear();
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
    void chosedate(ActionEvent event) {
        // Validate chosen date
        LocalDate selectedDate = date.getValue();
        LocalDate currentDate = LocalDate.now();
        if (selectedDate != null && selectedDate.isBefore(currentDate)) {
            System.out.println("Invalid date. Please choose a date from today onwards.");
            date.setValue(null);
        }
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
    void addreserv(ActionEvent event) {
        // Retrieve input values
        LocalDate selectedDate = date.getValue();
        int selectedHour = hour.getValue();
        int selectedMinute = min.getValue();
        int numberOfPersons;
        // Check if date, hour, and minute are selected
        if (date.getValue() == null || hour.getValue() == null || min.getValue() == null) {
            showAlert("Missing Information", "Please select both date and time.");
            return;
        }

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
        // Ensure the selected date is not in the past
        if (selectedDate.isBefore(LocalDate.now())) {
            showAlert("Invalid Date", "Please select a current or future date.");
            return;
        }
        // Create LocalDateTime object for reservation
        LocalDateTime dateTime = LocalDateTime.of(selectedDate, LocalTime.of(selectedHour, selectedMinute));

        // Get table ID
        int tableId = getTableID();

        // Create reservation object
        Reservation reservation = new Reservation(3, dateTime, tableId, numberOfPersons);


        // Add reservation
        ReservationService reservationService;
        try {
            reservationService = new ReservationService();
            reservationService.add(reservation);

            // Call sendSmsNotification method after adding the reservation
            sendSmsNotification(reservation);

            showAlert("Success", "Reservation added successfully!");
        } catch (SQLException e) {
            showAlert("Error", "Failed to add reservation: " + e.getMessage());
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

    private void sendSmsNotification(Reservation reservation) throws SQLException {
        // Initialize Twilio client
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // Prepare SMS message
        String message = "Your reservation has been successfully added for " +
                reservation.getDateTime() + ". Thank you for choosing our restaurant!";
        // Retrieve the userId associated with the reservation
        int userId = reservation.getUserId();
// Retrieve the User object associated with the userId using UserService
        userService userService = new userService(); // Instantiate UserService
        User user = userService.getUserById(userId); // Call non-static method
// If the user is found, send the SMS
        if (user != null) {
            // Get the user's phone number
            String userPhoneNumber = "+216"+String.valueOf(user.getNumber());

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
    private int getTableID() {
        int nextTableID = 0;
        try {
            RestaurantTableService tableService = new RestaurantTableService();
            List<RestaurantTable> tables = tableService.retrieveAll();
            if (!tables.isEmpty()) {
                int maxID = tables.stream().mapToInt(RestaurantTable::getTableId).max().getAsInt();
                nextTableID = maxID + 1;
            } else {
                nextTableID = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nextTableID;
    }
    @FXML
    public void gotodashboard(ActionEvent actionEvent) {
    }
    @FXML
    public void gotousers(ActionEvent actionEvent) {
    }
    @FXML
    public void gotocampaign(ActionEvent actionEvent) {
    }
    @FXML
    public void gotologin(ActionEvent actionEvent) {
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
    public void gotorecipes(ActionEvent actionEvent) {
    }




}




