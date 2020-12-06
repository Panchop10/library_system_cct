package com.cct;

import com.cct.model.*;

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

        AuthorModel authorModel = new AuthorModel();
        ReaderModel readerModel = new ReaderModel();
        BorrowingModel borrowingModel = new BorrowingModel(readerModel);
        BookModel bookModel = new BookModel(authorModel, borrowingModel);

//        String[] filters = {"name: with"};
//        String[] update = {"author_id: 2"};
//        ArrayList<Model> sortedList = bookModel.update(filters, update);
//        bookModel.remove(filters);
        ArrayList<Model> sortedList = bookModel.find();
        ArrayList<Model> sortedAuthors = authorModel.find();
        ArrayList<Model> sortedReaders = readerModel.find();
        ArrayList<Model> sortedBorrowings = borrowingModel.find();



        if (sortedList != null) {
            for (Model book: sortedList) {
                System.out.println(book);
            }
        }




        if (sortedAuthors != null) {
            for (Model author: sortedAuthors) {
                System.out.println(author);
            }
        }




        if (sortedReaders != null) {
            for (Model reader: sortedReaders) {
                System.out.println(reader);
            }
        }




        if (sortedBorrowings != null) {
            for (Model borrowing: sortedBorrowings) {
                System.out.println(borrowing);
            }
        }

//        BorrowingQueue borrowingQueue = new BorrowingQueue(borrowingModel);
//
//        System.out.println(borrowingQueue);
//        borrowingQueue.enqueueExisting(sortedBorrowings.get(0));
//        System.out.println(borrowingQueue);
//        borrowingQueue.enqueueExisting(sortedBorrowings.get(1));
//        System.out.println(borrowingQueue);
//        borrowingQueue.enqueueExisting(sortedBorrowings.get(2));
//        System.out.println(borrowingQueue);
//
//        borrowingQueue.dequeue();
//        System.out.println(borrowingQueue);
//        borrowingQueue.dequeue();
//        System.out.println(borrowingQueue);
//        borrowingQueue.dequeue();
//        System.out.println(borrowingQueue);
//
//
//        System.out.println(borrowingQueue.size());
//
//
//        sortedBorrowings = borrowingModel.find();
//
//        if (sortedBorrowings != null) {
//            for (Model borrowing: sortedBorrowings) {
//                System.out.println(borrowing);
//            }
//        }

        sortedList = bookModel.sortBy("author");


        if (sortedList != null) {
            for (Model book: sortedList) {
                System.out.println(book);
                System.out.println(book.getWaitingQueue());
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
