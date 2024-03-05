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
import models.User;
import services.CommandeService;
import services.ServiceLivraison;
import services.userService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class afficherOrderController {

    @FXML
    private ListView<Commande> afficherCommandeLV;

    @FXML
    private Button addOrderLogOutButt;

    @FXML
    private Button addOrderDeliveryButt;

    @FXML
    private Button addOrderCompaignButt1;

    @FXML
    private Label confirmationAjoutCommande;

    @FXML
    private Button addOrderHomeButt;

    @FXML
    private Button addOrderOrdersButt;

    @FXML
    private Button addOrderRecipesButt;
    Commande cccc=new Commande();

    @FXML
    private ComboBox<String> idSort;

    private final String[] attributsSortC = {"Prix", "Plats", "Addresse"};
    private final String[] attributsearchWith = {"adresseLivraison", "montantTotalCommande", "plats"};

    @FXML
    private TextField idSearch;

    @FXML
    private ComboBox<String> idSearchWith;

    @FXML
    void initialize()
    {

        idSort.setItems(FXCollections.observableArrayList(attributsSortC));
        idSearchWith.setItems(FXCollections.observableArrayList(attributsearchWith));


        afficherCommandeLV.getItems().clear();

        try {
            CommandeService cs = new CommandeService();
            List<Commande> commandes = cs.recuperer(); // Récupérer les données depuis le service
            ObservableList<Commande> observableStocks = FXCollections.observableArrayList(commandes);

            afficherCommandeLV.setItems(observableStocks);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");



            // Configurer l'adaptateur de cellule personnalisé
            afficherCommandeLV.setCellFactory(new Callback<ListView<Commande>, ListCell<Commande>>() {
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

                                        "                        " +commande.getAdresseLivraison()
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
        afficherCommandeLV.setOnMouseClicked(event -> {
            Commande commandeSelectionnee = afficherCommandeLV.getSelectionModel().getSelectedItem();
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
    private void searchCommande() {
        String searchAttribute = idSearchWith.getValue();
        String searchKeyword = idSearch.getText();

        if (searchKeyword.isEmpty()) {
            //initialize(); // Reset the table to its initial state
            initialize();
        }

        try {
            CommandeService commandeService=new CommandeService();
            List<Commande> searchResults = commandeService.searchCommandeByNomStartingWithLetter(searchAttribute, searchKeyword);

            // Set a custom cell factory for the ListView
            afficherCommandeLV.setCellFactory(param -> new ListCell<Commande>() {
                protected void updateItem(Commande commande, boolean empty) {
                    super.updateItem(commande, empty);

                    if (commande == null || empty) {
                        setText(null);
                    } else {
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
                                "      " + nom + " " + prenom
                                +
                                "        " + formattedDate
                                +
                                "          " + commande.getPlats()
                                +
                                "                       " +
                                commande.getMontantTotalCommande() +
                                "           " +

                                "                        " + commande.getAdresseLivraison()
                        );

                    }
                }
            });

            // Set the items in the ListView
            afficherCommandeLV.getItems().setAll(searchResults);

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    private void sortCommand() {
        String selectedSortOption = idSort.getValue();

        if (selectedSortOption != null) {
            ObservableList<Commande> items = afficherCommandeLV.getItems();

            switch (selectedSortOption) {
                case "Addresse":
                    Collections.sort(items, Comparator.comparing(Commande::getAdresseLivraison));
                    break;
                case "Plats":
                    Collections.sort(items, Comparator.comparing(Commande::getPlats));
                    break;
                case "Prix":
                    Collections.sort(items, Comparator.comparing(Commande::getMontantTotalCommande));

                    break;
                default:
                    System.out.println("Default case - no sorting");
                    break;
            }

            // Update the ListView with the sorted data
            afficherCommandeLV.setItems(items);
        } else {
            System.out.println("Default case - initialize or display original data");
            initialize();
        }
    }
    @FXML
    public void delet(){
            CommandeService commandeService=new CommandeService();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText("Are you sure you want to delete this item ?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
        try {
            ServiceLivraison serviceLivraison=new ServiceLivraison();
            serviceLivraison.supprimerLivraisonByCommande(cccc.getIdCommande());
            commandeService.supprimer(cccc.getIdCommande());
            initialize();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
        });}
    @FXML
    public void modifier(javafx.event.ActionEvent actionEvent){
        Commande selectedStock = afficherCommandeLV.getSelectionModel().getSelectedItem();
        if (selectedStock != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifierOrder.fxml"));
                Parent root = loader.load();
                modifierOrderController controller = loader.getController();
                controller.initData(selectedStock);
                afficherCommandeLV.getScene().setRoot(root);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
    @FXML
    public  void afficherAddOrder(){
        Stage primaryStage=new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajoutOrder.fxml"));
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
    public void delvrygo(){
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



}
