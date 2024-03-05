package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Restaurant;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import javafx.scene.control.ListCell;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import services.RestaurantService;

import java.io.FileOutputStream;

public class ShowRestaurantController {




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
    private ListView<Restaurant> showrestaurant;


    @FXML
    private Button recipesbut;

    @FXML
    private Button update;

    @FXML
    private Button delete;

    @FXML
    private Button cancelall;
    @FXML
    private TextField typesearch;
    @FXML
    private Button excel;

    private final RestaurantService restaurantService = new RestaurantService();

    public ShowRestaurantController() throws SQLException {
    }



    @FXML
    void initialize() {
        // Set up a custom cell factory for the ListView
        showrestaurant.setCellFactory(param -> new ListCell<>() {
            private final ImageView imageView = new ImageView();
            private final Label nameLabel = new Label();
            private final Label addressLabel = new Label();
            private final Label descriptionLabel = new Label();
            private final VBox textContainer = new VBox(nameLabel, addressLabel, descriptionLabel);
            private final HBox hBox = new HBox(imageView, textContainer);

            {
                // Configure image view size
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                imageView.setPreserveRatio(true);
                // Configure label properties
                nameLabel.setStyle("-fx-font-weight: bold;");
                addressLabel.setStyle("-fx-font-weight: bold;");
                descriptionLabel.setStyle("-fx-font-weight: bold;");

                // Set spacing between elements in the HBox
                textContainer.setSpacing(10);
                // Set padding for the textContainer to create space between it and imageView
                textContainer.setPadding(new Insets(0, 10, 0, 10)); // Adjust padding as needed
                // Set padding for the HBox
                hBox.setPadding(new javafx.geometry.Insets(5));
                // Set alignment for the HBox
                hBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
            }

            @Override
            protected void updateItem(Restaurant restaurant, boolean empty) {
                super.updateItem(restaurant, empty);

                if (empty || restaurant == null) {
                    setGraphic(null);
                } else {
                    // Display restaurant details with text aligned next to the image
                    imageView.setImage(new Image(new File(restaurant.getImagePath()).toURI().toString()));
                    nameLabel.setText("Name: " + restaurant.getName());
                    addressLabel.setText("Address: " + restaurant.getAddress());
                    descriptionLabel.setText("Description: " + restaurant.getDescription());
                    setGraphic(hBox);
                }
            }

        });
        // Add listener to the typesearch TextField to trigger search as user types
        typesearch.textProperty().addListener((observable, oldValue, newValue) -> {
            String searchText = typesearch.getText().trim();
            if (!searchText.isEmpty()) {
                try {
                    List<Restaurant> restaurants = restaurantService.search(searchText);
                    showrestaurant.getItems().clear();
                    showrestaurant.getItems().addAll(restaurants);
                } catch (SQLException e) {
                    showErrorAlert("Error searching restaurants", e);
                }
            } else {
                refreshListView(); // If search text is empty, refresh the list to show all restaurants
            }
        });

        refreshListView();
    }

    @FXML
    private void showErrorAlert(String title, SQLException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText("Error: " + e.getMessage());
        alert.showAndWait();
    }




    @FXML
    private Stage stage;
    @FXML
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @FXML
    void update(ActionEvent event) {
        Restaurant selectedRestaurant = showrestaurant.getSelectionModel().getSelectedItem();
        if (selectedRestaurant != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/updatingrestaurant.fxml"));
                Parent root = loader.load();
                UpdateRestaurantController controller = loader.getController();
                // Pass the selected restaurant to the UpdateRestaurantController
                controller.initData(selectedRestaurant);
                // Pass the stage to the UpdateRestaurantController
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                controller.setStage(stage);

                // Pass the selected restaurant to the next scene
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                showErrorAlert("Error updating restaurant", e);
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Restaurant Selected", "Please select a restaurant to update.");
        }

    }



    @FXML
    void delete(ActionEvent event) {
        Restaurant selectedRestaurant = showrestaurant.getSelectionModel().getSelectedItem();
        if (selectedRestaurant != null) {

            if (selectedRestaurant != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Delete Restaurant");
                alert.setContentText("Are you sure you want to delete this restaurant?");

                ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);
                alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == buttonTypeYes) {
                    try {
                        restaurantService.delete(selectedRestaurant.getRestaurantId());
                        showrestaurant.getItems().remove(selectedRestaurant);
                    } catch (SQLException e) {
                        showErrorAlert("Error deleting restaurant", e);
                    }
                }
            } else {
                showAlert(Alert.AlertType.WARNING, "No Restaurant Selected", "Please select a restaurant to delete.");
            }

        } else {
            showAlert(Alert.AlertType.WARNING, "No Restaurant Selected", "Please select a restaurant to delete.");
        }
    }

    @FXML
    void cancel(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/showreservation.fxml"));
            Parent root = loader.load();
            ShowReservationController controller = loader.getController();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            controller.setStage(stage);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            showErrorAlert("Error showing reservations", e);
        }
    }

    @FXML
    private void refreshListView() {
        try {
            List<Restaurant> restaurants = restaurantService.retrieveAll();
            showrestaurant.getItems().clear();
            showrestaurant.getItems().addAll(restaurants);
        } catch (SQLException e) {
            showErrorAlert("Error loading restaurants", e);
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
    private void showErrorAlert(String title, IOException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText("Error: " + e.getMessage());
        alert.showAndWait();
    }
    @FXML
    public void inputsearch(ActionEvent actionEvent) {
    }
    @FXML
    public void gotodashboard(ActionEvent actionEvent) {

    }
    @FXML
    public void gotorecipes(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherRecette4.fxml"));
            restaubut.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    public void gotousers(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ListUser.fxml"));
            restaubut.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
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
    public void gotocampaign(ActionEvent actionEvent) {
    }
    @FXML
    public void gotologin(ActionEvent actionEvent) {
    }
    @FXML
    public void add(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/addingrestaurant.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            AddRestaurantController controller = loader.getController();

            // Pass the stage to the AddReservationController
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            controller.setStage(stage);

            // Set the scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace(); // Handle or log the exception
        }}
    @FXML
    void SearchRestau(ActionEvent event) {
        String searchText = typesearch.getText().trim();
        if (!searchText.isEmpty()) {
            try {
                List<Restaurant> restaurants = restaurantService.search(searchText);
                showrestaurant.getItems().clear();
                showrestaurant.getItems().addAll(restaurants);
            } catch (SQLException e) {
                showErrorAlert("Error searching restaurants", e);
            }
        } else {
            refreshListView(); // If search text is empty, refresh the list to show all restaurants
        }
    }

    @FXML
    private String excelFileName = "restaurant_data.xlsx"; // Field to store the file name

    @FXML
    void showexcel(ActionEvent event) {
        refreshListView();
        File file = new File(excelFileName);
        if (file.exists()) {
            exportToExcel();
            openExcelFile(file);
        } else {
            exportToExcel();
        }
    }
    @FXML
    private void exportToExcel() {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Restaurant Data");

            // Define styles for header and data cells
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            Font headerFont = workbook.createFont();
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerFont.setBold(true); // Make header text bold
            headerStyle.setFont(headerFont);

            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setBorderBottom(BorderStyle.THIN);
            dataStyle.setBorderTop(BorderStyle.THIN);
            dataStyle.setBorderRight(BorderStyle.THIN);
            dataStyle.setBorderLeft(BorderStyle.THIN);

            // Create headers
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Name");
            headerRow.createCell(1).setCellValue("Address");
            headerRow.createCell(2).setCellValue("Description");
            headerRow.createCell(3).setCellValue("Image Path");

            // Apply header style
            for (Cell cell : headerRow) {
                cell.setCellStyle(headerStyle);
            }

            // Populate data
            int rowNum = 1;
            for (Restaurant restaurant : showrestaurant.getItems()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(restaurant.getName());
                row.createCell(1).setCellValue(restaurant.getAddress());
                row.createCell(2).setCellValue(restaurant.getDescription());
                row.createCell(3).setCellValue(restaurant.getImagePath());

                // Apply data style
                for (Cell cell : row) {
                    cell.setCellStyle(dataStyle);
                }
            }

            // Auto-size columns
            for (int i = 0; i < 4; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write the output to a file
            try (FileOutputStream fileOut = new FileOutputStream(excelFileName)) {
                workbook.write(fileOut);
                System.out.println("Excel file created successfully.");
                // Open the created Excel file
                openExcelFile(new File(excelFileName));
            } catch (IOException e) {
                showErrorAlert("Error exporting data to Excel", e);
            }
        } catch (IOException e) {
            showErrorAlert("Error creating Excel workbook", e);
        }
    }
    @FXML
    private void openExcelFile(File file) {
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            showErrorAlert("Error opening Excel file", e);
        }
    }
    @FXML
    private void showRestaurantReservations(ActionEvent event) {
        Restaurant selectedRestaurant = showrestaurant.getSelectionModel().getSelectedItem();
        if (selectedRestaurant != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/showrestaurantreservation.fxml"));
                Parent root = loader.load();
                ShowRestaurantReservationController controller = loader.getController();
                // Pass the selected restaurant to the ShowRestaurantReservationController
                controller.initData(selectedRestaurant);
                // Pass the stage to the ShowRestaurantReservationController
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                controller.setStage(stage);

                // Set the scene
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                showErrorAlert("Error showing restaurant reservations", e);
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Restaurant Selected", "Please select a restaurant.");
        }
    }




}










