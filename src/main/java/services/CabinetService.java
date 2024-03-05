package services;


import models.CrudCabinet;
import models.Cabinet;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CabinetService implements CrudCabinet<Cabinet> {
    public Connection conx;


    public CabinetService() {
        conx = MyDatabase.getInstance().getConnection();
    }
    @Override
    public void ajouter(Cabinet c) {
        String req =
                "INSERT INTO cabinet"
                        + "(nom,adresse,codePostal,adresseMail,id_medecin)"
                        + "VALUES(?,?,?,?,?)";
        try {
            PreparedStatement ps = conx.prepareStatement(req);
            ps.setString(1, c.getNom());
            ps.setString(2, c.getAdresse());
            ps.setInt(3, c.getCodePostal());
            ps.setString(4, c.getAdresseMail());
            ps.setInt(5, c.getId_medecin());
            ps.executeUpdate();
            System.out.println("Cabinet Ajoutée !!");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Cabinet c) {
        try {
            String req = "UPDATE cabinet SET nom=?, adresse=?, codePostal=?, adresseMail=?, id_medecin=? WHERE id=?";
            PreparedStatement pst = conx.prepareStatement(req);
            pst.setInt(6, c.getId());
            pst.setString(1, c.getNom());
            pst.setString(2, c.getAdresse());
            pst.setInt(3, c.getCodePostal());
            pst.setString(4, c.getAdresseMail());
            pst.setInt(5, c.getId_medecin());
            pst.executeUpdate();
            System.out.println("Cabinet Modifiée !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM cabinet WHERE id=?";
        try {
            PreparedStatement pst = conx.prepareStatement(req);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Cabinet suprimée !");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public List<Cabinet> Show() {
        List<Cabinet> list = new ArrayList<>();

        try {
            String req = "SELECT * from cabinet";
            Statement st = conx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                list.add(new Cabinet(rs.getInt("id"), rs.getString("nom"),
                        rs.getString("adresse"), rs.getInt("codePostal"),
                        rs.getString("adresseMail"),rs.getInt("id_medecin")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return list;
    }

    @Override
    public Cabinet getById(int id) throws SQLException {
        Cabinet cab = null;
        String sql = "SELECT * FROM cabinet WHERE id = ?";
        try {
            // Create a PreparedStatement with the SQL query
            PreparedStatement pst = conx.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            // Process the results
            while (rs.next()) {
                cab=new Cabinet(rs.getInt("id"), rs.getString("nom"),
                        rs.getString("adresse"), rs.getInt("codePostal"),
                        rs.getString("adresseMail"),rs.getInt("id_medecin"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cab;
    }

    public int getCountById(int id){
        int nb=0;
        String req = "SELECT COUNT(*) FROM rdv WHERE id_cabinet=?";
        try {
            PreparedStatement pst = conx.prepareStatement(req);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                nb=rs.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return nb;
    }




}
