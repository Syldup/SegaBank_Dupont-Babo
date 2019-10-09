package bo;

public class CompteEpargne extends Compte {
    private double tauxInteret;

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

    public void calculInteret() {
        solde *= 1+tauxInteret;
    }
}
