package bo;

public class Compte {
    protected int identifient;
    protected double solde;
    protected boolean payant;

    public Compte() {
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

    public int getIdentifient() {
        return identifient;
    }

    public double getSolde() {
        return solde;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Compte{");
        sb.append("identifient=").append(identifient);
        sb.append(", solde=").append(solde);
        sb.append(", payant=").append(payant);
        sb.append('}');
        return sb.toString();
    }
}
