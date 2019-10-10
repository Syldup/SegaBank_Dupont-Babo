package dal;

import bo.Agence;

import java.io.IOException;
import java.sql.*;

public class AgenceDAO implements IDAO<Integer, Agence> {

    private static final AgenceDAO DAO = new AgenceDAO();
    private static final String INSERT_QUERY = "INSERT INTO agence (code, addresse) VALUES (?, '?')";
    private static final String UPDATE_QUERY = "UPDATE agence SET code=?, addresse='?' WHERE idAgence=?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM agence";
    private static final String FIND_ID_QUERY = "SELECT * FROM agence WHERE idAgence=?";
    private static final String DELETE_QUERY = "DELETE FROM agence WHERE idCompte=?";

    private AgenceDAO() {}

    public static AgenceDAO getDAO() { return DAO;}

    @Override
    public void create(Agence object) {
        try (Connection conn = PersistenceManager.getConn();
             PreparedStatement ps = conn.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, object.getCode());
            ps.setString(2, object.getAdresse());
            ps.executeUpdate();
            try(ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next())
                    object.setId(rs.getInt(1));
            }
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Agence object) {
        if (object.getId() != 0)
        try (Connection conn = PersistenceManager.getConn();
             PreparedStatement ps = conn.prepareStatement(UPDATE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, object.getCode());
            ps.setString(2, object.getAdresse());
            ps.setInt(3, object.getId());
            ps.executeUpdate();
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Agence[] findAll() {
        Agence[] objects = {};
        try (Connection conn = PersistenceManager.getConn();
             Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(FIND_ALL_QUERY)) {
            rs.last();
            objects = new Agence[rs.getRow()];
            rs.beforeFirst();
            int i = 0;
            while (rs.next())
                objects[i++] = new Agence(rs);
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
        return objects;
    }

    @Override
    public Agence findById(Integer integer) {
        Agence object = null;
        try (Connection conn = PersistenceManager.getConn();
             PreparedStatement ps = conn.prepareStatement(FIND_ID_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, integer);
            try(ResultSet rs = ps.executeQuery();) {
                if (rs.next())
                    object = new Agence(rs);
            }
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
        return object;
    }

    @Override
    public void deleteById(Integer integer) {
        try (Connection conn = PersistenceManager.getConn();
             PreparedStatement ps = conn.prepareStatement(DELETE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, integer);
            ps.executeUpdate();
            CompteDAO.getDAO().deleteById(integer);
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
