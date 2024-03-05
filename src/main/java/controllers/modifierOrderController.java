package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Commande;
import models.User;
import services.CommandeService;
import services.userService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class modifierOrderController {
    @FXML
    private TextField UpdatetimeCommande;

    @FXML
    private Button addOrderDeliveryButt;

    @FXML
    private DatePicker UpdatedateCommande;

    @FXML
    private Label confirmationAjoutCommande;

    @FXML
    private ComboBox<User> UpdateclientCombobox;

    @FXML
    private ComboBox<String> UpdatOrderedishCombobox;

    @FXML
    private Button addOrderRecipesButt;

    @FXML
    private Button updateOrderUpdateButt;

    @FXML
    private Button addOrderLogOutButt;

    @FXML
    private Button updateOrderCancelButt;

    @FXML
    private Button addOrderCompaignButt1;


    @FXML
    private TextField updatePriceCTextField;

    @FXML
    private Button addOrderHomeButt;

    @FXML
    private Button addOrderOrdersButt;



Commande c;

int idModifier;




    @FXML
    public void initData(Commande commande) {

            c=commande;
        idModifier=c.getIdCommande();
        userService userService = new userService();
        ObservableList<User> listClient = FXCollections.observableArrayList();
        ObservableList<String> listPlat = FXCollections.observableArrayList();
        List<String> listArrayPlat = Arrays.asList(
                "Pizza",
                "Pasta",
                "oujja",
                "Koskous"
        );
        listPlat.addAll(listArrayPlat);
        UpdatOrderedishCombobox.setItems(listPlat);



        try {
            listClient.addAll(userService.recuperer());
            UpdateclientCombobox.setItems(listClient);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            UpdateclientCombobox.setValue(userService.recupererUtilisateur(c.getIdUser()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        UpdatOrderedishCombobox.setValue(c.getPlats());


        // Extraire la date (sans l'heure) pour l'objet DatePicker
        LocalDate localDate = c.getDateCommande().toLocalDate();

        // Extraire l'heure et les minutes sous forme de chaînes de caractères
        String heure = String.valueOf(c.getDateCommande().getHour());
        String minute = String.valueOf(c.getDateCommande().getMinute());

        // Créer un objet DatePicker et définir sa valeur sur la date extraite
        DatePicker datePicker = new DatePicker(localDate);

        // Convertir l'heure et les minutes en chaînes de deux chiffres (en ajoutant un zéro si nécessaire)
        heure = heure.length() == 1 ? "0" + heure : heure;
        minute = minute.length() == 1 ? "0" + minute : minute;
        UpdatedateCommande.setValue(localDate);
        UpdatetimeCommande.setText(heure+":"+minute);
        updatePriceCTextField.setText(String.valueOf(c.getMontantTotalCommande()));


    }



    @FXML
    public void modifier(javafx.event.ActionEvent actionEvent) {

        User client = UpdateclientCombobox.getValue();
        String plat = UpdatOrderedishCombobox.getValue();
        LocalDate dateC = UpdatedateCommande.getValue();
        String hourAndMinute = UpdatetimeCommande.getText();
        String[] parts = hourAndMinute.split(":");
        // Extract hour and minute parts
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);
        LocalDateTime dateTimeCom = LocalDateTime.of(dateC, LocalTime.of(hour, minute));
        double montant =Double.parseDouble(updatePriceCTextField.getText()) ;
        CommandeService commandeService = new CommandeService();


            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Are you sure you want to modify  ?");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        commandeService.modifierById(new Commande(c.getIdCommande(), client.getId(),dateTimeCom, client.getAddress(), montant, plat,9),5);
                        Stage primaryStage=new Stage();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherOrder.fxml"));
                        Parent root = null;
                        try {
                            root = loader.load();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Scene scene = new Scene(root);
                        primaryStage.setTitle("");
                        primaryStage.setScene(scene);
                        primaryStage.show();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    @FXML
    public  void cancel(){
        Stage primaryStage=new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherOrder.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        primaryStage.setTitle("");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

