package bo;

public class Compte {
    protected int id = 0;
    protected int identifient;
    protected double solde;
    protected boolean payant;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdentifient() {
        return identifient;
    }

    public double getSolde() {
        return solde;
    }

    public boolean isPayant() { return payant; }

    public int getIdAgence() {
        return 1;
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
