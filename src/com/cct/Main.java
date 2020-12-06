package com.cct;

import com.cct.model.BookModel;
import com.cct.model.Model;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // load all the information from the csv files in the folder database and store the results in ArrayLists.
//        RetrieveData loadData = new RetrieveData();
//        ArrayList<Model> listBooks = new ArrayList<>(loadData.getBooks());

        //BookController.bulkInsert(listBooks);

        //Book newBook = new Book(13, "Clean Code: A Handbook of Agile Software Craftsmanship",2,"9780132350884");
        //Book newBook = BookController.findById(300);
        //BookController.insert(newBook);
       // String[] filters = {"id: 6"};
        //String[] update = {"name: with"};
//
//        ArrayList<Book> sortedList = BookController.find(filters);
        //ArrayList<Book> sortedList = BookController.sortBy("name");

//        BookController.update()
 //       ArrayList<Book> sortedList = BookController.update(filters, update);

        //BookController.remove(update);
        //BookController bookController = new BookController();
        //ArrayList<Object> sortedList = bookController.find2();

        BookModel bookModel = new BookModel();

//        String[] filters = {"name: with"};
//        String[] update = {"author_id: 2"};
//        ArrayList<Model> sortedList = bookModel.update(filters, update);
//        bookModel.remove(filters);
        ArrayList<Model> sortedList = bookModel.find();


        if (sortedList != null) {
            for (Model book: sortedList) {
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
