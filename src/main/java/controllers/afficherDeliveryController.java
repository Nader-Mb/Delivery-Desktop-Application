package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Commande;
import models.Livraison;
import models.User;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import services.CommandeService;
import services.ServiceLivraison;
import services.userService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;
public class afficherDeliveryController {
    @FXML
    private Text itemselcted;
    @FXML
    private Button addOrderLogOutButt;

    @FXML
    private Button addOrderDeliveryButt;

    @FXML
    private Button addOrderCompaignButt1;

    @FXML
    private Button showDeliveryUpdateButt;

    @FXML
    private Label confirmationAjoutCommande;

    @FXML
    private Button showDeliveryDeleteButt;

    @FXML
    private Button addOrderHomeButt;

    @FXML
    private Button addOrderOrdersButt;
    @FXML
    private ListView<Commande> showDeliveryLV;
    @FXML
    private ListView<Livraison> showLV;


    @FXML
    void generateExcel(ActionEvent event) {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = (Sheet) workbook.createSheet("Données");

            // En-têtes du tableau


            String[] headers = {"Plats", "ID commande", "Adresse","MontantTotalCommande"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell =  headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Données à insérer dans le fichier Excel
            int rowNum = 1;
            for (Commande livraison : showDeliveryLV.getItems()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(livraison.getPlats());
                row.createCell(1).setCellValue(livraison.getIdCommande());
                row.createCell(2).setCellValue(livraison.getAdresseLivraison());
                row.createCell(3).setCellValue(livraison.getMontantTotalCommande());
            }

            // Enregistrer le fichier Excel
            String excelFilePath = "livraisons.xlsx";
            try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
                workbook.write(outputStream);
            }

            itemselcted.setText("Excel généré avec succès !");

            // Ouvrir automatiquement le fichier Excel
            File file = new File(excelFilePath);
            java.awt.Desktop.getDesktop().open(file);

        } catch (IOException e) {
            e.printStackTrace();
            itemselcted.setText("Erreur lors de la génération de l'Excel.");
        }
    }




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
    Livraison l=new Livraison();
    @FXML
    void initialize()
    {
        showDeliveryLV.getItems().clear();
        ServiceLivraison serviceLivraison=new ServiceLivraison();
        System.out.println("ok");

        try {

            List<Commande> commandes = serviceLivraison.récupérerToutesLesLivraisons(); // Récupérer les données depuis le service
            ObservableList<Commande> observableStocks = FXCollections.observableArrayList(commandes);

            showDeliveryLV.setItems(observableStocks);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");



            // Configurer l'adaptateur de cellule personnalisé
            showDeliveryLV.setCellFactory(new Callback<ListView<Commande>, ListCell<Commande>>() {
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
                                    String status = ""; // Declare status variable outside try block

                                    try {
                                        ServiceLivraison serviceLivraison=new ServiceLivraison();

                                        // Fetch status for each command individually
                                        status = serviceLivraison.searchLivraisonByIdCom(commande.getIdCommande()).getStatut();

                                        // Rest of your code here...

                                    } catch (SQLException s) {
                                        s.printStackTrace();
                                        // Handle the exception appropriately
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
                                        "     "+ status
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
        showDeliveryLV.setOnMouseClicked(event -> {
            Commande commandeSelectionnee = showDeliveryLV.getSelectionModel().getSelectedItem();
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
    public void delete(){
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
    public void update() {
        ServiceLivraison serviceLivraison = new ServiceLivraison();

        if (cccc != null) {
            try {
                Livraison l = serviceLivraison.searchLivraisonByIdCom(cccc.getIdCommande());


                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifierDelivery.fxml"));
                    Parent root = loader.load();
                    ModifierDeliveryController modifierDeliveryController = loader.getController();
                    modifierDeliveryController.init(l);

                showDeliveryLV.getScene().setRoot(root);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Error");
            alert2.setContentText("no command selected ");
            alert2.showAndWait();
        }



    }
}
