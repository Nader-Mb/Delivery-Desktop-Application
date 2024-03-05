package services;

import java.sql.SQLException;
import java.util.List;

public interface Crud<T> {


    void ajouter(T t) throws SQLException;
    void modifier(int id,T t) throws SQLException;
    void supprimer(int id) throws SQLException;
    List<T> afficher() throws SQLException;
}
