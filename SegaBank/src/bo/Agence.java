package bo;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Agence {
    private int id;
    private int code;
    private String adresse;
    private List<Compte> comptes = new ArrayList<Compte>();

    public Agence(int id, int code, String adresse) {
        this.id = id;
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
        final StringBuilder sb = new StringBuilder("Agence{");
        sb.append("id=").append(id);
        sb.append(", code=").append(code);
        sb.append(", adresse='").append(adresse).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public void creationCompteSimple(boolean payant) {

        if (payant == false) {
            Compte compte = new CompteSimple(1, 0);
            System.out.println(compte.toString());
            this.comptes.add(compte);
        } else if (payant == true) {
            Compte compte = new CompteSimple(1, 0, true);
            System.out.println(compte.toString());
            this.comptes.add(compte);
        }
    }

}
