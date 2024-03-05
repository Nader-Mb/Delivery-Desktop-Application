package services;

import models.Reservation;
import models.Restaurant;
import models.RestaurantTable;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {

    void ajouter(T t) throws SQLException;

    void modifier(T t) throws SQLException;

    void modifier(int number, int goal, String title, String assossiation, String type, String desc, int id) throws SQLException;


    void modifier(int idCamp, float valeurDon, int idDoantor, int idDon) throws SQLException;

    void supprimer(int id) throws SQLException;

    List<T> recuperer() throws SQLException;
    void add(T entity) throws SQLException;

    /**
     * Updates an existing entity in the MyDatabase.
     *
     * @param entity the entity to be updated
     * @throws SQLException if an error occurs during the MyDatabase operation
     */
    void update(T entity) throws SQLException;

    /**
     * Deletes an entity from the MyDatabase by its unique identifier.
     *
     * @param id the unique identifier of the entity to be deleted
     * @throws SQLException if an error occurs during the MyDatabase operation
     */
    void delete(int id) throws SQLException;

    void delete(Reservation reservation) throws SQLException;

    /**
     * Retrieves all entities of type T from the MyDatabase.
     *
     * @return a list of all entities of type T
     * @throws SQLException if an error occurs during the MyDatabase operation
     */
    List<T> retrieveAll() throws SQLException;

    /**
     * Conducts research based on specified criteria.
     *
     * @param criteria the criteria for conducting the research
     * @return a list of entities matching the specified criteria
     * @throws SQLException if an error occurs during the MyDatabase operation
     */
    List<T> search(Object criteria) throws SQLException;

}
