package services;

import models.Menu;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CrudMenu implements Crud<Menu>{

    private Connection connection;

    public CrudMenu(){
        connection= MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Menu menu) throws SQLException {
        String sql = "INSERT INTO `menu`(`nameP`,`priceP`,`categoryP`,`ingredientsP`,`restaurantId`,`imageP`) VALUES ('"+menu.getNameP()+"','"+menu.getPriceP()+"','"+menu.getCategoryP()+"','"+menu.getIngredientsP()+"','"+menu.getRestaurantId()+"','"+menu.getImageP()+"')";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    @Override
    public void modifier(int idP,Menu menu) throws SQLException {
        String sql = "UPDATE `menu` SET nameP = ?, priceP = ?, categoryP = ?, ingredientsP = ?, restaurantId=?, imageP =? where idP=?";
        PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
        preparedStatement.setString(1, menu.getNameP());
        preparedStatement.setFloat(2, menu.getPriceP());
        preparedStatement.setString(3, menu.getCategoryP());
        preparedStatement.setString(4, menu.getIngredientsP());
        preparedStatement.setInt(5, menu.getRestaurantId());
        preparedStatement.setString(6, menu.getImageP());
        preparedStatement.setInt(7, idP);
        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(int idP) throws SQLException {
        String sql = "DELETE FROM menu WHERE idP = ?";
        PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
        preparedStatement.setInt(1, idP);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Menu> afficher() throws SQLException {
        List<Menu> menu= new ArrayList<>();
        String sql = "select * from menu";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            Menu p = new Menu();
            p.setIdP(rs.getInt("idP"));
            p.setNameP(rs.getString("nameP"));
            p.setPriceP(rs.getFloat("priceP"));
            p.setCategoryP(rs.getString("categoryP"));
            p.setIngredientsP(rs.getString("ingredientsP"));
            p.setImageP(rs.getString("imageP"));
            menu.add(p);
        }
        return menu;
    }
}