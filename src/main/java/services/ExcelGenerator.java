package services;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelGenerator {

    public static void main(String[] args) {
        try {
            // Créer un classeur Excel
            Workbook workbook = new XSSFWorkbook();

            // Créer une feuille dans le classeur
            Sheet sheet = workbook.createSheet("Données");

            // Données à insérer dans le fichier Excel
            Object[][] data = {
                    {"Nom", "Âge", "Ville"},
                    {"John", 30, "New York"},
                    {"Alice", 25, "Paris"},
                    {"Bob", 35, "London"}
            };

            // Écrire les données dans la feuille Excel
            int rowNum = 0;
            for (Object[] rowData : data) {
                Row row = sheet.createRow(rowNum++);
                int colNum = 0;
                for (Object field : rowData) {
                    Cell cell = row.createCell(colNum++);
                    if (field instanceof String) {
                        cell.setCellValue((String) field);
                    } else if (field instanceof Integer) {
                        cell.setCellValue((Integer) field);
                    }
                }
            }

            // Enregistrer le fichier Excel
            String excelFilePath = "data.xlsx";
            try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
                workbook.write(outputStream);
            }

            System.out.println("Le fichier Excel a été créé avec succès.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

