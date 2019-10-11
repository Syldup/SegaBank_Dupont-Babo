package dal;

import bo.Agence;
import bo.CompteEpargne;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompteEpargneDAO implements IDAO<Integer, CompteEpargne> {

    private static final CompteEpargneDAO DAO = new CompteEpargneDAO();
    private static final String INSERT_QUERY = "INSERT INTO compteepargne (tauxInteret, idCompte) VALUES (?, ?)";
    private static final String UPDATE_QUERY = "UPDATE compteepargne SET tauxInteret=? WHERE idCompte=?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM compteepargne ce LEFT JOIN compte AS c ON ce.idCompte=c.idCompte";
    private static final String FIND_ID_QUERY = "SELECT * FROM compteepargne ce LEFT JOIN compte AS c ON ce.idCompte=c.idCompte WHERE ce.idCompte=?";
    private static final String DELETE_QUERY = "DELETE FROM compteepargne WHERE idCompte=?";
    private static final String FIND_BY_AGENCE_QUERY = "SELECT * FROM compteepargne ce LEFT JOIN compte AS c ON ce.idCompte=c.idCompte WHERE c.idAgence=?";


    private CompteEpargneDAO() {}

    public static CompteEpargneDAO getDAO() { return DAO;}

    public void create(CompteEpargne object, int idAgence) {
        object.setIdAgence(idAgence);
        create(object);
    }

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
    public List<CompteEpargne> findAll() {
        List<CompteEpargne> objects = new ArrayList<CompteEpargne>();
        try (Connection conn = PersistenceManager.getConn();
             Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(FIND_ALL_QUERY)) {
            while (rs.next())
                objects.add(new CompteEpargne(rs));
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
            try(ResultSet rs = ps.executeQuery();) {
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

    public List<CompteEpargne> findByIdAgence(Integer interger) {
        List<CompteEpargne> objects = new ArrayList<CompteEpargne>();
        try (Connection conn = PersistenceManager.getConn();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_AGENCE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, interger);
            ps.executeQuery();
            try(ResultSet rs = ps.executeQuery();) {
                while (rs.next())
                    objects.add(new CompteEpargne(rs));
            }
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
        return objects;
    }
}
