package controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import models.Livraison;
import services.ServiceLivraison;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ModifierDeliveryController implements Initializable {

    @FXML
    private Button addOrderLogOutButt;

    @FXML
    private Button addOrderDeliveryButt;

    @FXML
    private Button addOrderCompaignButt1;

    @FXML
    private Label confirmationAjoutCommande;

    @FXML
    private Button updateDeliverypdateButt;

    @FXML
    private Button addOrderHomeButt;

    @FXML
    private Button updateDeliveryCancelButt;

    @FXML
    private Button addOrderOrdersButt;

    @FXML
    private Button addOrderRecipesButt;

    @FXML
    private ComboBox<String> updateStatusCombobox;

    Livraison livraison=new Livraison();

    List<String> listArrayPlat = Arrays.asList(
            "On Hold",
            "Delivered",
            "Canceled",
            "In Progress"
    );



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

    @FXML
    public void update(){
        ServiceLivraison serviceLivraison=new ServiceLivraison();
        livraison.setStatut(updateStatusCombobox.getValue());

        try {
            serviceLivraison.update(livraison, livraison.getIdCommande());
        }
        catch (SQLException s) {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Error");
            alert2.setContentText(s.getMessage());
            alert2.showAndWait();

        }
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/afficherDelivery.fxml"));
            updateStatusCombobox.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }



    @FXML
    public void init(Livraison l){
        updateStatusCombobox.setValue(l.getStatut());
        livraison = l;
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateStatusCombobox.setItems(FXCollections.observableList(listArrayPlat));
    }
}
