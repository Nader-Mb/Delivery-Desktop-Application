package services;

import models.Recette;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CrudRecette implements Crud<Recette>{
    private Connection connection;

    public CrudRecette(){
        connection= MyDatabase.getInstance().getConnection();
    }
    @Override
    public void ajouter(Recette recette) throws SQLException {
        String sql = "INSERT INTO `recette`(`nomRec`,`CategoryR`, `difficulty`,`serves`,`prep`,`description`,`date`,`rating`,`idUser`,`imageRec`) VALUES ('"+recette.getNomRec()+"','"+recette.getCategoryR()+"','"+recette.getDifficulty()+"','"+recette.getServes()+"','"+recette.getPrep()+"','"+recette.getDescription()+"','"+recette.getDate()+"','"+recette.getRating()+"','"+recette.getIdUser()+"','"+recette.getImageRec()+"')";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }
    @Override
    public void modifier(int idRec,Recette recette) throws SQLException {
        String sql = "UPDATE recette SET `nomRec` = ?,`CategoryR`=? ,`difficulty` = ?, `serves` = ?, `prep` = ?, `description` = ?, `date` = ?, `rating` = ?, `idUser` = ?, `imageRec`=? where `idRec`=?";
        PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
        preparedStatement.setString(1, recette.getNomRec());
        preparedStatement.setString(2, recette.getCategoryR());
        preparedStatement.setString(3, recette.getDifficulty());
        preparedStatement.setInt(4, recette.getServes());
        preparedStatement.setString(5, recette.getPrep());
        preparedStatement.setString(6, recette.getDescription());
        preparedStatement.setString(7, recette.getDate());
        preparedStatement.setInt(8, recette.getRating());
        preparedStatement.setInt(9, recette.getIdUser());
        preparedStatement.setString(10, recette.getImageRec());
        preparedStatement.setInt(11, idRec);

        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(int idRec) throws SQLException {
        String sql = "DELETE FROM recette WHERE idRec = ?";
        PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
        preparedStatement.setInt(1, idRec);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Recette> afficher() throws SQLException {
        List<Recette> recette= new ArrayList<>();
        String sql = "select * from recette";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            Recette p = new Recette();
            p.setNomRec(rs.getString("nomRec"));
            p.setCategoryR(rs.getString("categoryR"));
            p.setDifficulty(rs.getString("difficulty"));
            p.setServes(rs.getInt("serves"));
            p.setPrep(rs.getString("prep"));
            p.setdescription(rs.getString("description"));
            p.setDate(rs.getString("date"));
            p.setRating(rs.getInt("rating"));
            p.setIdUser(rs.getInt("idUser"));
            p.setImageRec(rs.getString("imageRec"));
            p.setIdRec(rs.getInt("idRec"));

            recette.add(p);
        }
        return recette;
    }
}