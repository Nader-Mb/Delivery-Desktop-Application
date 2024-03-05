package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import utils.MyDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ChartPageController implements Initializable {

    @FXML
    private BarChart<String, Number> barchart;
    @FXML
    private ListView<String> recipeListView;

    @FXML
    private PieChart piechartRec;
    @FXML
    private Button btCancel;

    @FXML
    void cancel(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherRecette4.fxml"));
            btCancel.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        barchart.setTitle("Latest Added Recipes");
        try {
            Connection conn = MyDatabase.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT CategoryR, COUNT(*) FROM recette WHERE date <= DATE_SUB(CURDATE(), INTERVAL 1 WEEK) GROUP BY CategoryR");
            ObservableList<XYChart.Series<String, Number>> data = FXCollections.observableArrayList();
            while (rs.next()) {
                XYChart.Series<String, Number> series = new XYChart.Series<>();
                series.setName(rs.getString(1));
                series.getData().add(new XYChart.Data<>(rs.getString(1), rs.getInt(2)));
                data.add(series);
            }
            barchart.setData(data);

            String sqlQuery = "SELECT * FROM recette ORDER BY rating DESC, date DESC LIMIT 5";
            ResultSet rs2 = stmt.executeQuery(sqlQuery);

            while (rs2.next()) {
                String recipeName = rs2.getString("nomRec");
                int rating = rs2.getInt("rating");
                String dateCreated = rs2.getString("date");
                recipeListView.getItems().add(recipeName + " (Rating: " + rating +")");
            }

            String query = "SELECT difficulty, COUNT(*) FROM recette GROUP BY difficulty";
            ResultSet rs3 = stmt.executeQuery(query);

            while (rs3.next()) {
                piechartRec.getData().add(new PieChart.Data(rs3.getString(1), rs3.getInt(2)));
            }
            piechartRec.setTitle("Difficulty Distribution of Recipes");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}