package dal;

import bo.CompteEpargne;

import java.io.IOException;
import java.sql.*;

public class CompteEpargneDAO implements IDAO<Integer, CompteEpargne> {

    private static final CompteEpargneDAO compteEpargneDAO = new CompteEpargneDAO();
    private static final String INSERT_QUERY = "INSERT INTO compteepargne (tauxInteret, idCompte) VALUES (?, ?)";
    private static final String UPDATE_QUERY = "UPDATE compteepargne SET tauxInteret=? WHERE idCompte=?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM compteepargne e LEFT JOIN compte AS c ON e.idCompte=c.idCompte";
    private static final String FIND_ID_QUERY = "SELECT * FROM compteepargne e LEFT JOIN compte AS c ON e.idCompte=c.idCompte WHERE e.idCompte=?";
    private static final String DELETE_QUERY = "DELETE FROM compteepargne WHERE idCompte=?";

    private CompteEpargneDAO() {}

    public static CompteEpargneDAO getDAO() { return compteEpargneDAO;}

    @Override
    public void create(CompteEpargne object) {
        try (Connection conn = PersistenceManager.getConn();
             PreparedStatement ps = conn.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            CompteDAO.getDAO().create(object);
            ps.setDouble(1, object.getTauxInteret());
            ps.setInt(2, object.getId());
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
    public void update(CompteEpargne object) {
        if (object.getId() != 0)
        try (Connection conn = PersistenceManager.getConn();
             PreparedStatement ps = conn.prepareStatement(UPDATE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            CompteDAO.getDAO().update(object);
            ps.setDouble(1, object.getTauxInteret());
            ps.setInt(2, object.getId());
            ps.executeUpdate();
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public CompteEpargne[] findAll() {
        CompteEpargne[] objects = {};
        try (Connection conn = PersistenceManager.getConn();
             Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(FIND_ALL_QUERY)) {
            rs.last();
            objects = new CompteEpargne[rs.getRow()];
            rs.beforeFirst();
            int i = 0;
            while (rs.next())
                objects[i++] = new CompteEpargne(rs);
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
        return objects;
    }

    @Override
    public CompteEpargne findById(Integer integer) {
        CompteEpargne object = null;
        try (Connection conn = PersistenceManager.getConn();
             PreparedStatement ps = conn.prepareStatement(FIND_ID_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, integer);
            ps.executeUpdate();
            try(ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next())
                    object = new CompteEpargne(rs);
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
