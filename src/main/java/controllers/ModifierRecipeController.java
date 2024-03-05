package controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import models.Recette;
import services.CrudRecette;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class ModifierRecipeController implements Initializable {
    @FXML
    private Button btCancel;
    @FXML
    private TextField tfURLimg;
    @FXML
    private TextField tfNameRec;
    @FXML
    private ToggleGroup difficultyGr;
    @FXML
    private RadioButton rbEasy;
    @FXML
    private RadioButton rbAverage;
    @FXML
    private RadioButton rbDifficult;
    int id,rating,idU;
    String date;
    @FXML
    private TextArea tfDesc;

    @FXML
    private TextField tfServes;

    @FXML
    private Button btAddImg;
    @FXML
    private Button ButtSubmitRec;
    //combobox
    @FXML
    private ComboBox<String> CombCategory;
    @FXML
    private ComboBox<String> CombMin;
    @FXML
    private ComboBox<String> CombHour;

    private final String[] Hour = new String[]{"00H","01H", "02H", "03H", "+3H"};
    private final String[] Min = new String[]{"00min","05min", "10min", "15min", "20min","25min", "30min", "35min", "40min","45min", "50min", "55min"};

    private final String[] Category = new String[]{
            "Appetizers",
            "Main Courses",
            "Side Dishes",
            "Desserts",
            "Beverages"};
    CrudRecette RecCrud = new CrudRecette();

    public ModifierRecipeController(){
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.CombHour.setItems(FXCollections.observableArrayList(this.Hour).sorted());
        this.CombMin.setItems(FXCollections.observableArrayList(this.Min).sorted());
        this.CombCategory.setItems(FXCollections.observableArrayList(this.Category).sorted());

    }

    public void populateFields(Recette rec) {
        id=rec.getIdRec();
        rating=rec.getRating();
        date=rec.getDate();
        //idU=rec.getIdUser();

        tfServes.setText(String.valueOf(rec.getServes()));
        tfDesc.setText(rec.getDescription());
        tfNameRec.setText(rec.getNomRec());
        tfURLimg.setText(rec.getImageRec());
        CombCategory.setValue(rec.getCategoryR());
        CombHour.setValue(rec.getPrep().substring(0,2));
        CombMin.setValue(rec.getPrep().substring(3));

        if (rec.getDifficulty().equals("Easy")) {rbEasy.setSelected(true);
        } else if (rec.getDifficulty().equals("Average")) {
            rbAverage.setSelected(true);
        } else if (rec.getDifficulty().equals("Difficult")) {
            rbDifficult.setSelected(true);
        }
    }

    public String getDifficultyGr(ActionEvent event){
        if(rbEasy.isSelected()){
            return rbEasy.getText();}
        else if(rbAverage.isSelected()){
            return rbAverage.getText();}
        else if(rbDifficult.isSelected()){
            return rbDifficult.getText();}
        else return "error";
    }
    public String dureePreparation(){
        return this.CombHour.getValue()+ this.CombMin.getValue();
    }
    private String dateSys() {
        Date thisDate= new Date();
        SimpleDateFormat dateform =new SimpleDateFormat("MM/dd/YY");
        return dateform.format(thisDate);
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
        File file = fileChooser.showOpenDialog(tfURLimg.getScene().getWindow());
        if (file != null) {
            tfURLimg.setText(file.getPath());
        } else {
            System.out.println("No file has been selected");
        }

    }
    public void modifierRec() {
        Alert alert;
        if (this.tfNameRec.getText().isEmpty()|| this.tfDesc.getText().isEmpty()||this.CombCategory.getValue().isEmpty() ||this.CombHour.getValue().isEmpty() ||this.CombMin.getValue().isEmpty() ||this.tfURLimg.getText().isEmpty()||this.tfServes.getText().isEmpty()||this.getDifficultyGr(new ActionEvent()).equals("error")){
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing arguments");
            alert.setHeaderText(null);
            alert.setContentText("Make sure to fill in all the arguments");
            alert.showAndWait();
        }else if ((tfServes.getText().length()> 2) || (!tfServes.getText().matches("\\d+"))){
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Number of serves invalid");
            alert.setHeaderText(null);
            alert.setContentText("Check serves value");
            alert.showAndWait();
        }/*else if(!tfURLimg.getText().startsWith("@../../image/")){
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("URL image invalid");
            alert.setHeaderText(null);
            alert.setContentText("Correct URL image to @../../image/nomImg.format");
            alert.showAndWait();
        }*/else{
        try {
            //    int serves, String prep, String description, String date, int rating, int idUser, String imageRec) {
           RecCrud.modifier(id,new Recette(tfNameRec.getText(),CombCategory.getValue(),this.getDifficultyGr(new ActionEvent()),
                   Integer.parseInt(tfServes.getText()),this.dureePreparation(),
                   tfDesc.getText(),date,rating,2,
                   tfURLimg.getText()));

        } catch (SQLException e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherRecette4.fxml"));
            rbDifficult.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());}
    }}

    @FXML
    public void cancel(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherRecette4.fxml"));
            btCancel.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }
/*
    @FXML
    void 1b6135(ActionEvent event) {

    }

    @FXML
    void 1b6135(ActionEvent event) {

    }

    @FXML
    void 1b6135(ActionEvent event) {

    }

    @FXML
    void getDifficultyGr(ActionEvent event) {

    }

    @FXML
    void getDifficultyGr(ActionEvent event) {

    }

    @FXML
    void getDifficultyGr(ActionEvent event) {

    }

    @FXML
    void 00785c(ActionEvent event) {

    }

    @FXML
    void modifierRec(ActionEvent event) {

    }

    @FXML
    void 1b6135(ActionEvent event) {

    }

    @FXML
    void 1b6135(ActionEvent event) {

    }

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
*/
}
