package models;

import java.util.List;
import java.sql.SQLException;


public interface CrudCabinet <Cab> {
    public void ajouter(Cab c);
    public void modifier(Cab c);
    public void supprimer(int id) throws SQLException;
    public List<Cabinet> Show();

    public Cabinet getById(int id) throws SQLException;
}
