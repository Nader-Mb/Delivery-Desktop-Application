package models;

import java.util.Comparator;
import java.util.List;

public class Menu {

    private int idP;
    private String nameP;
    private String ingredientsP;

    private float priceP;
    private String categoryP;

    private int restaurantId;
    private String imageP;

    public Menu() {
    }

    public Menu(int idP, String nameP, float priceP, String categoryP, String ingredientsP, int restaurantId, String imageP) {
        this.idP = idP;
        this.nameP = nameP;
        this.ingredientsP=ingredientsP;
        this.priceP = priceP;
        this.categoryP = categoryP;
        this.restaurantId = restaurantId;
        this.imageP=imageP;
    }

    public Menu(String nameP, float priceP, String categoryP,String ingredientsP, int restaurantId, String imageP) {
        this.nameP = nameP;
        this.ingredientsP=ingredientsP;
        this.priceP = priceP;
        this.categoryP = categoryP;
        this.restaurantId = restaurantId;
        this.imageP=imageP;
    }


    public int getIdP() {
        return idP;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }

    public String getNameP() {
        return nameP;
    }

    public void setNameP(String nameP) {
        this.nameP = nameP;
    }

    public String getIngredientsP() {
        return ingredientsP;
    }

    public void setIngredientsP(String ingredientsP) {
        this.ingredientsP = ingredientsP;
    }

    public float getPriceP() {
        return priceP;
    }

    public void setPriceP(float priceP) {
        this.priceP = priceP;
    }

    public String getCategoryP() {
        return categoryP;
    }

    public void setCategoryP(String categoryP) {
        this.categoryP = categoryP;
    }
    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }
    public String getImageP() {
        return imageP;
    }

    public void setImageP(String imageP) {
        this.imageP = imageP;
    }

    public static Comparator<Menu> compareByCategory() {
        final List<String> categoryOrder = List.of("Appetizers", "Main Courses", "Side Dishes", "Desserts", "Beverages");
        return (r1, r2) -> {
            int index1 = categoryOrder.indexOf(r1.getCategoryP());
            int index2 = categoryOrder.indexOf(r2.getCategoryP());
            if (index1 == -1) index1 = Integer.MAX_VALUE; // Place unknown categories at the end
            if (index2 == -1) index2 = Integer.MAX_VALUE; // Place unknown categories at the end
            return Integer.compare(index1, index2);
        };
    }

    @Override
        public String toString() {

            return String.format("%-15s  %-15.3f  %-15s  %-15s", nameP, priceP,categoryP,ingredientsP);

        /*
        return "Menu{" +
                "idP=" + idP +
                ", nameP='" + nameP + '\'' +
                ", priceP='" + priceP + '\'' +
                ", ingredientsP=" + ingredientsP +'\''+
                ", categoryP='" + categoryP + '\'' +
                ", restaurantId='" + restaurantId + '\'' +
                ", imageP='" + imageP +
                '}';

        */
    }
}