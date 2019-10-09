package bo;

import java.util.Map;

public class CompteSimple extends Compte {
    private double decouvert;

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

    public void versement(double solde) {
        if (solde > 0)
            super.versement(solde);
    }

    public void retrait(double solde) {
        if (solde < 0) solde *= -1;
        double oldSolde = this.solde;
        super.retrait(solde);
        if (decouvert < this.solde)
            this.solde = oldSolde;
    }
}
