package models;

import java.time.LocalDateTime;
import java.util.Objects;
import models.Restaurant;

public class Reservation {
    private int reservationId;
    private int userId;
    private LocalDateTime dateTime;
    private int tableId;
    private int numberOfPersons;

    public Reservation() {
    }

    public Reservation(int userId, LocalDateTime dateTime, int tableId, int numberOfPersons) {
        this.userId = userId;
        this.dateTime = dateTime;
        this.tableId = tableId;
        this.numberOfPersons = numberOfPersons;
    }

    public Reservation(int reservationId, int userId, LocalDateTime dateTime, int tableId, int numberOfPersons) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.dateTime = dateTime;
        this.tableId = tableId;
        this.numberOfPersons = numberOfPersons;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getNumberOfPersons() {
        return numberOfPersons;
    }

    public void setNumberOfPersons(int numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", userId=" + userId +
                ", dateTime=" + dateTime +
                ", tableId=" + tableId +
                ", numberOfPersons=" + numberOfPersons +
                '}';
    }

    private Restaurant restaurant;

    // Constructor, getters, setters, and other methods

    public Restaurant getRestaurant() {
        return restaurant;
    }
}

