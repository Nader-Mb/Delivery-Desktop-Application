package models;

import java.util.Comparator;
import java.util.List;

public class Recette {

    private int idRec;
    private String nomRec;
    private String CategoryR;
    private String difficulty;

    private int serves;
    private String prep;

    private String description;
    private String date;
    //dans la base hatit type date
    private int rating;
    private int idUser;
    private String imageRec;

    public Recette() {
    }

    public Recette(int idRec, String nomRec, String CategoryR, String difficulty, int serves, String prep, String description, String date, int rating, int idUser, String imageRec) {
        this.idRec = idRec;
        this.nomRec = nomRec;
        this.CategoryR=CategoryR;
        this.difficulty=difficulty;
        this.serves = serves;
        this.prep = prep;
        this.description = description;
        this.date = date;
        this.rating = rating;
        this.idUser = idUser;
        this.imageRec=imageRec;
    }

    public Recette(String nomRec, String CategoryR, String difficulty, int serves, String prep, String description, String date, int rating, int idUser, String imageRec) {
        this.nomRec = nomRec;
        this.CategoryR=CategoryR;
        this.difficulty=difficulty;
        this.serves = serves;
        this.prep = prep;
        this.description = description;
        this.date = date;
        this.rating = rating;
        this.idUser = idUser;
        this.imageRec=imageRec;

    }


    public int getIdRec() { return idRec;}

    public void setIdRec(int idRec) {
        this.idRec = idRec;
    }

    public String getNomRec() {
        return nomRec;
    }

    public void setNomRec(String nomRec) {
        this.nomRec = nomRec;
    }
    public String getCategoryR(){return CategoryR;}
    public void setCategoryR(String CategoryR){this.CategoryR=CategoryR;}


    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getServes() {
        return serves;
    }

    public void setServes(int serves) {
        this.serves = serves;
    }

    public String getPrep() {
        return prep;
    }

    public void setPrep(String prep) {
        this.prep = prep;
    }
    public String getDescription() {
        return description;
    }

    public void setdescription(String description) {
        this.description = description;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
    public String getImageRec() {
        return imageRec;
    }

    public void setImageRec(String imageRec) {
        this.imageRec = imageRec;
    }


    public static Comparator<Recette> compareByCategory() {
        final List<String> categoryOrder = List.of("Appetizers", "Main Courses", "Side Dishes", "Desserts", "Beverages");
        return (r1, r2) -> {
            int index1 = categoryOrder.indexOf(r1.getCategoryR());
            int index2 = categoryOrder.indexOf(r2.getCategoryR());
            if (index1 == -1) index1 = Integer.MAX_VALUE; // Place unknown categories at the end
            if (index2 == -1) index2 = Integer.MAX_VALUE; // Place unknown categories at the end
            return Integer.compare(index1, index2);
        };
    }

    public static Comparator<Recette> compareByDifficulty() {
        final List<String> difficultyOrder = List.of("Easy", "Average", "Difficult");
        return (r1, r2) -> {
            int index1 = difficultyOrder.indexOf(r1.getDifficulty());
            int index2 = difficultyOrder.indexOf(r2.getDifficulty());
            if (index1 == -1) index1 = Integer.MAX_VALUE; // Place unknown difficulty at the end
            if (index2 == -1) index2 = Integer.MAX_VALUE; // Place unknown difficulty at the end
            return Integer.compare(index1, index2);
        };
    }


    @Override
    public String toString() {
        return //"Recipe{" +
                //"idRec=" + idRec +
                //"nameRec='" +
                        nomRec
                        /*
                        + '\'' +
                ", CategoryR='" + CategoryR + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", number of serves=" + serves +'\''+
                ", duration of preparation='" + prep + '\'' +
                //", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", rating='" + rating + '\'' +
                //", idUser=" + idUser +'\''+
                //", imageRec=" + imageRec + '}'
                        */
                ;
    }
}
