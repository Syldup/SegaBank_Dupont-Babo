package bo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
}
