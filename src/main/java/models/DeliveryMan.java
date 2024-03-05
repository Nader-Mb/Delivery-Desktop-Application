package models;
import models.*;
public class DeliveryMan extends User{

        private String vehicle;
        private String availability;
        private String sector;
        private int idUser;


        public DeliveryMan(String firstName, String lastName, String email, String address, double rating, String role, int number,String password, String vehicle, String availability, String sector) {
            super(firstName, lastName, email, address, rating, role, number,password);
            this.vehicle = vehicle;
            this.availability = availability;
            this.sector = sector;
        }

        // Getters and setters for additional attributes
        public String getVehicle() {
            return vehicle;
        }

        public void setVehicle(String vehicle) {
            this.vehicle = vehicle;
        }

        public String getAvailability() {
            return availability;
        }

        public void setAvailability(String availability) {
            this.availability = availability;
        }

        public String getSector() {
            return sector;
        }

        public void setSector(String sector) {
            this.sector = sector;
        }

        // Override toString() method for better representation
        @Override
        public String toString() {
            return "DeliveryMan{" +
                    "id=" + getId() +
                    ", firstName='" + getFirstName() + '\'' +
                    ", lastName='" + getLastName() + '\'' +
                    ", email='" + getEmail() + '\'' +
                    ", address='" + getAddress() + '\'' +
                    ", rating=" + getRating() +
                    ", role='" + getRole() + '\'' +
                    ", number='" + getNumber() + '\'' +
                    ", vehicle='" + vehicle + '\'' +
                    ", availability='" + availability + '\'' +
                    ", sector='" + sector + '\'' +
                    '}';
        }
    }


