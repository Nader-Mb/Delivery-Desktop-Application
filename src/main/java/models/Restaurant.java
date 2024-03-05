package models;

public class Restaurant {
    private int restaurantId;
    private String name;
    private String address;
    private String description;
    private String imagePath;
    private int userId;


    public Restaurant() {
    }

    public Restaurant(int restaurantId, String name, String address, String description, String imagePath, int userId) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.address = address;
        this.description = description;
        this.imagePath = imagePath;
        this.userId = userId;
    }

    public Restaurant(String name, String address, String description, String imagePath, int userId) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.imagePath = imagePath;
        this.userId = userId;
    }

    public Restaurant(String name, String address, String description, String imagePath) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.imagePath = imagePath;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "restaurantId=" + restaurantId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", userId=" + userId +
                '}';
    }
}