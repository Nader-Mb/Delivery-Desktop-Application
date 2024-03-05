package controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import models.Menu;
import services.CrudMenu;
import controllers.AfficherMenuController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModifierPlatMenuController implements Initializable {

    @FXML
    private Button btCancel;
    @FXML
    private Button btAddImg;
    @FXML
    private ComboBox<String> CombCategoryD;
    @FXML
    private TextField tfPriceD;
    @FXML
    private TextField tfIngD;
    @FXML
    private TextField tfNameD;
    @FXML
    private TextField tfURL_ImgD;
    int id,idR;
    @FXML
    private Button btMdPlat;

    private final String[] Category = new String[]{
            "Appetizers",
            "Main Courses",
            "Side Dishes",
            "Desserts",
            "Beverages"};
    CrudMenu MenuCrud = new CrudMenu();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.CombCategoryD.setItems(FXCollections.observableArrayList(this.Category).sorted());

    }
public ModifierPlatMenuController(){
    }

    public void populateFields(Menu menu) {
        id=menu.getIdP();
        //idR=menu.getRestaurantId();
        tfPriceD.setText(String.valueOf(menu.getPriceP()));
        tfIngD.setText(menu.getIngredientsP());
        tfNameD.setText(menu.getNameP());
        tfURL_ImgD.setText(menu.getImageP());
        CombCategoryD.setValue(menu.getCategoryP());
    }

    @FXML
    public void cancel(ActionEvent event) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/AfficherMenu2.fxml"));
                btCancel.getScene().setRoot(root);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }

    }

    @FXML
    void AddImage(ActionEvent event) {

        String userHome = System.getProperty("user.home");
        Path downloadsPath = Paths.get(userHome, "Downloads");
        System.out.println("Downloads Path: " + downloadsPath);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("choose an image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("jpg images", "*.jpg"),
                new FileChooser.ExtensionFilter("png images", "*.png"), new FileChooser.ExtensionFilter("all images", "*.jpg", "*.png"));
        File file = fileChooser.showOpenDialog(tfURL_ImgD.getScene().getWindow());
        if (file != null) {
            tfURL_ImgD.setText(file.getPath());
        } else {
            System.out.println("No file has been selected");
        }

    }
    public void ModifierPlat() {
        Alert alert;
        if (this.tfNameD.getText().isEmpty()|| this.tfPriceD.getText().isEmpty()||this.CombCategoryD.getValue().isEmpty() ||this.tfIngD.getText().isEmpty()||this.tfURL_ImgD.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing arguments");
            alert.setHeaderText(null);
            alert.setContentText("Make sure to fill in all the arguments");
            alert.showAndWait();
        }else if ((tfPriceD.getText().length() > 6) || (!tfPriceD.getText().matches("\\d+(\\.\\d+)?"))) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Price value not accepted");
            alert.setHeaderText(null);
            alert.setContentText("Check price value");
            alert.showAndWait();
        }/*else if(!tfURL_ImgD.getText().startsWith("@../../image/")){
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("URL image invalid");
            alert.setHeaderText(null);
            alert.setContentText("Correct URL image to @../../image/nomImg.format");
            alert.showAndWait();
        }*/
        else{
        try {
            MenuCrud.modifier(id,new Menu(tfNameD.getText(),
            Float.parseFloat(tfPriceD.getText()),
            CombCategoryD.getValue(),
            tfIngD.getText(),
            2,
            tfURL_ImgD.getText()));

        } catch (SQLException e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherMenu2.fxml"));
            btMdPlat.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());}
        }}


/*
    @FXML
    void 82391a(ActionEvent event) {

    }

    @FXML
    void 034a03(ActionEvent event) {

    }

    @FXML
    void d1dad1(ActionEvent event) {

    }

    @FXML
    void 867878(ActionEvent event) {

    }

    @FXML
    void 5e0707(ActionEvent event) {

    }

    @FXML
    void ModifierPlat(ActionEvent event) {

    }

    @FXML
    void 1b6135(ActionEvent event) {

    }*/

}

