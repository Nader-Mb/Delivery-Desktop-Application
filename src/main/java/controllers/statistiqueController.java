package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import models.Commande;
import services.CommandeService;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class statistiqueController implements Initializable {


    @FXML
    private LineChart<String, Integer> lineid;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            statistique();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void statistique() throws SQLException {
        CommandeService cs = new CommandeService();
        List<Commande> forums = null;
        forums = cs.recuperer();


        // Créer les axes pour le graphique
        final NumberAxis yAxis = new NumberAxis();
        final CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Titre");
        yAxis.setLabel("Nbr Commande");

        // Créer la série de données à afficher
        XYChart.Series series = new XYChart.Series();
        series.setName("Statistiques des forums selon leurs nombre des commentaires");
        for (Commande f : forums) {
            series.getData().add(new XYChart.Data<>(f.getPlats(), cs.getById(f.getIdUser())));
        }

        // Créer le graphique et ajouter la série de données
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Statistiques des forums");
        lineChart.getData().add(series);

        // Afficher le graphique dans votre scène
        lineid.setCreateSymbols(false);
        lineid.getData().add(series);
    }
}
