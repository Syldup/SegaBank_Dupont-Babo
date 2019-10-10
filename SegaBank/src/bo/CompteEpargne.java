package bo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompteEpargne extends Compte {
    private double tauxInteret;

    public CompteEpargne(ResultSet rs) throws SQLException {
        super(rs.getInt("identifiant"), rs.getDouble("solde"), rs.getBoolean("payant"));
        this.id = rs.getInt("idCompteEpargne");
        this.tauxInteret = rs.getDouble("tauxInteret");
    }

    public CompteEpargne(int identifient, double solde) {
        super(identifient, solde);
        this.tauxInteret = 0;
    }

    public CompteEpargne(int identifient, double solde, double tauxInteret) {
        super(identifient, solde);
        this.tauxInteret = tauxInteret;
    }

    public CompteEpargne(int identifient, double solde, boolean payant) {
        super(identifient, solde, payant);
        this.tauxInteret = 0;
    }

    public CompteEpargne(int identifient, double solde, double tauxInteret, boolean payant) {
        super(identifient, solde, payant);
        this.tauxInteret = tauxInteret;
    }

    public double getTauxInteret() { return tauxInteret; }

    @Override
    public String toString() {
        return String.format("CompteEpargne{identifient=%d, solde=%.2f, payant=%b, tauxInteret=%.2f}",
                identifient, solde, payant, tauxInteret);
    }

    public void calculInteret() {
        solde *= 1+tauxInteret;
    }
}
/*
package bo;

public class CompteEpargne extends CompteSimple{

    private double tauxInteret;
    private double interet;

    public CompteEpargne(int id, double solde, double tauxInteret) {
        super(id, solde);
        this.tauxInteret = tauxInteret;
    }

    public void calculInteret() {
        this.interet = this.solde * this.tauxInteret;
    }

    public double getInteret() {
        return interet;
    }

    public void setInteret(double interet) {
        this.interet = interet;
    }
}

 */
