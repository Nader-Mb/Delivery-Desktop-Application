package models;

import java.sql.SQLException;
import java.util.List;

public interface CrudRdv <Rd> {
    public void ajouter(Rd r);
    public void modifier(Rd r);
    public void supprimer(int id) throws SQLException;
    public List<Rdv> Show();
}
