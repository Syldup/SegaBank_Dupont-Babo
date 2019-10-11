package bo;

import dal.AgenceDAO;
import dal.CompteDAO;
import dal.CompteEpargneDAO;
import dal.CompteSimpleDAO;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Menu {
    Scanner sc = new Scanner(System.in);
    private List<Agence> agences;
    private List<Compte> comptes;
    List<Compte> comptesSimple = new ArrayList<Compte>();
    List<Compte> comptesEpargne = new ArrayList<Compte>();


    public Menu() {
        agences = new ArrayList<Agence>();
        comptes = new ArrayList<Compte>();
    }

    public int getNumber() {
        int response = 0;
        boolean ok = false;
        do {
            if (ok)
                System.out.println( "Mauvais choix... merci de recommencer !" );
                System.out.print( "Entrez votre choix : " );
            try {
                response = sc.nextInt();
            } catch ( InputMismatchException e ) {
                response = 0;
                ok = true;
            } finally {
                sc.nextLine();
            }
        } while (ok);
        return response;
    }

    public int getNumber(int max) {
        int response = 0;
        do {
            if (response < 0 || response > max)
                System.out.printf( "Entrer un nombre entre %d et %d comprie !%n", 0, max );
            response = getNumber();
        } while (response < 0 || response > max);
        return response;
    }

    public String getString(String msg) {
        String str = "";
        do {
            if (!str.equals(""))
                System.out.println("Erreur... merci de recommencer !");
            System.out.println(msg);
            str = sc.nextLine();
        } while (str.equals("") || str.length() > 40);
        return str;
    }



    public void mainMenu() {
        int response;
        do {
            System.out.println( "=====================================" );
            System.out.println( "=========== MENU - AGENCES ==========" );
            System.out.println( "=====================================" );
            System.out.println( "1 - Créer une nouvelle agence" );
            System.out.println( "2 - Sélectionner une agence" );
            System.out.println( "3 - Lister les agences" );
            System.out.println( "0 - Quitter" );
            response = getNumber(3);

            switch (response) {
                case 1: createAgence(); break;
                case 2: selectAgence(); break;
                case 3: listAgence(); break;
            }
        } while (response != 0);
    }

    public void createAgence() {
        String codeAgence = getString("Entrer le code de l'agence : ");
        String adresseAgence = getString("Entrer l'adresse de l'agence : ");

        Agence agence = new Agence(codeAgence, adresseAgence);
        AgenceDAO.getDAO().create(agence);
        agences.add(agence);
    }

    public void listAgence() {
        if (agences.size() != 0) {
            System.out.println("Actualiser la list des agences : ");
            System.out.println("1 - Oui");
            System.out.println("2 - Non");
            System.out.println("0 - Retour");
            switch (getNumber(2)) {
                case 1:
                    agences.clear();
                    agences = AgenceDAO.getDAO().findAll();
                case 2: break;
                default: return;
            }
        } else
            agences = AgenceDAO.getDAO().findAll();

        System.out.println( "=====================================" );
        System.out.println( "========= LISTE DES AGENCES =========" );
        System.out.println( "=====================================" );

        for (int i=0; i<agences.size(); i++)
            System.out.printf("%d - %s%n", i+1, agences.get(i));
        selectAgence();
    }

    public void editAgence(Agence agence) {
        int rep;
        do {
            System.out.printf("Dans %s, Modifier :%n", agence);
            System.out.println("1 - code");
            System.out.println("2 - addresse");
            System.out.println("3 - Valider");
            System.out.println("0 - Retour");
            rep = getNumber(3);
            switch (rep) {
                case 1: agence.setCode(getString("Entrer le code de l'agence : ")); break;
                case 2: agence.setAdresse(getString("Entrer l'adresse de l'agence : ")); break;
                case 0:
                    Agence oldAgence = AgenceDAO.getDAO().findById(agence.getId());
                    agence.setAdresse(oldAgence.getAdresse());
                    agence.setCode(oldAgence.getCode());
                    break;
                case 3:
                    AgenceDAO.getDAO().update(agence);
                    System.out.println( "Les modifications ont été enregistrées" );
                    rep = 0;
            }
        } while (rep != 0);
    }

    public void deleteAgence(Agence agence) {
        System.out.println("Etes-vous sûr : ");
        System.out.println("0 - Oui");
        System.out.println("1 - Non");
        if (getNumber(0) == 0) {
            agences.remove(agence);
            AgenceDAO.getDAO().deleteById(agence.getId());
        }
    }

    public void selectAgence() {
        System.out.println( "Sélectionner une agence : " );
        System.out.println("-X - La Xème agence");
        System.out.println("Y - L'agence numero Y");
        System.out.println("0 - Retour");
        int rep;
        Agence curAgence = null;
        do {
            rep = getNumber();
            if (rep > 0)
                curAgence = AgenceDAO.getDAO().findById(rep);
            else if (rep >= -agences.size() && rep < 0)
                curAgence = agences.get(-rep-1);
        } while (curAgence == null && rep != 0);

        while (rep != 0) {
            System.out.println( "========================================" );
            System.out.printf(  "========= MENU - AGENCE N°%04d =========%n", curAgence.getId());
            System.out.println( "========================================" );
            System.out.println( "1 - Modifier l'agence" );
            System.out.println( "2 - Supprimer l'agence" );
            System.out.println( "3 - Créer un comple" );
            System.out.println( "4 - Sélectionner un compte" );
            System.out.println( "5 - Liser les comples" );
            System.out.println( "0 - Quitter" );
            rep = getNumber(5);

            switch (rep) {
                case 1: editAgence(curAgence); break;
                case 2:
                    deleteAgence(curAgence);
                    rep = 0;
                    break;
                case 3: createCompte(curAgence); break;
                /*
                case 4: selectCompte(); break;
                 */
                case 5: listCompte(curAgence); break;
            }
        }
    }

    public void createCompte(Agence curAgence) {
        int rep;
        System.out.println("1 - Compte simple");
        System.out.println("2 - Compte simple payant");
        System.out.println("3 - Compte épargne");
        System.out.println("4 - Compte épargne payant");
        rep = getNumber(4);

        System.out.println("Identifiant :");
        int identifiant = getNumber(10);
        System.out.println("Solde par défaut :");
        int solde = getNumber(1000);
        switch (rep) {
            case 1:
                System.out.println("Découvert autorisé : ");
                double decouvert = getNumber(1000);
                CompteSimple compte = new CompteSimple(identifiant,solde,decouvert);
                CompteSimpleDAO.getDAO().create(compte, curAgence.getId());
                comptesSimple.add(compte);
                curAgence.ajouterCompte(compte);
                break;
            case 2:
                System.out.println("Découver autorisé : ");
                double decouvert2 = getNumber(1000);
                CompteSimple compte2 = new CompteSimple(identifiant,solde,decouvert2,true);
                CompteSimpleDAO.getDAO().create(compte2, curAgence.getId());
                comptesSimple.add(compte2);
                curAgence.ajouterCompte(compte2);
                break;
            case 3:
                System.out.println("Taux d'intérêt : ");
                double taux = getNumber(100);
                CompteEpargne compte3 = new CompteEpargne(identifiant, solde, taux);
                CompteEpargneDAO.getDAO().create(compte3, curAgence.getId());
                comptesEpargne.add(compte3);
                curAgence.ajouterCompte(compte3);
                break;
            case 4:
                System.out.println("Taux d'intérêt :");
                double taux2 = getNumber(100);
                CompteEpargne compte4 = new CompteEpargne(identifiant, solde, taux2, true);
                CompteEpargneDAO.getDAO().create(compte4, curAgence.getId());
                comptesEpargne.add(compte4);
                curAgence.ajouterCompte(compte4);
                break;
        }
    }

    public void listCompte(Agence curAgence) {
        if (curAgence.getComptes().size() != 0) {
            System.out.println("Actualiser la listes des comptes : ");
            System.out.println("1 - Oui");
            System.out.println("2 - Non");
            System.out.println("0 - Retour");
            switch (getNumber(2)) {
                case 1:
                    curAgence.clear();
                    curAgence.ajouterComptes(CompteSimpleDAO.getDAO().findAgence(curAgence.getId()));
                case 2: break;
                default: return;
            }
        } else {
            curAgence.ajouterComptes(comptesSimple);
            curAgence.ajouterComptes(comptesEpargne);
        }

        System.out.println( "=====================================" );
        System.out.println( "========= LISTE DES COMPTES =========" );
        System.out.println( "=====================================" );

        for (int i=0; i<comptesSimple.size(); i++)
            System.out.printf("%d - %s%n", i+1, comptesSimple.get(i));
       // selectAgence();
    }
}
