package services;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import models.Recette;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class PdfExporter {
    public static void export(Map<String, Object> data, String fileName) throws DocumentException, IOException {
        Document document = new Document();
        FileOutputStream fos = new FileOutputStream(fileName);
        PdfWriter.getInstance(document, fos);
        document.open();

        Recette recipe = (Recette) data.get("recipe");

        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
        Paragraph recipeName = new Paragraph("Recipe: " + recipe.getNomRec(), headFont);
        recipeName.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(recipeName);

        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
        Paragraph recipeDescription = new Paragraph("Description: " + recipe.getDescription(), normalFont);
        document.add(recipeDescription);

        Paragraph recipeIngredients = new Paragraph("Difficulty: " + recipe.getDifficulty(), normalFont);
        document.add(recipeIngredients);

        Paragraph recipeInstructions = new Paragraph("Category: " + recipe.getCategoryR(), normalFont);
        document.add(recipeInstructions);

        document.close();
        fos.close();

    }
}
