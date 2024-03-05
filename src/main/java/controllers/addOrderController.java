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
import javafx.stage.Stage;
import models.Commande;
import models.Sendmail;
import models.User;
import services.CommandeService;
import services.userService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class addOrderController implements Initializable {

    public Button addOrderAddButt;
    public TextField timeCommande;
    public TextField priceCTextField;
    public Button addOrderCancelButt;
    public ComboBox<User> clientCombobox;
    public DatePicker dateCommande;
    public ComboBox<String> paimentModeCombobox;
    public TextField quantite;
    public ComboBox<String> dishCombobox;

    @FXML
    private Label confirmationAjoutCommande;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userService userService = new userService();
        ObservableList<User> listClient = FXCollections.observableArrayList();
        ObservableList<String> listPlat = FXCollections.observableArrayList();
        List<String> listArrayPlat = Arrays.asList(
                "Pizza",
                "Pasta",
                "oujja",
                "Koskous"
        );

        dishCombobox.setItems(listPlat);
        listPlat.addAll(listArrayPlat);

        clientCombobox.setItems(listClient);
        dishCombobox.setItems(listPlat);
        try {
            listClient.addAll(userService.recuperer());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        clientCombobox.setItems(listClient);

    }
    private Sendmail sn = new Sendmail();

    public void addOrder(ActionEvent actionEvent) throws SQLException {
        User client = clientCombobox.getValue();
        String plat = dishCombobox.getValue();
        LocalDate dateC = dateCommande.getValue();
        String hourAndMinute = timeCommande.getText();
        String[] parts = hourAndMinute.split(":");
        // Extract hour and minute parts
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);
        LocalDateTime dateTimeCom = LocalDateTime.of(dateC, LocalTime.of(hour, minute));
        int qte = Integer.parseInt(quantite.getText());
        int price = Integer.parseInt(priceCTextField.getText()) * qte;
        CommandeService commandeService = new CommandeService();
        Commande c1=new Commande();
        c1.setIdUser(client.getId());
        c1.setDateCommande(dateTimeCom);
        c1.setAdresseLivraison(client.getAddress());
        c1.setMontantTotalCommande(price);
        c1.setPlats(plat);
        commandeService.ajouter(c1);
        confirmationAjoutCommande.setText("The order is added successfully !");
        sn.envoyer(
                "Dorsaf.Riahi@esprit.tn",
                "Order Command",
                "Order of price"+price+"delivered"
        );
        naviguezVersAffichage();
    }
    @FXML
    void naviguezVersAffichage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/afficherOrder.fxml"));
            quantite.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void afficherDelivery(){
        Stage primaryStage=new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/confirmationLivraison.fxml"));
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
