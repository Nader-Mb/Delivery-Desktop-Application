package controllers;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import models.Reservation;
import models.Restaurant;
import services.ReservationService;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

public class BeachReservationController {

    @FXML
    private Button cancel;

    @FXML
    private Button print;

    @FXML
    private TextArea ticket;


    private Reservation reservation;

    @FXML

    public void initialize() {
        // Set font style to bold
        Font boldFont = Font.font("System", FontWeight.BOLD, 29);
        ticket.setFont(boldFont);
        ticket.setStyle("-fx-alignment: center;");

        // Set wrap text property to true
        ticket.setWrapText(true);
    }


    @FXML
    void cancel(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/showreservation.fxml"));
            Parent root = loader.load();
            ShowReservationController controller = loader.getController();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            controller.setStage(stage);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            showErrorAlert("Error redirecting to Show Reservation", e);
        }
    }
    @FXML
    private void showErrorAlert(String message, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.setContentText("Error: " + e.getMessage());
        alert.showAndWait();
    }

    @FXML
    public void initData(Reservation reservation) {
        this.reservation = reservation;
        if (reservation != null) {
            // Populate TextArea with reservation details when initializing
            String ticketContent = generateTicketContent(reservation);
            ticket.setText(ticketContent);
        } else {
            showErrorAlert("Error: Reservation data is null", new NullPointerException("Reservation data is null"));
        }
    }
    String ticketcontenttopdf;
    @FXML
    private String generateTicketContent(Reservation reservation) {
        StringBuilder ticketContent = new StringBuilder();
        ticketContent.append("-----------------------------\n");
        ticketContent.append("        Reservation Ticket        \n");
        ticketContent.append("-----------------------------\n\n");

        Restaurant restaurant = reservation.getRestaurant();
        ticketContent.append("Number:").append(reservation.getReservationId()).append("\n");

        ticketContent.append("Restaurant: ").append(getRestaurantName(reservation.getTableId())).append("\n");
        ticketContent.append("Date: ").append(reservation.getDateTime().toLocalDate()).append("\n");
        ticketContent.append("Time: ").append(reservation.getDateTime().toLocalTime()).append("\n");
        ticketContent.append("Number of Persons: ").append(reservation.getNumberOfPersons()).append("\n\n");
        ticketContent.append("-----------------------------\n");
        ticketContent.append("Thank you !\n");

        ticketcontenttopdf = ticketContent.toString();
        return ticketContent.toString();
    }


    @FXML
    private void printReservationAsPDF() {
        try {
            // Create a temporary file path for the PDF
            String filePath = "reservation_" + UUID.randomUUID().toString() + ".pdf";
            File file = new File(filePath);

            // Create a PdfDocument instance
            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(file));

            // Create a Document instance
            Document document = new Document(pdfDoc);

            // Create a color for the text
            Color textColor = new DeviceRgb(42, 170, 138); // Blue color, you can change as needed

            // Set font size and alignment
            float fontSize = 20; // Change the font size as needed
            document.setFontSize(fontSize);
            document.setTextAlignment(TextAlignment.CENTER);

            // Add content to the document with filled text color
            Paragraph paragraph = new Paragraph(ticket.getText())
                    .setFontColor(textColor)
                    .setFontSize(fontSize)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(paragraph);

            // Close the document
            document.close();

            // Open the generated PDF
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            showErrorAlert("Error generating or opening PDF", e);
        }
    }

    private String getRestaurantName(int tableId) {
        try {
            ReservationService rs = new ReservationService();
            return rs.getRestaurantNameByResId(tableId);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Unknown"; // Handle error gracefully
        }
    }

}
