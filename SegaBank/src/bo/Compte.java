package bo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Compte implements Comparable<Compte> {
    protected int id = 0;
    protected int identifient;
    protected double solde;
    protected boolean payant;
    protected int idAgence = 0;

    public Compte(ResultSet rs) throws SQLException {
        this.id = rs.getInt("idCompte");
        this.identifient = rs.getInt("identifiant");
        this.solde = rs.getDouble("solde");
        this.payant = rs.getBoolean("payant");
        this.idAgence = rs.getInt("idAgence");
    }

    public Compte(int identifient, double solde) {
        this.identifient = identifient;
        this.solde = solde;
        this.payant = false;
    }
    public Compte(int identifient, double solde, boolean payant) {
        this.identifient = identifient;
        this.solde = solde;
        this.payant = payant;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdentifient() {
        return identifient;
    }
    public double getSolde() {
        return solde;
    }

    public int getIdAgence() { return idAgence; }
    public void setIdAgence(int idAgence) { this.idAgence = idAgence; }

    public boolean isPayant() { return payant; }

    public void versement(double solde) {
        if (payant) {
            this.solde += solde * 0.95;
        } else {
            this.solde += solde;
        }
    }

    public void retrait(double solde) {
        if (payant) {
            this.solde -= solde * 0.95;
        } else {
            this.solde -= solde;
        }
    }
    public int compareTo(Compte o) {
        return this.id - o.id;
    }

    @Override
    public String toString() {
        return String.format("CompteEpargne{id=%d, identifient=%d, solde=%.2f, payant=%b}",
                id, identifient, solde, payant);
    }
}