package services;

import models.Ingredients;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CrudIngredients implements Crud<Ingredients>{

    private Connection connection;

    public CrudIngredients(){
        connection= MyDatabase.getInstance().getConnection();
    }
    @Override
    public void ajouter(Ingredients ingredients) throws SQLException {
        String sql = "INSERT INTO `ingredients`(`nameIng`, `amount`,`idRec`) VALUES ('"+ingredients.getNameIng()+"','"+ingredients.getAmount()+"','"+ingredients.getIdRec()+"')";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    @Override
    public void modifier(int idIng,Ingredients ingredients) throws SQLException {
        String sql = "update ingredients set nameIng = ?, amount = ?, idRec = ? where idIng = ?";
        PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
        preparedStatement.setString(1, ingredients.getNameIng());
        preparedStatement.setString(2, ingredients.getAmount());
        preparedStatement.setInt(3, ingredients.getIdRec());
        preparedStatement.setInt(4, idIng);
        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(int idIng) throws SQLException {
        String sql = "delete from ingredients where idIng = ?";
        PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
        preparedStatement.setInt(1, idIng);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Ingredients> afficher() throws SQLException {
        List<Ingredients> ingredients= new ArrayList<>();
        String sql = "select * from ingredients";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            Ingredients p = new Ingredients();
            p.setIdIng(rs.getInt("idIng"));
            p.setNameIng(rs.getString("nameIng"));
            p.setIdRec(rs.getInt("idRec"));
            p.setAmount(rs.getString("amount"));
            ingredients.add(p);
        }
        return ingredients;
    }
}

