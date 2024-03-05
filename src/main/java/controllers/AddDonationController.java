package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Donation;
import response.PayementResponse;
import services.CampaignService;
import services.DonationService;
import services.KonnectPaymentService;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class AddDonationController {

    @FXML
    private TextField donationValue;

    String paymentUrl;


    int idCampaign;

    private final DonationService donationService = new DonationService();

    private final KonnectPaymentService paymentService = new KonnectPaymentService();


    @FXML
    void donate(ActionEvent event) {
        try {
            donationService.addDonation(Float.parseFloat(donationValue.getText()), idCampaign);

            donationService.ajouter(new Donation(idCampaign, 1, Float.parseFloat(donationValue.getText()), Date.valueOf(LocalDate.now())));

            PayementResponse payementResponse = paymentService.initPayment(Float.parseFloat(donationValue.getText()));

            paymentUrl = payementResponse.payUrl();
            Desktop.getDesktop().browse(new URL(paymentUrl).toURI());


        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error while modifying a campaign");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void switchToDisplayAllDonations() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Donate.fxml"));
            Parent newPageRoot = loader.load();


            Scene pageScene = new Scene(newPageRoot);
            Stage stage = (Stage) donationValue.getScene().getWindow();
            stage.setScene(pageScene);

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }
    }
    public void switchToDisplayAllCampaigns() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Campaign.fxml"));
            Parent newPageRoot = loader.load();


            Scene pageScene = new Scene(newPageRoot);
            Stage stage = (Stage) donationValue.getScene().getWindow();
            stage.setScene(pageScene);

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }
    }


    private final CampaignService campaignService = new CampaignService();
    private final AddCampaignController addCampaignController = new AddCampaignController();

    public void initializeValues(int number, int id) {
        donationValue.setText(Float.toString(number));
        idCampaign = id;
    }


}
