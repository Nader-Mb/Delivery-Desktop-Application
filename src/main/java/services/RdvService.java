package services;

import models.Cabinet;
import models.CrudRdv;
import models.Rdv;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import utils.MyDatabase;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RdvService implements CrudRdv<Rdv> {
    public Connection conx;
    public Statement stm;


    public RdvService() {
        conx = MyDatabase.getInstance().getConnection();

    }
    @Override
    public void ajouter(Rdv r) {
        String req =
                "INSERT INTO rdv"
                        + "(nom,prenom,numTel,email,dateRdv,id_cabinet)"
                        + "VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement ps = conx.prepareStatement(req);
            ps.setString(1, r.getNom());
            ps.setString(2, r.getPrenom());
            ps.setInt(3, r.getNumTel());
            ps.setString(4, r.getEmail());
            ps.setDate(5, new java.sql.Date(r.getDateRdv().getTime()));
            ps.setInt(6, r.getId_cabinet());
            ps.executeUpdate();
            System.out.println("Rdv Ajoutée !!");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Rdv r) {
        try {
            String req = "UPDATE rdv SET nom=?, prenom=?, numTel=?, email=?, dateRdv=?, id_cabinet=? WHERE id=?";
            PreparedStatement pst = conx.prepareStatement(req);
            pst.setInt(7, r.getId());
            pst.setString(1, r.getNom());
            pst.setString(2, r.getPrenom());
            pst.setInt(3, r.getNumTel());
            pst.setString(4, r.getEmail());
            pst.setDate(5, new java.sql.Date(r.getDateRdv().getTime()));
            pst.setInt(6, r.getId_cabinet());
            pst.executeUpdate();
            System.out.println("Rdv Modifiée !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM rdv WHERE id=?";
        try {
            PreparedStatement pst = conx.prepareStatement(req);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Rdv suprimée !");

        } catch (SQLException e) {
            System.err.println(e.getMessage());

        }
    }

    @Override
    public List<Rdv> Show() {
        List<Rdv> list = new ArrayList<>();

        try {
            String req = "SELECT * from rdv";
            Statement st = conx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                list.add(new Rdv(rs.getInt("id"), rs.getString("nom"),
                        rs.getString("prenom"), rs.getInt("numtel"),
                        rs.getString("email"),rs.getDate("dateRdv"),
                        rs.getInt("id_cabinet")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return list;
    }
}
