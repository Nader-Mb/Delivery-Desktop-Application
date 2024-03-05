package services;

import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import models.Menu;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
//import java.util.Map;


public class ExcelExporter {

    public void generateExcel(List<Menu> menu) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le fichier Excel");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Fichiers Excel", "*.xlsx")
        );
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Menu");

            // En-têtes des colonnes
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Name");
            headerRow.createCell(1).setCellValue("Price");
            headerRow.createCell(2).setCellValue("Category");
            headerRow.createCell(3).setCellValue("Ingredients");


            for (int i = 0; i < menu.size(); i++) {
                Row dataRow = sheet.createRow(i + 1); // +1 pour éviter d'écraser l'en-tête
                Menu menu2 = menu.get(i);

                dataRow.createCell(0).setCellValue(menu2.getNameP());
                dataRow.createCell(1).setCellValue(menu2.getPriceP());
                dataRow.createCell(2).setCellValue(menu2.getCategoryP());
                dataRow.createCell(3).setCellValue(menu2.getIngredientsP());
                // Ajoutez les données des autres colonnes ici
            }

            try (FileOutputStream fileOut = new FileOutputStream(file)) {
                workbook.write(fileOut);
                workbook.close();
                System.out.println("Excel file successfully generated");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /*
import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
*/

    /*

    static String fileLoc = "src/test/resources/reportFile.xls";

    public static void WriteExcel() {
        HSSFWorkbook workbook = new HSSFWorkbook();
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        data.put("1", new Object[] { "Name", "NAME", "LASTNAME" });
        data.put("2", new Object[] { 1, "Amit", "Shukla" });
        data.put("3", new Object[] { 2, "Lokesh", "Gupta" });
        data.put("4", new Object[] { 3, "John", "Adwards" });
        data.put("5", new Object[] { 4, "Brian", "Schultz" });

        createSheet(workbook, "sheet1", data);
        createSheet(workbook, "sheet2", data);

    }

    public static void createSheet(HSSFWorkbook workbook, String sheetName, Map<String, Object[]> data) {

        HSSFSheet sheet = workbook.createSheet(sheetName);

        updateCells(sheet, data);

        try {
            FileOutputStream out = new FileOutputStream(new File(fileLoc));
            workbook.write(out);
            workbook.close();
            out.close();
            System.out.println("report.xlsx written successfully on disk.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateCells(HSSFSheet sheet, Map<String, Object[]> data) {
        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (String key : keyset) {
            Row row = sheet.createRow(rownum++);
            Object[] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                if (obj instanceof String)
                    cell.setCellValue((String) obj);
                else if (obj instanceof Integer)
                    cell.setCellValue((Integer) obj);
            }
        }
    }
*/

    //methode2

   /* public static void export(Map<String, Object> data, String fileName) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Menu");

        int rowCount = 0;

        Row headerRow = sheet.createRow(rowCount++);
        headerRow.createCell(0).setCellValue("Dish Name");
        headerRow.createCell(1).setCellValue("Ingredients");
        headerRow.createCell(2).setCellValue("Price");
        headerRow.createCell(3).setCellValue("Category");

        Menu recipe = (Menu) data.get("menu");

        Row dataRow = sheet.createRow(rowCount++);
        dataRow.createCell(0).setCellValue(recipe.getNameP());
        dataRow.createCell(1).setCellValue(recipe.getIngredientsP());
        dataRow.createCell(2).setCellValue(recipe.getPriceP());
        dataRow.createCell(3).setCellValue(recipe.getCategoryP());

        FileOutputStream fileOut = new FileOutputStream(fileName);
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
    }*/

/*
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import pidevFood.models.Recette;

import java.io.File;
import java.io.IOException;
import java.util.Map;
*/

/*
        public static void export(Map<String, Object> data, String fileName) throws IOException, WriteException {
            WritableWorkbook workbook = Workbook.createWorkbook(new File(fileName));
            WritableSheet sheet = workbook.createSheet("Menu", 0);

            Menu recipe = (Menu) data.get("menu");

            Label recipeName = new Label(0, 0, "Dish: " + menu.getNameP());
            sheet.addCell(recipeName);

            Label recipeDescription = new Label(0, 1, "Ingredients: " + menu.getIngredientsP());
            sheet.addCell(recipeDescription);

            Label recipeIngredients = new Label(0, 2, "Prix: " + menu.getPrixP());
            sheet.addCell(recipeIngredients);

            Label recipeInstructions = new Label(0, 3, "Category: " + menu.getCategoryP());
            sheet.addCell(recipeInstructions);

            workbook.write();
            workbook.close();
        }*/
}
