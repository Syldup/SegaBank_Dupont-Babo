package dal;

import bo.Compte;

import java.io.IOException;
import java.sql.*;

public class CompteDAO implements IDAO<Integer, Compte> {

    private static final CompteDAO DAO = new CompteDAO();
    private static final String INSERT_QUERY = "INSERT INTO compte (identifiant, solde, payant, idAgence) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE compte SET identifient=?, solde=?, payant=?, idAgence=? WHERE idCompte = ?";
    private static final String DELETE_QUERY = "DELETE FROM compte WHERE idCompte=?";

    private CompteDAO() {}

    public static CompteDAO getDAO() { return DAO;}

    @Override
    public void create(Compte object) {
        try (Connection conn = PersistenceManager.getConn();
             PreparedStatement ps = conn.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, object.getIdentifient());
            ps.setDouble(2, object.getSolde());
            ps.setBoolean(3, object.isPayant());
            ps.setInt(4, object.getIdAgence());
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
    public void update(Compte object) {
        if (object.getId() != 0)
        try (Connection conn = PersistenceManager.getConn();
             PreparedStatement ps = conn.prepareStatement(UPDATE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, object.getIdentifient());
            ps.setDouble(2, object.getSolde());
            ps.setBoolean(3, object.isPayant());
            ps.setInt(4, object.getIdAgence());
            ps.setInt(5, object.getId());
            ps.executeUpdate();
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Compte[] findAll() { return null; }

    @Override
    public Compte findById(Integer integer) { return null; }

    @Override
    public void deleteById(Integer integer) {
        try (Connection conn = PersistenceManager.getConn();
             PreparedStatement ps = conn.prepareStatement(DELETE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, integer);
            ps.executeUpdate();
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
