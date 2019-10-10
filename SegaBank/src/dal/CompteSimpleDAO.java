package dal;

import bo.CompteSimple;

import java.io.IOException;
import java.sql.*;

public class CompteSimpleDAO implements IDAO<Integer, CompteSimple> {

    private static final CompteSimpleDAO DAO = new CompteSimpleDAO();
    private static final String INSERT_QUERY = "INSERT INTO comptesimple (decouvert, idCompte) VALUES (?, ?)";
    private static final String UPDATE_QUERY = "UPDATE comptesimple SET decouvert=? WHERE idCompte=?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM comptesimple cs LEFT JOIN compte AS c ON cs.idCompte=c.idCompte";
    private static final String FIND_ID_QUERY = "SELECT * FROM comptesimple cs LEFT JOIN compte AS c ON cs.idCompte=c.idCompte WHERE cs.idCompte=?";
    private static final String DELETE_QUERY = "DELETE FROM comptesimple WHERE idCompte=?";

    private CompteSimpleDAO() {}

    public static CompteSimpleDAO getDAO() { return DAO;}

    @Override
    public void create(CompteSimple object) {
        try (Connection conn = PersistenceManager.getConn();
             PreparedStatement ps = conn.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            CompteDAO.getDAO().create(object);
            ps.setDouble(1, object.getDecouvert());
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
    public void update(CompteSimple object) {
        if (object.getId() != 0)
        try (Connection conn = PersistenceManager.getConn();
             PreparedStatement ps = conn.prepareStatement(UPDATE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            CompteDAO.getDAO().update(object);
            ps.setDouble(1, object.getDecouvert());
            ps.setInt(2, object.getId());
            ps.executeUpdate();
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public CompteSimple[] findAll() {
        CompteSimple[] objects = {};
        try (Connection conn = PersistenceManager.getConn();
             Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(FIND_ALL_QUERY)) {
            rs.last();
            objects = new CompteSimple[rs.getRow()];
            rs.beforeFirst();
            int i = 0;
            while (rs.next())
                objects[i++] = new CompteSimple(rs);
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
        return objects;
    }

    @Override
    public CompteSimple findById(Integer integer) {
        CompteSimple object = null;
        try (Connection conn = PersistenceManager.getConn();
             PreparedStatement ps = conn.prepareStatement(FIND_ID_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, integer);
            try(ResultSet rs = ps.executeQuery();) {
                if (rs.next())
                    object = new CompteSimple(rs);
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
