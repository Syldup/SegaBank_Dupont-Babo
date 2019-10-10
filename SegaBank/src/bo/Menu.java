package bo;

import dal.AgenceDAO;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Menu {
    Scanner sc = new Scanner(System.in);
    private List<Agence> agences;

    public Menu() {
        agences = new ArrayList<Agence>();
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
                /*
                case 3: createCompte(); break;
                case 4: selectCompte(); break;
                case 5: listCompte(); break;*/
            }
        }
    }

}
