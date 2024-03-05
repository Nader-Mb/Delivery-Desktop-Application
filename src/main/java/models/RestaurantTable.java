package models;

import java.util.Objects;

public class RestaurantTable {
    private int tableId;
    private boolean isOccupied;

    private int restaurantId;

    public RestaurantTable() {
    }

    public RestaurantTable(int tableId, boolean isOccupied, String description, int restaurantId) {
        this.tableId = tableId;
        this.isOccupied = isOccupied;
        this.restaurantId = restaurantId;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }


    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public String toString() {
        return "RestaurantTable{" +
                "tableId=" + tableId +
                ", isOccupied=" + isOccupied + '\'' +
                ", restaurantId=" + restaurantId +
                '}';
    }
}

