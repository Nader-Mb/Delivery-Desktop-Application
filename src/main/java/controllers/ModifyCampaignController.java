package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.CampaignService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModifyCampaignController implements Initializable {

    @FXML
    private ImageView notifIcon;

    @FXML
    private Button usersMenu;

    @FXML
    private Button coampaignMenu;

    @FXML
    private ImageView imageField;

    @FXML
    private TextField associationNameField;

    @FXML
    private Button addImage;

    @FXML
    private ImageView settingsIcon;

    @FXML
    private TextField numberFiled;

    @FXML
    private TextField descriptionField;

    @FXML
    private ComboBox<String> campaignTypeField;

    @FXML
    private Button dashBoardMenu;

    @FXML
    private TextField campaignTitleField;

    @FXML
    private Button ModifyCampainButton;

    @FXML
    private Button recipesMenu;

    @FXML
    private TextField GoalField;

    @FXML
    private Button restaurantsMenu;

    @FXML
    private ImageView imageProfil;

    @FXML
    private Label helloLabel;

    @FXML
    private Label nameLabel;

    private final CampaignService campaignService = new CampaignService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableArrayList("Food", "Money");
        campaignTypeField.setItems(list);
    }

    @FXML
    void addImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("choose an image");
        fileChooser.setInitialDirectory(new File("/Users/NaderMb/Downloads"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("jpg images", "*.jpg"),
                new FileChooser.ExtensionFilter("png images", "*.png"), new FileChooser.ExtensionFilter("all images", "*.jpg", "*.png"));
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            Image image = new Image(file.getPath());
            imageField.setImage(image);
        } else {
            System.out.println("No file has been selected");
        }
    }


    int idCampaign;

    public void initializeValues(int number, int goal, String title, String associationName, String description, int id) {
        numberFiled.setText(Integer.toString(number));
        GoalField.setText(Integer.toString(goal));
        campaignTitleField.setText(title);
        associationNameField.setText(associationName);
        descriptionField.setText(description);
        idCampaign = id;
    }


    public void switchToDisplayAllCampaigns() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Campaign.fxml"));
            Parent newPageRoot = loader.load();


            Scene pageScene = new Scene(newPageRoot);
            Stage stage = (Stage) campaignTitleField.getScene().getWindow();
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
            Stage stage = (Stage) campaignTitleField.getScene().getWindow();
            stage.setScene(pageScene);

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }
    }

    public void modifyCampaign(ActionEvent actionEvent) {
        if (numberFiled.getText().isEmpty() || GoalField.getText().isEmpty() || campaignTitleField.getText().isEmpty() || associationNameField.getText().isEmpty() ||
                campaignTypeField.getValue().isEmpty() || descriptionField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("At least one field is empty ");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields !");
            alert.showAndWait();
        } else if (!numberFiled.getText().matches(".*\\d.*")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid number");
            alert.setHeaderText(null);
            alert.setContentText("Phone number must be in numbers !");
            alert.showAndWait();

        } else if (!campaignTitleField.getText().matches("^[a-zA-Z ]+$")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Title must be all alphabetic characters");
            alert.setHeaderText(null);
            alert.setContentText("please fill this field only with alphabetic characters !");
            alert.showAndWait();
        } else if (!associationNameField.getText().matches("^[a-zA-Z ]+$")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Name must be all alphabetic characters");
            alert.setHeaderText(null);
            alert.setContentText("please fill this field only with alphabetic characters !");
            alert.showAndWait();
        } else if (!GoalField.getText().matches(".*\\d.*")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("the goal is invalid");
            alert.setHeaderText(null);
            alert.setContentText("the goal must be in numbers!");
            alert.showAndWait();

        } else if (numberFiled.getText().length() != 8) {


            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid number");
            alert.setHeaderText(null);
            alert.setContentText("Phone number must be 8 numbers");
            alert.showAndWait();
        } else {
            try {

                campaignService.modifier(Integer.parseInt(numberFiled.getText()), Integer.parseInt(GoalField.getText()), campaignTitleField.getText(),
                        associationNameField.getText(), campaignTypeField.getValue(), descriptionField.getText(), idCampaign);

                switchToDisplayAllCampaigns();

            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error while modifying a campaign");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
                throw new RuntimeException(e);
            }
        }

    }


}
