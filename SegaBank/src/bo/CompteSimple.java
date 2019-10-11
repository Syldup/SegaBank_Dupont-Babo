package bo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class CompteSimple extends Compte {
    private double decouvert;

    public CompteSimple(ResultSet rs) throws SQLException {
        super(rs);
        this.decouvert = rs.getDouble("decouvert");
    }

    public CompteSimple(int identifient, double solde) {
        super(identifient, solde);
        this.decouvert = 0;
    }

    public CompteSimple(int identifient, double solde, double decouvert) {
        super(identifient, solde);
        this.decouvert = decouvert;
    }

    public CompteSimple(int identifient, double solde, boolean payant) {
        super(identifient, solde, payant);
        this.decouvert = 0;
    }

    public CompteSimple(int identifient, double solde, double decouvert, boolean payant) {
        super(identifient, solde, payant);
        this.decouvert = decouvert;
    }

    public double getDecouvert() {
        return decouvert;
    }

    @Override
    public String toString() {
        return String.format("CompteSimple{id=%d, identifient=%d, solde=%.2f, payant=%b, decouvert=%.2f}",
                id, identifient, solde, payant, decouvert);
    }

    public void versement(double solde) {
        if (solde > 0)
            super.versement(solde);
    }

    public void retrait(double solde) {
        if (solde < 0) solde *= -1;
        double oldSolde = this.solde;
        super.retrait(solde);
        if (-decouvert > this.solde)
            this.solde = oldSolde;
    }
}
