import bo.Agence;
import bo.Menu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App {

    private static Scanner sc = new Scanner( System.in );

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.mainMenu();
    }
}
