package bo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Agence {
    private int id = 0;
    private int code;
    private String adresse;
    private List<Compte> comptes = new ArrayList<Compte>();

    public Agence(ResultSet rs) throws SQLException {
        this.id = rs.getInt("idAgence");
        this.code = rs.getInt("code");
        this.adresse = rs.getString("adresse");
    }

    public Agence(int code, String adresse) {
        this.code = code;
        this.adresse = adresse;
    }

    public int getCode() {
        return code;
    }

    public int getId() {
        return id;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
    }

    @Override
    public String toString() {
        return String.format("Agence{id=%d, code=%d, adresse=%s}",
                id, code, adresse);
    }

    public void createCompteSimple() {

        Compte compte = new CompteSimple(1, 0);
        System.out.println(compte.toString());
        this.comptes.add(compte);
    }

    public void createCompteSimple(boolean payant) {

        Compte compte = new CompteSimple(1, 0, payant);
        System.out.println(compte.toString());
        this.comptes.add(compte);
    }

}
