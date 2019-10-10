package bo;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Menu {
    Scanner sc = new Scanner(System.in);
    Agence agence = new Agence(1, 12345, "8 rue d'Alger");
    private int choix;

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
            System.out.println( "5 - Versement");
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
                listComptes(true);
                break;
            case 5:
                versement();
                break;
            case 6:
//                retrait();
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
        int response2 = 0;
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
            first = false;
        } while (response < 1  || response > 2);

        if(response == 1) {
            do {
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
                second = false;
            }while (response2 < 1 || response2 > 2);
        }
        if (response == 2) {
            agence.creationCompteEpargne();
        } else if(response == 1 && response2 == 2) {
            agence.creationCompteSimple(true);
        } else {
            agence.creationCompteSimple(false);
        }
        this.mainMenu();
    }

    public void listComptes(boolean dspMainMenu) {
        System.out.println( "======================================" );
        System.out.println( "========= LISTE DES COMPTES =========" );
        System.out.println( "======================================" );

        boolean first = true;
        int response;
        Scanner sc = new Scanner(System.in);
        do {
            if (!first) {
                System.out.println("Mauvais choix");
            }
            System.out.println("Sélectionner le type de comptes à lister");
            System.out.println("1 - Simple");
            System.out.println("2 - Epargne");
            try {
                response = sc.nextInt();
            } catch(InputMismatchException e) {
                response = -1;
            } finally {
                sc.nextLine();
            }
        } while (response < 1 || response > 2);
        List<Compte> list;
        if (response == 1) {
            this.choix = response;
            list = agence.getComptesSimple();
        } else {
            this.choix = response;
            list = agence.getComptesEpargne();
        }

        for ( int i = 0, length = list.size(); i < length; ++i ) {
            System.out.printf( "%d - %s%n", i + 1, list.get( i ) );
        }

        if(dspMainMenu) {
            this.mainMenu();
        }
    }

    public void versement() {

        System.out.println( "Choisissez le compte ..." );
        boolean first = true;
        int response;
        int sizeEpargne = agence.getComptesEpargne().size();
        int sizeSimple = agence.getComptesSimple().size();
        do {
            if ( !first ) {
                System.out.println( "Mauvais choix... merci de recommencer !" );
            }
            this.listComptes(false);
            System.out.print( "Votre choix : " );
            try {
                response = sc.nextInt();
            } catch ( InputMismatchException e ) {
                response = -1;
            } finally {
                sc.nextLine();
            }
            first = false;
        } while ( (response < 1 || response > sizeEpargne) || (response < 1 || response > sizeSimple) );

        if (this.choix == 1) {
            Compte compte = agence.getComptesSimple().get( (response -1 ) );
            System.out.printf( "======== VERSEMENT DU COMPTE SIMPLE ========" );

            System.out.println( "Entrez la somme à verser : ");
            double versement = sc.nextDouble();
            if (versement != 0) {
            }

        }
    }
}
