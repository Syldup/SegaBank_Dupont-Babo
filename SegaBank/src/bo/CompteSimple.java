package bo;

import java.util.Map;

public class CompteSimple {

    protected static Map<Integer, CompteSimple> comptes;

    protected int identifient;
    protected double solde;
    protected boolean payant;

    public CompteSimple() {
    }
    public CompteSimple(int identifient, double solde) {
        this.identifient = identifient;
        this.solde = solde;
        this.payant = false;
    }
    public CompteSimple(int identifient, double solde, boolean payant) {
        this.identifient = identifient;
        this.solde = solde;
        this.payant = payant;
    }

    public int getIdentifient() {
        return identifient;
    }

    public void setIdentifient(int identifient) {
        this.identifient = identifient;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

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
}
