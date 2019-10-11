package bo;

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

    public List<Compte> getComptes() { return comptes; }
    public void setComptes(List<Compte> comptes) { this.comptes = comptes; }

    @Override
    public String toString() {
        return String.format("Agence{id=%d, code=%s, adresse=%s}",
                id, code, adresse);
    }

    public void addCompte(Compte compte) {
        this.comptes.add(compte);
    }

    public void addComptesSimple(List<CompteSimple> comptes) {
        for (Compte c : comptes)
            this.comptes.add(c);
    }
    public void addComptesEpargne(List<CompteEpargne> comptes) {
        for (Compte c : comptes)
            this.comptes.add(c);
    }

    public void clearComptes() {
        this.comptes.clear();
    }

    public void sortComptes() {
        Collections.sort(this.comptes);
    }

    public void printComptes() {
        for (int i=0; i<comptes.size(); i++)
            System.out.printf("%d - %s%n", i+1, comptes.get(i));
    }
}
