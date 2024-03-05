package services;


import models.User;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import java.io.File;

import javafx.scene.control.TableView;
import javafx.stage.FileChooser;


    public class ExcelExporterUser {

            public void generateExcel(TableView<User> tableView) {
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
                    headerRow.createCell(0).setCellValue("First Name");
                    headerRow.createCell(1).setCellValue("Last Name");
                    headerRow.createCell(2).setCellValue("Email");
                    headerRow.createCell(3).setCellValue("Address");
                    headerRow.createCell(4).setCellValue("Role");
                    headerRow.createCell(5).setCellValue("Rating");
                    headerRow.createCell(6).setCellValue("Number");
                    // Ajoutez les en-têtes des autres colonnes ici

                    for (int i = 0; i < tableView.getItems().size(); i++) {
                        Row dataRow = sheet.createRow(i + 1); // +1 pour éviter d'écraser l'en-tête
                        User user = tableView.getItems().get(i);

                        dataRow.createCell(0).setCellValue(user.getFirstName());
                        dataRow.createCell(1).setCellValue(user.getLastName());
                        dataRow.createCell(2).setCellValue( user.getEmail());
                        dataRow.createCell(3).setCellValue( user.getAddress());
                        dataRow.createCell(4).setCellValue(user.getRole());
                        dataRow.createCell(5).setCellValue(user.getRating());
                        dataRow.createCell(6).setCellValue(Integer.toString(user.getNumber()));

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


