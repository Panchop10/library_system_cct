package com.cct.view;

import com.cct.controller.MainController;
import com.cct.model.Model;

import java.util.ArrayList;
import java.util.Scanner;

class ResultsView {
    private MainController mainController;

    ResultsView(MainController mainController){
        this.mainController = mainController;
    }

    void searchBookByTitle(){
        System.out.println("Please enter the title (partially or fully):");
        String title = getUserInput();
        ArrayList<Model> books = mainController.getBooksByTitle(title);

        for(Model book: books){
            System.out.println(book);
        }

        System.out.println("Results found: "+books.size());
    }

    void searchBookByAuthor(){
        System.out.println("Please enter the author name (partially or fully):");
        String author = getUserInput();
        ArrayList<Model> books = mainController.getBooksByAuthor(author);

        for(Model book: books){
            System.out.println(book);
        }

        System.out.println("Results found: "+books.size());
    }

    void listBookSortedByTitle(){
        ArrayList<Model> books = mainController.getBooksSortedByTitle();

        for(Model book: books){
            System.out.println(book);
        }

        System.out.println("Results found: "+books.size());
    }

    void listBookSortedByAuthor(){
        ArrayList<Model> books = mainController.getBooksSortedByAuthor();

        for(Model book: books){
            System.out.println(book);
        }

        System.out.println("Results found: "+books.size());
    }

    void searchReaderByName(){
        System.out.println("Please enter the name of the reader (partially or fully):");
        String name = getUserInput();
        ArrayList<Model> readers = mainController.getReadersByName(name);

        for(Model reader: readers){
            System.out.println(reader);
        }

        System.out.println("Results found: "+readers.size());
    }

    void searchReaderById(){
        System.out.println("Please enter the id of the reader:");
        int id = getUserInputId();
        Model reader = mainController.getReaderById(id);

        System.out.println(reader);

        System.out.println("Results found: 1");
    }

    void listReadersSortedByName(){
        ArrayList<Model> readers = mainController.getReadersSortedByName();

        for(Model reader: readers){
            System.out.println(reader);
        }

        System.out.println("Results found: "+readers.size());
    }

    void listReadersSortedById(){
        ArrayList<Model> readers = mainController.getReadersSortedById();

        for(Model reader: readers){
            System.out.println(reader);
        }

        System.out.println("Results found: "+readers.size());
    }

    void registerNewBorrowing() {
        listReadersSortedByName();
        System.out.println("Please enter the id of the reader:");
        int id_reader = getUserInputId();

        listBookSortedByTitle();
        System.out.println("Please enter the id of the book:");
        int id_book = getUserInputId();

        try {
            boolean borrowed = mainController.registerNewBorrowing(id_reader, id_book);

            if(borrowed){
                System.out.println("Book has been borrowed.");
            }
            else{
                System.out.println("The reader has been added to the queue list.");
                System.out.println("The reader is in the position "
                        + mainController.getQueueSize(id_book)+ " on the queue.");
            }

        } catch (Exception e) {
            System.out.println("Please try again");
        }
    }

    void registerReturnBorrowing() {
        //if there are books borrowed, ask the user which book wants to return.
        if(listActiveBorrowings() > 0){
            System.out.println("Please enter the id of the borrowing to return:");
            int id_borrowing = getUserInputId();

            try {
                boolean queueForTheBook = mainController.returnBorrowing(id_borrowing);

                System.out.println("Book returned successfully.");

                if(queueForTheBook){
                    System.out.println("The next reader in the waiting list is:");
                    System.out.println(mainController.getNextInWaitingListByBorrowing(id_borrowing));
                }

            } catch (Exception e) {
                System.out.println("Please try again");
            }
        }
        else{
            System.out.println("There are no active borrowings.");
        }
    }

    /**
     * return the number of active borrowings
     * @return int of size of ArrayList active borrowings
     */
    private int listActiveBorrowings(){
        ArrayList<Model> borrowings = mainController.getActiveBorrowings();

        for(Model borrowing: borrowings){
            System.out.println(borrowing);
        }

        System.out.println("Results found: "+borrowings.size());
        return borrowings.size();
    }

    void listBorrowingsPerReader() {
        listReadersSortedByName();
        System.out.println("Please enter the id of the reader:");
        int id_reader = getUserInputId();

        try {
            ArrayList<Model> borrowings = mainController.getBorrowingsPerReader(id_reader);

            for(Model borrowing: borrowings){
                System.out.println(borrowing);
            }

            System.out.println("Results found: "+borrowings.size());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Please try again");
        }
    }

    /**
     * get user input as int, it asks for the int again if the value entered is not valid.
     * @return int input by the user.
     */
    private int getUserInputId(){
        Scanner myScanner = new Scanner(System.in);  // Create a Scanner object

        int toReturn = -9999;

        while(toReturn == -9999){
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
     * Ask user for an input and returns that
     * @return a string with the value input by the user.
     */
    private String getUserInput(){
        Scanner myScanner = new Scanner(System.in);  // Create a Scanner object

        return myScanner.nextLine();
    }

}
