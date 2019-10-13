package bo;

import dal.*;

import java.util.*;

public class Menu {
    Scanner sc = new Scanner(System.in);
    private List<Agence> agences;

    public Menu() {
        agences = new ArrayList<Agence>();
    }

    public double getNumber() {
        double rep = 0;
        boolean ok = false;
        do {
            if (ok)
                System.out.println( "Mauvais choix... merci de recommencer !" );
                System.out.print( "Entrez votre choix : " );
            try {
                rep = Double.valueOf(sc.nextLine().replace(',','.'));
            } catch ( NumberFormatException e ) {
                rep = 0;
                ok = true;
            }
        } while (ok && rep==0);
        return rep;
    }

    public int getNumber(int max) {
        int response = 0;
        do {
            if (response < 0 || response > max)
                System.out.printf( "Entrer un nombre entre %d et %d comprie !%n", 0, max );
            response = (int)getNumber();
        } while (response < 0 || response > max);
        return response;
    }

    public double getNumber(double max) {
        double response = 0;
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
        System.out.println(agence);
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

        if (agences.size() == 0) {
            System.out.println("Il n'y a pas d'agences enregistrées");
            return;
        } else
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
            rep = (int)getNumber();
            if (rep > 0) {
                curAgence = AgenceDAO.getDAO().findById(rep);

            } else if (rep >= -agences.size() && rep < 0)
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
                case 4: selectCompte(curAgence); break;
                case 5: listCompte(curAgence); break;
            }
        }
    }

    public void createCompte(Agence curAgence) {
        System.out.println("Type de compte possible :");
        System.out.println("1 - Compte simple");
        System.out.println("2 - Compte simple payant");
        System.out.println("3 - Compte épargne");
        System.out.println("4 - Compte épargne payant");
        System.out.println("0 - Retour");
        int rep = getNumber(4);
        if (rep == 0)
            return;

        System.out.println("Identifiant :");
        int identifiant = (int)getNumber();
        System.out.println("Solde par défaut :");
        double solde = getNumber(10000.0);

        Compte compte;
        switch (rep) {
            case 1:
            case 2:
                System.out.println("Découver autorisé : ");
                double decouvert = getNumber(10000.0);
                compte = new CompteSimple(identifiant, solde, decouvert,rep == 2);
                CompteSimpleDAO.getDAO().create((CompteSimple)compte, curAgence.getId());
                curAgence.addCompte(compte);
                break;
            case 3:
            case 4:
                System.out.println("Taux d'intérêt :");
                double taux = getNumber(100.0);
                compte = new CompteEpargne(identifiant, solde, taux, rep == 4);
                CompteEpargneDAO.getDAO().create((CompteEpargne)compte, curAgence.getId());
                curAgence.addCompte(compte);
                break;
        }
    }

    public void listCompte(Agence curAgence) {
        if (curAgence.getNbCompte() != 0) {
            System.out.println("Actualiser la listes des comptes : ");
            System.out.println("1 - Oui");
            System.out.println("2 - Non");
            System.out.println("0 - Retour");
            switch (getNumber(2)) {
                case 1:
                    curAgence.clearComptes();
                    curAgence.addComptes(CompteDAO.getDAO().findByIdAgence(curAgence.getId()));
                case 2: break;
                default: return;
            }
        } else
            curAgence.addComptes(CompteDAO.getDAO().findByIdAgence(curAgence.getId()));

        System.out.println( "=====================================" );
        System.out.println( "========= LISTE DES COMPTES =========" );
        System.out.println( "=====================================" );

        if(curAgence.getNbCompte() == 0) {
            System.out.println("Il n'y a pas de compte enregistré");
            return;
        } else
            curAgence.printComptes();

        selectCompte(curAgence);
    }


    public void selectCompte(Agence curAgence) {
        System.out.println( "Sélectionner un compte : " );
        System.out.println("-X - Le Xème compte");
        System.out.println("Y - L'agence numero Y");
        System.out.println("0 - Retour");
        int rep = 0;
        Compte curCompte = null;
        do {
            rep = (int)getNumber();
            if (rep > 0)
                curCompte = curAgence.getCompte(rep-1);
            else if (rep >= -curAgence.getNbCompte() && rep < 0)
                curCompte = curAgence.getCompte(-rep-1);
        } while (curCompte == null && rep != 0);

        while (rep != 0) {
            System.out.println( "========================================" );
            System.out.printf(  "========= MENU - COMPTE N°%04d =========%n", curCompte.getId());
            System.out.println( "========================================" );
            System.out.println( "1 - Faire un versement" );
            System.out.println( "2 - Faire un retrait" );
            System.out.println( "3 - Supprimer le compte" );
            System.out.println( "0 - Quitter" );
            rep = getNumber(3);

            switch (rep) {
                case 1: versement(curCompte); break;
                case 2: retrait(curCompte);break;
                case 3: deleteCompte(curAgence, curCompte);
                    rep = 0;
                    break;
            }
        }
    }
    public void versement(Compte curCompte) {
        System.out.println( "Sélectionner le montant à verser : " );
        double rep = getNumber();
        curCompte.versement(rep);
        CompteDAO.getDAO().update(curCompte);
    }

    public void retrait(Compte curCompte) {
        System.out.println( "Sélectionner le montant à retirer : " );
        double rep = getNumber();
        curCompte.retrait(rep);
        CompteDAO.getDAO().update(curCompte);
    }

    public void deleteCompte(Agence curAgence, Compte curCompte) {
        System.out.println("Etes-vous sûr : ");
        System.out.println("0 - Oui");
        System.out.println("1 - Non");
        if (getNumber() == 0)
            curAgence.removeCompte(curCompte);
    }
}

