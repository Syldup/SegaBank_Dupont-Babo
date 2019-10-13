package bo;

import dal.CompteDAO;
import dal.CompteEpargneDAO;
import dal.CompteSimpleDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Agence {
    private int id = 0;
    private String code;
    private String adresse;
    private List<Compte> comptes = new ArrayList<Compte>();

    public Agence(ResultSet rs) throws SQLException {
        this.id = rs.getInt("idAgence");
        this.code = rs.getString("code");
        this.adresse = rs.getString("adresse");
    }

    public Agence(String code, String adresse) {
        this.code = code;
        this.adresse = adresse;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getAdresse() {
        return adresse;
    }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }


    @Override
    public String toString() {
        return String.format("Agence{id=%d, code=%s, adresse=%s}",
                id, code, adresse);
    }

    public Compte getCompte(int nb) { return this.comptes.get(nb); }
    public List<Compte> getComptes() { return comptes; }
    public void addCompte(Compte compte) { this.comptes.add(compte); }
    public void addComptes(List<Compte> comptes) { this.comptes.addAll(comptes); }

    public void removeCompte(Compte compte) {
        if (compte instanceof CompteSimple)
            CompteSimpleDAO.getDAO().deleteById(compte.getId());
        else if (compte instanceof CompteEpargne)
            CompteEpargneDAO.getDAO().deleteById(compte.getId());
        CompteDAO.getDAO().deleteById(compte.getId());
        this.comptes.remove(compte);
    }

    public void clearComptes() { this.comptes.clear(); }
    public int getNbCompte() { return this.comptes.size(); }

    public void printComptes() {
        Collections.sort(this.comptes);
        for (int i=0; i<comptes.size(); i++)
            System.out.printf("%d - %s%n", i+1, comptes.get(i));
    }
}
