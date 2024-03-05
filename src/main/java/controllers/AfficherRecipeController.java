package controllers;

import java.io.File;

import com.itextpdf.text.DocumentException;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import services.PdfExporter;
import services.CrudRecette;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
        import javafx.beans.value.ChangeListener;
        import javafx.beans.value.ObservableValue;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;

import models.Recette;

import java.util.*;

import javafx.scene.image.Image;

public class AfficherRecipeController implements Initializable {
    @FXML
    private Button btAjRec;
    @FXML
    private Button btDelRec;
    @FXML
    private Button btMoveToCampaign;
    @FXML
    private Button btModRec;
    @FXML
    private Button btPrintRec;
    @FXML
    private Button btsend;

    @FXML
    private Button btViewChart;
    @FXML
    private ComboBox<String> CombCategory;
    @FXML
    private ComboBox<String> CombDifficulty;
    @FXML
    private ComboBox<String> CombSortBy;
    @FXML
    private Button btSort;
    @FXML
    private Button btInvSort;
    @FXML
    private Button btSearch;

    @FXML
    private TextField tfSearchName;
    @FXML
    private Label lbRecDet;
    @FXML
    private Label lbIngDet;
    @FXML
    private ImageView imgVRec;
    @FXML
    private ListView<Recette> lvRec;
    @FXML
    private Button btMoveToUsers;

    @FXML
    private Button btMoveToResto;

    Recette RecetteSelectionne;
    private final CrudRecette crudRecette = new CrudRecette();

    private final String[] Category = new String[]{
            "Appetizers",
            "Main Courses",
            "Side Dishes",
            "Desserts",
            "Beverages"};
    private final String[] Difficulty = new String[]{
            "Easy","Average","Difficult"};
    private final String[] SortBy = new String[]{
            "Category","Difficulty","Name"};

    @FXML
    void ChartView_Action(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ChartPageRecette.fxml"));
            btViewChart.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void goToCampaign(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Campaign.fxml"));
            lbIngDet.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void goToResto(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/showrestaurant.fxml"));
            lbIngDet.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void goTousers(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ListUser.fxml"));
            lbIngDet.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialize();

        // Create a tooltip
        Tooltip tooltip = new Tooltip("Search by Category, Difficulty or a Keyword Match");
        // Attach the tooltip to the button
        Tooltip.install(btSearch, tooltip);

        Tooltip tooltip2 = new Tooltip("Sort by Category, Difficulty or Name");
        Tooltip.install(btSort, tooltip2);

        this.CombCategory.setItems(FXCollections.observableArrayList(Category).sorted());
        this.CombDifficulty.setItems(FXCollections.observableArrayList(Difficulty).sorted());
        this.CombSortBy.setItems(FXCollections.observableArrayList(SortBy).sorted());

    }


        public void afficheDetailsRec(){

        lvRec.setOnMouseClicked(event -> {
        Recette recetteSelectionne2 = lvRec.getSelectionModel().getSelectedItem();
            File imageFile = new File(recetteSelectionne2.getImageRec());


            if (!imageFile.exists()) {
                System.out.println("File not found: " + imageFile.getAbsolutePath());
                return;
            }

            try (FileInputStream inputStream = new FileInputStream(imageFile)) {
                Image img = new Image(inputStream);
                imgVRec.setImage(img);
            } catch (IOException e) {
                System.err.println("Error loading image: " + e.getMessage());
            }

            /*      Image img = null;
                try {
                    img = new Image(imageFile.toURI().toURL().toString());
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);   }*/


                lbRecDet.setText("Name: " + recetteSelectionne2.getNomRec() + "\n" +
                        "Category: " + recetteSelectionne2.getCategoryR() + "\n" +
                        "Difficulty: " + recetteSelectionne2.getDifficulty() + "\n" +
                        "Serves: " + recetteSelectionne2.getServes() + "\n" +
                        "Preparation Time: " + recetteSelectionne2.getPrep() + "\n" +
                        "Description: " + recetteSelectionne2.getDescription() + "\n" +
                        "Date: " + recetteSelectionne2.getDate() + "\n" +
                        "Rating: " + recetteSelectionne2.getRating() + "\n");

        });}


    public void initialize() {

        ObservableList<Recette> recipeList = FXCollections.observableArrayList();
        lvRec.setItems(recipeList);
        Alert alert;
        try {
            List<Recette> recetteDetails = crudRecette.afficher();
            recipeList.addAll(recetteDetails);
            lvRec.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number oldIndex, Number newIndex) {
                    RecetteSelectionne = lvRec.getSelectionModel().getSelectedItem();}}
                );
            }
        catch (SQLException excp) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(excp.getMessage());
                    alert.showAndWait();
                }

        }

    @FXML
    void searchRecAction(ActionEvent event) {
        if(CombCategory.getValue()!=null || CombDifficulty.getValue()!=null || tfSearchName.getText()!=""){
            try {
                searchRecipes(CombCategory.getValue(), CombDifficulty.getValue(), tfSearchName.getText());
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
                throw new RuntimeException(e);
            }
        }

        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Choose criteria");
            alert.showAndWait();
        }
    }

    public void searchRecipes(String category,String difficulty, String name ) throws SQLException {
        ObservableList<Recette> filteredRecipes = FXCollections.observableArrayList();
        List<Recette> recipes = crudRecette.afficher();

        // Filtrage des recettes selon la catégorie et/ou la difficulté sélectionnées
        for (Recette recipe : recipes) {
            boolean categoryMatch = category==null || recipe.getCategoryR().equals(category);
            boolean difficultyMatch = difficulty==null || recipe.getDifficulty().equals(difficulty);
            boolean nameSearch = name==null || recipe.getNomRec().toLowerCase().contains(name.toLowerCase());
            if (categoryMatch && difficultyMatch && nameSearch) {
                filteredRecipes.add(recipe);
            }
        }
        lvRec.setItems(filteredRecipes);
    }

    @FXML
    void Sort_Action(ActionEvent event) {
        if(CombSortBy.getValue()!=null){
            try {
                sortRecipes(CombSortBy.getValue());
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
                throw new RuntimeException(e);
            }}
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Choose criteria");
            alert.showAndWait();
        }
    }

    public void sortRecipes(String selectedOption) throws SQLException {
        ObservableList<Recette> sortedRecipes = FXCollections.observableArrayList();
        List<Recette> recipes = crudRecette.afficher();

        switch (selectedOption) {
            case "Category":
                recipes.sort(Recette.compareByCategory());
                break;
            case "Difficulty":
                recipes.sort(Recette.compareByDifficulty());
                break;
            case "Name":
                recipes.sort((r1, r2) -> r1.getNomRec().compareToIgnoreCase(r2.getNomRec()));
                break;
        }

        sortedRecipes.addAll(recipes);
        lvRec.setItems(sortedRecipes);
    }

    @FXML
    void InverseSort_Action(ActionEvent event) {
        ObservableList<Recette> viewedRecipes = lvRec.getItems();
        if (viewedRecipes.size() > 0) {
            List<Recette> sortedRecipes = new ArrayList<>(viewedRecipes);
            Collections.reverse(sortedRecipes);
            lvRec.setItems(FXCollections.observableArrayList(sortedRecipes));
        }  else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No recipes to sort");
            alert.showAndWait();
        }
        }

        public void onModifierRec(ActionEvent actionEvent){
            RecetteSelectionne = lvRec.getSelectionModel().getSelectedItem();
            if(RecetteSelectionne!=null) {
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierRecipe2.fxml"));
                    Parent root = loader.load();

                    ModifierRecipeController ModifierRecipeController = loader.getController();

                    RecetteSelectionne = lvRec.getSelectionModel().getSelectedItem();

                    ModifierRecipeController.populateFields(RecetteSelectionne);
                    btModRec.getScene().setRoot(root);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                } catch (Exception e) {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Choose recipe");
                alert.showAndWait();
            }
        }
    public void OnDeleteRec (ActionEvent actionEvent) {
        RecetteSelectionne = lvRec.getSelectionModel().getSelectedItem();
        if (RecetteSelectionne != null) {
            try {
            crudRecette.supprimer(RecetteSelectionne.getIdRec());
            initialize();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select recipe");
            alert.showAndWait();
        }}
    public void MoveToAjoutRec(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterRecipe.fxml"));
            lbRecDet.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }}

    public void printRecipe(Recette recipe) throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("recipe", recipe);


        String fileName = "Recipe_" + recipe.getNomRec() + ".pdf";
        String filePath = System.getProperty("user.dir") + File.separator + "Recipe_" + recipe.getNomRec() + ".pdf";
        PdfExporter.export(data, fileName);

        try {
            PdfExporter.export(data, filePath);
            // Show a message to the user that the PDF file has been generated
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PDF Generated");
            alert.setHeaderText("PDF Generated");
            alert.setContentText("The PDF file has been generated at " + filePath);
            alert.showAndWait();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            // Show an error message to the user
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("An error occurred while generating the PDF file.");
            alert.showAndWait();
        }

    }


        @FXML
    void PrintRecAction(ActionEvent event){
        try {
            RecetteSelectionne = lvRec.getSelectionModel().getSelectedItem();
            if (RecetteSelectionne != null) {
                    //RecetteSelectionne.getIdRec();
                    printRecipe(RecetteSelectionne);}

        } catch (SQLException e) {
                throw new RuntimeException(e);
    } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void openSendMail(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Mail.fxml"));
            Parent root = loader.load();

            MailController MailController = loader.getController();

            RecetteSelectionne = lvRec.getSelectionModel().getSelectedItem();

            MailController.generateMail(RecetteSelectionne);
            btsend.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


        /*

        @FXML
        void 82391a(ActionEvent event) {

        }

        @FXML
        void 034a03(ActionEvent event) {

        }

        @FXML
        void d1dad1(ActionEvent event) {

        }

        @FXML
        void 867878(ActionEvent event) {

        }

        @FXML
        void 5e0707(ActionEvent event) {

        }

    */
}
