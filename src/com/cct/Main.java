package com.cct;

import com.cct.controller.BookController;
import com.cct.controller.MainController;
import com.cct.database.RetrieveData;
import com.cct.model.Book;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // load all the information from the csv files in the folder database and store the results in ArrayLists.
        RetrieveData loadData = new RetrieveData();
        ArrayList<Book> listBooks = loadData.getBooks();

        BookController.bulkInsert(listBooks);

        //Book newBook = new Book(13, "Clean Code: A Handbook of Agile Software Craftsmanship",2,"9780132350884");
        //Book newBook = BookController.findById(300);
        //BookController.insert(newBook);
//        String[] filters = {"name: with"};
//
//        ArrayList<Book> sortedList = BookController.find(filters);
        ArrayList<Book> sortedList = BookController.sortBy("name");

        if (sortedList != null) {
            for (Book book: sortedList) {
                System.out.println(book);
            }
        }


//        MainView mainView = new MainView();
//        MainController mainController = new MainController();
//        int option = mainView.getUserOption();
//
//        if(option==1){
//            mainController.getBooksSorted(listBooks);
//        }
    }
}
