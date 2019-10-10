package bo;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Menu {
    Scanner sc = new Scanner(System.in);
    Agence agence = new Agence(1, 12345, "8 rue d'Alger");

    public Menu() {
    }

    public void mainMenu() {
        int response;
        boolean first = true;
        do {
            if ( !first ) {
                System.out.println( "Mauvais choix... merci de recommencer !" );
            }
            System.out.println( "======================================" );
            System.out.println( "=========== MENU - COMPTE ==========" );
            System.out.println( "======================================" );
            System.out.println( "1 - Ajouter un nouveau compte" );
            System.out.println( "2 - Modifier un compte existant" );
            System.out.println( "3 - Supprimer un compte existant" );
            System.out.println( "4 - Lister les comptes" );
            System.out.println( "9 - Quitter" );
            System.out.print( "Entrez votre choix : " );
            try {
                response = sc.nextInt();
            } catch ( InputMismatchException e ) {
                response = -1;
            } finally {
                sc.nextLine();
            }
            first = false;
        } while ( response < 1 || response > 9 );

        switch ( response ) {
            case 1:
                creationCompte(this.agence);
                break;
            case 2:
//                updateContact();
                break;
            case 3:
                break;
            case 4:
                listComptes();
                break;
            case 5:
//                storeCurrentBook();
                break;
            case 6:
//                restoreBackup();
                break;
            case 7:
//                sortContacts(true);
                break;
            case 8:
//                sortContacts(false);
                break;
        }
    }

    public void creationCompte(Agence agence) {
        boolean first = true;
        boolean second = true;
        int response;
        int response2;
        Scanner sc = new Scanner(System.in);
        do {
            if (!first) {
                System.out.println("Mauvais choix");
            }
            System.out.println("Sélectionner le type de compte");
            System.out.println("1 - Simple");
            System.out.println("2 - Epargne");
            try {
                response = sc.nextInt();
            } catch (InputMismatchException e) {
                response = -1;
            } finally {
                sc.nextLine();
            }

            if (!second) {
                System.out.println("Mauvais choix");
            }
            System.out.println("Sélectionner si il s'agit de compte simple ou payant");
            System.out.println("1 - Simple");
            System.out.println("2 - Payant");
            try {
                response2 = sc.nextInt();
            } catch (InputMismatchException e) {
                response2 = -1;
            } finally {
                sc.nextLine();
            }
            first = false;
        } while ((response < 1 && (response2 < 1 || response2 > 2)) || response > 2);

        if (response == 2) {
        } else if(response == 1 && response2 == 2) {
            agence.creationCompteSimple(true);
        } else {
            agence.creationCompteSimple(false);
        }
        this.mainMenu();
    }

    public void listComptes() {
        System.out.println( "======================================" );
        System.out.println( "========= LISTE DES COMPTES =========" );
        System.out.println( "======================================" );

        List<Compte> list = agence.getComptes();

        for ( int i = 0, length = list.size(); i < length; ++i ) {
            System.out.printf( "%d - %s%n", i + 1, list.get( i ) );
        }

        this.mainMenu();
    }
}
