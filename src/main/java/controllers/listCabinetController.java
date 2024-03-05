package controllers;

import models.Cabinet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import services.CabinetService;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class listCabinetController implements Initializable {


    @FXML
    private VBox vBox;

    @FXML
    private AnchorPane listCabinetPane;

    @FXML
    private TextField searchbar_id;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int column=0;
        int row=0;
        try {
            CabinetService cs = new CabinetService();
            List<Cabinet> cabs = cs.Show();

                for(int i=0;i<cabs.size();i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/itemCabinet.fxml"));

                    try {
                        AnchorPane anchorPane = fxmlLoader.load();
                        HBox hBox = (HBox) anchorPane.getChildren().get(0);
                        itemCabinetController itemController = fxmlLoader.getController();
                        itemController.setData(cabs.get(i));
                        vBox.getChildren().add(hBox);

                    } catch (IOException ex) {
                        Logger.getLogger(itemCabinetController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void SearchForCabinet(ActionEvent event) {
        CabinetService cs = new CabinetService();
        try {
            vBox.getChildren().clear(); // Clear previous content

            String searchText = searchbar_id.getText(); // Assuming id_search is the TextField where the user enters the search text

            ObservableList<Cabinet> observableList = FXCollections.observableList(cs.Show());

            // Filter the list based on the search text
            List<Cabinet> filteredList = observableList.stream()
                    .filter(e -> e.getNom().toLowerCase().contains(searchText.toLowerCase()))
                    .collect(Collectors.toList());

            // Load and display filtered data
            for (Cabinet c : filteredList) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/itemCabinet.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                HBox cardBox = (HBox) anchorPane.getChildren().get(0);
                itemCabinetController cardController = fxmlLoader.getController();
                cardController.setData(c);
                vBox.getChildren().add(cardBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openStat(ActionEvent event) throws IOException{
        Parent fxml= FXMLLoader.load(getClass().getResource("/statistiques.fxml"));
        listCabinetPane.getChildren().removeAll();
        listCabinetPane.getChildren().setAll(fxml);
    }
}
