package dal;

import bo.Agence;
import bo.CompteSimple;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompteSimpleDAO implements IDAO<Integer, CompteSimple> {

    private static final CompteSimpleDAO DAO = new CompteSimpleDAO();
    private static final String INSERT_QUERY = "INSERT INTO comptesimple (decouvert, idCompte) VALUES (?, ?)";
    private static final String UPDATE_QUERY = "UPDATE comptesimple SET decouvert=? WHERE idCompte=?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM comptesimple cs LEFT JOIN compte AS c ON cs.idCompte=c.idCompte";
    private static final String FIND_ID_QUERY = "SELECT * FROM comptesimple cs LEFT JOIN compte AS c ON cs.idCompte=c.idCompte WHERE cs.idCompte=?";
    private static final String DELETE_QUERY = "DELETE FROM comptesimple WHERE idCompte=?";
    private static final String FIND_BY_AGENCE_QUERY = "SELECT * FROM comptesimple cs LEFT JOIN compte AS c ON cs.idCompte=c.idCompte WHERE c.idAgence=?";


    private CompteSimpleDAO() {}

    public static CompteSimpleDAO getDAO() { return DAO;}

    public void create(CompteSimple object, int idAgence) {
        object.setIdAgence(idAgence);
        create(object);
    }

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
    public List<CompteSimple> findAll() {
        List<CompteSimple> objects = new ArrayList<CompteSimple>();
        try (Connection conn = PersistenceManager.getConn();
             Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(FIND_ALL_QUERY)) {
            while (rs.next())
                objects.add(new CompteSimple(rs));
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

    public List<CompteSimple> findByIdAgence(Integer integer) {
        List<CompteSimple> objects = new ArrayList<CompteSimple>();
        try (Connection conn = PersistenceManager.getConn();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_AGENCE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, integer);
            ps.executeQuery();
            try(ResultSet rs = ps.executeQuery();) {
                while (rs.next())
                    objects.add(new CompteSimple(rs));
            }
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
        return objects;
    }
}
