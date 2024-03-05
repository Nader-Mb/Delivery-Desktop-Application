package services;


import javafx.scene.control.ListView;
import models.Campaign;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;

import java.io.File;

import javafx.stage.FileChooser;


public class ExcelExporterCampaign {

    public void generateExcel(ListView<Campaign> listView) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Excel");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel Files", "*.xlsx")
        );
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Sheet1");

            // Create header row
            // Row headerRow = sheet.createRow(0);
            //String[] headers = {
            //  "Username", "Email", "Password", "Role",
            // "Image", "Age", "Sexe"
            //};
            // En-têtes des colonnes
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Title");
            headerRow.createCell(1).setCellValue(" Association Name");
            headerRow.createCell(2).setCellValue("Type");
            headerRow.createCell(3).setCellValue("Goal");
            headerRow.createCell(4).setCellValue("Total Doantion");
            headerRow.createCell(5).setCellValue("Number");
            headerRow.createCell(6).setCellValue("Description");
            // Ajoutez les en-têtes des autres colonnes ici

            for (int i = 0; i < listView.getItems().size(); i++) {
                Row dataRow = sheet.createRow(i + 1); // +1 pour éviter d'écraser l'en-tête
                Campaign campaign = listView.getItems().get(i);

                dataRow.createCell(0).setCellValue(campaign.getTitle());
                dataRow.createCell(1).setCellValue(campaign.getAssociationName());
                dataRow.createCell(2).setCellValue( campaign.getCampaignType());
                dataRow.createCell(3).setCellValue( campaign.getGoal());
                dataRow.createCell(4).setCellValue(campaign.getCurrent());
                dataRow.createCell(5).setCellValue(Integer.toString(campaign.getNumber()));
                dataRow.createCell(6).setCellValue(campaign.getDescription());

            }

            try (FileOutputStream fileOut = new FileOutputStream(file)) {
                workbook.write(fileOut);
                workbook.close();
                System.out.println("Excel generated successfully.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}