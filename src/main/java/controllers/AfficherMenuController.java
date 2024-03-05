package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import services.ExcelExporter;
import models.Menu;
import models.Recette;
import services.CrudMenu;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import services.SendSMS;

public class AfficherMenuController implements Initializable {
    @FXML
    private Button btMoveToUsers;
    @FXML
    private Button btMoveToCampaign;

    @FXML
    private Button btMoveToResto;
    @FXML
    private Button btInvSort;
    @FXML
    private Button btSort;

    @FXML
    private ComboBox<String> SortCombobox;
    @FXML
    private ComboBox<String> CombCategory;

    @FXML
    private ImageView imgMenuDish;
    @FXML
    private Button btMoveToRec;
    @FXML
    private Button btAjD;
    @FXML
    private Button btDelMenu;
    @FXML
    private Button btmodMenu;
    @FXML
    private Button btPrintMenu;
    @FXML
    private TextField tfPhoneNB;
    @FXML
    private Button btSendSMS;
    @FXML
    private ListView<Menu> lvMenu;
    @FXML
    private TextField tfSearchName;
    @FXML
    private Button btSearch;
    Menu menuSelectionne;
    private final CrudMenu crudMenu = new CrudMenu();

    private final String[] Category = new String[]{
            "Appetizers",
            "Main Courses",
            "Side Dishes",
            "Desserts",
            "Beverages"};
    private final String[] SortBy = new String[]{
            "Category","Name"};

    @FXML
    void goTousers(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ListUser.fxml"));
            btPrintMenu.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void goToResto(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/showrestaurant.fxml"));
            btPrintMenu.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void goToCampaign(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Campaign.fxml"));
            btPrintMenu.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialize();

        Tooltip tooltip = new Tooltip("Search by Category and/or a Keyword Match");
        Tooltip.install(btSearch, tooltip);

        Tooltip tooltip2 = new Tooltip("Sort by Category or Name");
        Tooltip.install(btSort, tooltip2);

        Tooltip tooltip3 = new Tooltip("Send SMS");
        Tooltip.install(btSendSMS, tooltip3);

        this.CombCategory.setItems(FXCollections.observableArrayList(Category).sorted());
        this.SortCombobox.setItems(FXCollections.observableArrayList(SortBy).sorted());
    }

    public void searchMenu(String category, String name ) throws SQLException {
        ObservableList<Menu> filteredMenu = FXCollections.observableArrayList();
        List<Menu> menuS = crudMenu.afficher();

        // Filtrage des recettes selon la catégorie et/ou la difficulté sélectionnées
        for (Menu menu : menuS) {
            boolean categoryMatch = category==null || menu.getCategoryP().equals(category);
            boolean nameSearch = name==null || menu.getNameP().toLowerCase().contains(name.toLowerCase());
            if (categoryMatch && nameSearch) {
                filteredMenu.add(menu);
            }
        }
        lvMenu.setItems(filteredMenu);
    }

    @FXML
    void searchMenuAction(ActionEvent event) {
        if(CombCategory.getValue()!=null || tfSearchName.getText()!=""){
            try {
                searchMenu(CombCategory.getValue(), tfSearchName.getText());
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
                throw new RuntimeException(e);
            }
        }

        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Choose criteria");
            alert.showAndWait();
        }
    }
    @FXML
    void SendSMS_Action(ActionEvent event) {
        if ((tfPhoneNB.getText()!= null)&&(tfPhoneNB.getText().matches("\\d+"))){
        SendSMS sender = new SendSMS();
        sender.send_SMS("+216"+tfPhoneNB.getText());}
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Check phone number");
            alert.showAndWait();
        }
    }

    @FXML
    void Sort_Action(ActionEvent event) {
        if(SortCombobox.getValue()!=null){
            try {
                sortMenu(SortCombobox.getValue());
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
                throw new RuntimeException(e);
            }}
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Choose criteria");
            alert.showAndWait();
        }
    }
    public void sortMenu(String selectedOption) throws SQLException {
        ObservableList<Menu> sortedRecipes = FXCollections.observableArrayList();
        List<Menu> menuS = crudMenu.afficher();

        switch (selectedOption) {
            case "Category":
                menuS.sort(Menu.compareByCategory());
                break;
            case "Name":
                menuS.sort((r1, r2) -> r1.getNameP().compareToIgnoreCase(r2.getNameP()));
                break;
        }

        sortedRecipes.addAll(menuS);
        lvMenu.setItems(sortedRecipes);
    }

    @FXML
    void InverseSort_Action(ActionEvent event) {
        ObservableList<Menu> viewedMenu = lvMenu.getItems();
        if (viewedMenu.size() > 0) {
            List<Menu> sortedMenu = new ArrayList<>(viewedMenu);
            Collections.reverse(sortedMenu);
            lvMenu.setItems(FXCollections.observableArrayList(sortedMenu));
        }  else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No recipes to sort");
            alert.showAndWait();
        }
    }

    public void initialize() {
        Tooltip tooltip2 = new Tooltip("Notify that Menu is updated");
        Tooltip.install(btSendSMS, tooltip2);

        ObservableList<Menu> menuList = FXCollections.observableArrayList();
        lvMenu.setItems(menuList);
        Alert alert;
        try {
            List<Menu> menuDetails = crudMenu.afficher();
            menuList.addAll(menuDetails);
            lvMenu.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number oldIndex, Number newIndex) {
                    menuSelectionne = lvMenu.getSelectionModel().getSelectedItem();}}
            );}
        catch (SQLException excp) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(excp.getMessage());
            alert.showAndWait();
        }}

    public void afficheDetailsM(){
        lvMenu.setOnMouseClicked(event -> {
            Menu menuSelectionne2 = lvMenu.getSelectionModel().getSelectedItem();
            File imageFile = new File(menuSelectionne2.getImageP());


            if (!imageFile.exists()) {
                System.out.println("File not found: " + imageFile.getAbsolutePath());
                return;
            }

            try (FileInputStream inputStream = new FileInputStream(imageFile)) {
                Image img = new Image(inputStream);
                imgMenuDish.setImage(img);
            } catch (IOException e) {
                System.err.println("Error loading image: " + e.getMessage());
            }

            /*      Image img = null;
                try {
                    img = new Image(imageFile.toURI().toURL().toString());
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);   }*/
        });}


    public void MoveToAjoutMenu(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterPlatauMenu.fxml"));
            lvMenu.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }}

    public void OnDeleteMenu(ActionEvent event) {
        menuSelectionne = lvMenu.getSelectionModel().getSelectedItem();
        if(menuSelectionne!=null){
            try {
            crudMenu.supprimer(menuSelectionne.getIdP());
                initialize();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select dish");
            alert.showAndWait();
        }}

    public void MovetoafficherRec(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherRecette4.fxml"));
            btMoveToRec.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void PrintMenuAction(ActionEvent event) throws SQLException {

        ExcelExporter excelGenerator = new ExcelExporter();
        CrudMenu crudMenu = new CrudMenu();

        List<Menu> menu = crudMenu.afficher();
        excelGenerator.generateExcel(menu);

        System.out.println("Excel file created !");
        /*
        try {
            Menu menu = lvMenu.getSelectionModel().getSelectedItem();
            if (menu != null) {
                printMenu(menu);}
        } catch (SQLException e) {
                throw new RuntimeException(e);}
             catch (Exception e) {
                throw new RuntimeException(e);
            }*/
    }

    public void onModifierMenu(ActionEvent event){
        menuSelectionne = lvMenu.getSelectionModel().getSelectedItem();
        if(menuSelectionne!=null){
            try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierPlatMenu2.fxml"));
            Parent root = loader.load();

            ModifierPlatMenuController ModifierPlatMenuController = loader.getController();

            menuSelectionne = lvMenu.getSelectionModel().getSelectedItem();

            ModifierPlatMenuController.populateFields(menuSelectionne);
            btmodMenu.getScene().setRoot(root);
            }
            catch (IOException e) {
                System.err.println(e.getMessage());
            }
            catch (Exception e) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Choose dish");
            alert.showAndWait();
        }
    }
}
