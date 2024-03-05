
package models;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import javafx.scene.control.Button;


public class User {
    // Attributes
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private double rating;
    private String role;
    private int number;
    private String password;







    // Constructor
    public User( String firstName, String lastName, String email, String address, double rating, String role, int number,String password) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.rating = rating;
        this.role = role;
        this.number = number;
        this.password=password;

    }

    public User(int id, String firstName, String lastName, String email, String address, String role, int number, float rating) {
       this.id=id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.rating = rating;
        this.role = role;
        this.number = number;


    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(int id, String firstName, String lastName, String email, String address, double rating, String role, int number,String password) {

        this.id=id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.rating = rating;
        this.role = role;
        this.number = number;
        this.password=password;

    }



    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }



    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", rating=" + rating +
                ", role='" + role + '\'' +
                ", number='" + number + '\'' +
                '}';
    }


    public User() {
    }
}
