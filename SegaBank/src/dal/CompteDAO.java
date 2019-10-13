package dal;

import bo.Compte;
import bo.CompteEpargne;
import bo.CompteSimple;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompteDAO implements IDAO<Integer, Compte> {

    private static final CompteDAO DAO = new CompteDAO();
    private static final String INSERT_QUERY = "INSERT INTO compte (identifiant, solde, payant, idAgence) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE compte SET identifiant=?, solde=?, payant=? WHERE idCompte = ?";
    private static final String DELETE_QUERY = "DELETE FROM compte WHERE idCompte=?";
    private static final String FIND_ALL_QUERY = "SELECT c.idCompte, identifiant, solde, payant, idAgence, tauxInteret, decouvert FROM compte c " +
            "LEFT JOIN compteepargne AS ce ON c.idCompte=ce.idCompte LEFT JOIN comptesimple AS cs ON c.idCompte=cs.idCompte";

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
            ps.setInt(4, object.getId());
            ps.executeUpdate();
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Compte> findAll() {
        List<Compte> objects = new ArrayList<Compte>();
        try (Connection conn = PersistenceManager.getConn();
             Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(FIND_ALL_QUERY)) {
            while (rs.next()) {
                rs.getInt("tauxInteret");
                if (rs.wasNull())
                    objects.add(new CompteEpargne(rs));
                else objects.add(new CompteSimple(rs));
            }
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
        return objects;}

    @Override
    public Compte findById(Integer integer) {
        Compte object = null;
        try (Connection conn = PersistenceManager.getConn();
             Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(String.format("%s WHERE c.idCompte=%d", FIND_ALL_QUERY, integer))) {
            if (rs.next()) {
                rs.getInt("tauxInteret");
                if (rs.wasNull())
                    object = new CompteEpargne(rs);
                else object = new CompteSimple(rs);
            }
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
        return object; }

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

    public List<Compte> findByIdAgence(Integer integer) {
        List<Compte> objects = new ArrayList<Compte>();
        try (Connection conn = PersistenceManager.getConn();
             Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(String.format("%s WHERE c.idAgence=%d", FIND_ALL_QUERY, integer))) {
            while (rs.next()) {
                rs.getInt("tauxInteret");
                if (rs.wasNull())
                    objects.add(new CompteEpargne(rs));
                else objects.add(new CompteSimple(rs));
            }
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
        return objects;
    }
}
