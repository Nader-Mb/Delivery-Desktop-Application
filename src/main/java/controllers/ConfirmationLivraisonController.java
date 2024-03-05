package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Commande;
import models.Livraison;
import models.User;
import services.CommandeService;
import services.ServiceLivraison;
import services.userService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ConfirmationLivraisonController {
    @FXML
    private Button addOrderLogOutButt;

    @FXML
    private Button addOrderDeliveryButt;

    @FXML
    private Button addOrderCompaignButt1;

    @FXML
    private Label confirmationAjoutCommande;

    @FXML
    private Button DeliveryConfirmButt;

    @FXML
    private ListView<Commande> confirmationLivraisonLV;

    @FXML
    private Button addOrderHomeButt;

    @FXML
    private Button DeliveryCancelButt;

    @FXML
    private Button addOrderRecipesButt;

    @FXML
    private Button dorder;


    @FXML
     public void afficherorder  (){
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
    Commande cccc=new Commande();
    @FXML
    void initialize()
    {
        confirmationLivraisonLV.getItems().clear();
        ServiceLivraison serviceLivraison=new ServiceLivraison();
        System.out.println("ok");

        try {

            List<Commande> commandes = serviceLivraison.recuprerlivraisonEncours(); // Récupérer les données depuis le service
            ObservableList<Commande> observableStocks = FXCollections.observableArrayList(commandes);

            confirmationLivraisonLV.setItems(observableStocks);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");



            // Configurer l'adaptateur de cellule personnalisé
            confirmationLivraisonLV.setCellFactory(new Callback<ListView<Commande>, ListCell<Commande>>() {
                @Override
                public ListCell<Commande> call(ListView<Commande> listView) {
                    return new ListCell<Commande>() {
                        @Override
                        protected void updateItem(Commande commande, boolean empty) {
                            super.updateItem(commande, empty);

                            if (commande == null || empty) {
                                setText(null);
                            }else {
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                String formattedDate = commande.getDateCommande().format(formatter);
                                userService us = new userService();
                                String prenom;
                                String nom;
                                try {
                                    User x = us.recupererUtilisateur(commande.getIdUser());
                                    nom = x.getFirstName();
                                    prenom = x.getLastName();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                setText("   " +
                                        commande.getIdCommande() +
                                        "      " +nom + " " + prenom
                                        +
                                        "        " +formattedDate
                                        +
                                        "          " +commande.getPlats()
                                        +
                                        "                       " +
                                        commande.getMontantTotalCommande() +
                                        "           " +

                                        "                        " +commande.getAdresseLivraison()+
                                        "     "+" on hold"
                                );

                            }
                        }
                    };
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de récupération des données de la base de données
        }
        confirmationLivraisonLV.setOnMouseClicked(event -> {
            Commande commandeSelectionnee = confirmationLivraisonLV.getSelectionModel().getSelectedItem();
            if (commandeSelectionnee != null) {
                cccc.setIdCommande(commandeSelectionnee.getIdCommande());
                cccc.setIdUser(commandeSelectionnee.getIdUser());
                cccc.setDateCommande(commandeSelectionnee.getDateCommande());
                cccc.setAdresseLivraison(commandeSelectionnee.getAdresseLivraison());
                cccc.setMontantTotalCommande(commandeSelectionnee.getMontantTotalCommande());
                cccc.setPlats(commandeSelectionnee.getPlats());
            } });

    }

    @FXML
    public void confirmer(){
        ServiceLivraison serviceLivraison=new ServiceLivraison();
        CommandeService commandeService=new CommandeService();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(" Confirmation");
        alert.setHeaderText("Are you sure you want to Confirm this item ?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    Livraison livraison =new Livraison();
                    livraison.setIdCommande(cccc.getIdCommande());
                    if(serviceLivraison.searchLivraisonByIdCom(cccc.getIdCommande())==null){
                    serviceLivraison.planifierLivraison(livraison);
                    initialize();
                    Stage primaryStage=new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherDelivery.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    primaryStage.setTitle("");
                    primaryStage.setScene(scene);
                    primaryStage.show();}

                    else {
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                        alert2.setTitle("Error");
                        alert2.setHeaderText("Already confirmed");
                        alert2.setContentText("This delivery has already been confirmed.");
                        alert2.showAndWait(); // Show the alert and wait for it to be closed
                    }

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });}

}
