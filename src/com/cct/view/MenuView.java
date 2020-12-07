package com.cct.view;

import com.cct.controller.MainController;

import java.util.Scanner;

public class MenuView {
    private ResultsView resultsView;

    public MenuView(MainController mainController){

        // creates an instance of the results view and pass the main controller as reference.
        this.resultsView = new ResultsView(mainController);

        // print the menu.
        System.out.println("                                                 \n" +
                "                                           &&&&&            \n" +
                "            /&&&&&/          *%&&&&(.      &&&&&            \n" +
                "        &&&&&&&&&&&&&    *&&&&&&&&&&&&%  &&&&&&&&&&.        \n" +
                "      .&&&&&&*    %(    &&&&&&(    (&   ***&&&&&***         \n" +
                "      &&&&&            &&&&&%              &&&&&            \n" +
                "      &&&&&            &&&&&#              &&&&&            \n" +
                "      &&&&&&#          ,&&&&&&      ,      &&&&&&           \n" +
                "       ,&&&&&&&&&&&&&    &&&&&&&&&&&&&.    *&&&&&&&&        \n" +
                "          .&&&&&&&&&,       &&&&&&&&&#       ,&&&&&.        \n" +
                "");
        System.out.println("              WELCOME TO CCT LIBRARY SYSTEM");


        int menuOption = mainMenu();
        while(menuOption != 0){
            switch (menuOption){
                case 1:
                    menuOptionOne();
                    break;
                case 2:
                    menuOptionTwo();
                    break;
                case 3:
                    menuOptionThree();
                    break;
                case 4:
                    menuOptionFour();
                    break;
                case 5:
                    menuOptionFive();
                    break;
                case 6:
                    menuOptionSix();
                    break;
                case 7:
                    menuOptionSeven();
                    break;
                default:
                    System.out.println("That's not a valid option. Please try again.");
                    break;
            }

            menuOption = mainMenu();
        }
    }

    /**
     * Print main menu of the system
     * @return int with the value selected by the user.
     */
    private int mainMenu(){
        System.out.println(" ");
        System.out.println("Select an option:");
        System.out.println("1 - Search for a specific book by title and/or author name.");
        System.out.println("2 - List all books by title and/or author name alphabetical order.");
        System.out.println("3 - Search for a specific reader by name and/or ID.");
        System.out.println("4 - List all readers by alphabetical and/or ID order.");
        System.out.println("5 - Register that a reader has borrowed a book.");
        System.out.println("6 - Register that a reader has returned a book.");
        System.out.println("7 - For a specific reader, list the books that they have borrowed.");
        System.out.println("0 - Exit.");

        return getUserOption();
    }

    /**
     * Print the menu option 1 and calls the controller.
     */
    private void menuOptionOne(){
        int menuOption = printOptionsOne();
        while(menuOption != 0){
            switch (menuOption){
                case 1:
                    resultsView.searchBookByTitle();
                    pressAnyKey();
                    break;
                case 2:
                    resultsView.searchBookByAuthor();
                    pressAnyKey();
                    break;
                default:
                    System.out.println("That's not a valid option. Please try again.");
                    break;
            }

            menuOption = printOptionsOne();
        }
    }

    private int printOptionsOne(){
        System.out.println(" ");
        System.out.println("Select an option:");
        System.out.println("1 - Search for a specific book by title.");
        System.out.println("2 - Search for a specific book by author name.");
        System.out.println("0 - Go back.");

        return getUserOption();
    }

    /**
     * Print the menu option 2 and calls the controller.
     */
    private void menuOptionTwo(){
        int menuOption = printOptionsTwo();
        while(menuOption != 0){
            switch (menuOption){
                case 1:
                    resultsView.listBookSortedByTitle();
                    pressAnyKey();
                    break;
                case 2:
                    resultsView.listBookSortedByAuthor();
                    pressAnyKey();
                    break;
                default:
                    System.out.println("That's not a valid option. Please try again.");
                    break;
            }

            menuOption = printOptionsTwo();
        }
    }

    private int printOptionsTwo(){
        System.out.println(" ");
        System.out.println("Select an option:");
        System.out.println("1 - List all books by title in alphabetical order.");
        System.out.println("2 - List all books by author in alphabetical order.");
        System.out.println("0 - Go back.");

        return getUserOption();
    }

    /**
     * Print the menu option 3 and calls the controller.
     */
    private void menuOptionThree(){
        int menuOption = printOptionsThree();
        while(menuOption != 0){
            switch (menuOption){
                case 1:
                    resultsView.searchReaderByName();
                    pressAnyKey();
                    break;
                case 2:
                    resultsView.searchReaderById();
                    pressAnyKey();
                    break;
                default:
                    System.out.println("That's not a valid option. Please try again.");
                    break;
            }

            menuOption = printOptionsThree();
        }
    }

    private int printOptionsThree(){
        System.out.println(" ");
        System.out.println("Select an option:");
        System.out.println("1 - Search for a specific reader by name");
        System.out.println("2 - Search for a specific reader by ID");
        System.out.println("0 - Go back.");

        return getUserOption();
    }

    /**
     * Print the menu option 4 and calls the controller.
     */
    private void menuOptionFour(){
        int menuOption = printOptionsFour();
        while(menuOption != 0){
            switch (menuOption){
                case 1:
                    resultsView.listReadersSortedByName();
                    pressAnyKey();
                    break;
                case 2:
                    resultsView.listReadersSortedById();
                    pressAnyKey();
                    break;
                default:
                    System.out.println("That's not a valid option. Please try again.");
                    break;
            }

            menuOption = printOptionsFour();
        }
    }

    private int printOptionsFour(){
        System.out.println(" ");
        System.out.println("Select an option:");
        System.out.println("1 - List all readers by name in alphabetical.");
        System.out.println("2 - List all readers sorted by ID.");
        System.out.println("0 - Go back.");

        return getUserOption();
    }

    /**
     * Print the menu option 5 and calls the controller.
     */
    private void menuOptionFive(){
        resultsView.registerNewBorrowing();
        pressAnyKey();
    }

    /**
     * Print the menu option 6 and calls the controller.
     */
    private void menuOptionSix(){
        resultsView.registerReturnBorrowing();
        pressAnyKey();
    }

    /**
     * Print the menu option 7 and calls the controller.
     */
    private void menuOptionSeven(){
        resultsView.listBorrowingsPerReader();
        pressAnyKey();
    }

    private int getUserOption(){
        Scanner myScanner = new Scanner(System.in);  // Create a Scanner object
        System.out.println(" ");
        System.out.println("Input: ");

        int toReturn = -999;

        while(toReturn == -999){
            try{
                toReturn = Integer.parseInt(myScanner.nextLine());
            }
            catch (Exception e){
                System.out.println("That's not a valid option, please try again.");
                System.out.println("Input: ");
            }
        }

        return toReturn;
    }

    /**
     * Press any key to continue method.
     */
    private void pressAnyKey() {
        Scanner myScanner = new Scanner(System.in);
        System.out.println("Press Enter key to continue...");
        myScanner.nextLine();
    }
}
