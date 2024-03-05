package controllers;

import models.Cabinet;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import services.CabinetService;
import javafx.fxml.FXML;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StatistiquesController implements Initializable {

    @FXML
    private AnchorPane statPane;

    @FXML
    private LineChart<String, Integer> lineChartCabinet;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        statistique();
    }

    public void statistique() {
        CabinetService cs = new CabinetService();

        List<Cabinet> cabs = cs.Show();



        // Créer les axes pour le graphique
        final NumberAxis yAxis = new NumberAxis();
        final CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Nom Cabinet");
        yAxis.setLabel("Nb Rendez-vous");

        // Créer la série de données à afficher
        XYChart.Series series = new XYChart.Series();
        series.setName("Statistiques des cabinets selon leurs nombre des rendez-vous");
        for (Cabinet c : cabs) {
            series.getData().add(new XYChart.Data<>(c.getNom(),cs.getCountById(c.getId())));
        }

        // Créer le graphique et ajouter la série de données
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Statistiques des Cabinets");
        lineChart.getData().add(series);

        // Afficher le graphique dans votre scène
        lineChartCabinet.setCreateSymbols(false);
        lineChartCabinet.getData().add(series);

    }
}
