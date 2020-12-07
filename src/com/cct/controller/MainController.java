package com.cct.controller;


import com.cct.model.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainController {
    private AuthorModel authorModel;
    private ReaderModel readerModel;
    private BorrowingModel borrowingModel;
    private BookModel bookModel;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    public MainController(AuthorModel authorModel, ReaderModel readerModel, BorrowingModel borrowingModel,
                          BookModel bookModel){

        this.authorModel = authorModel;
        this.readerModel = readerModel;
        this.borrowingModel = borrowingModel;
        this.bookModel = bookModel;
    }


    /**
     * returns list of books filtered by title
     * @param title title that is going to be searched
     * @return list of books filtered
     */
    public ArrayList<Model> getBooksByTitle(String title) {
        String[] filters = {"name: "+title};

        return bookModel.find(filters);
    }

    /**
     * returns list of books filtered by author
     * @param author name of author that is going to be searched
     * @return list of books filtered
     */
    public ArrayList<Model> getBooksByAuthor(String author) {
        String[] filters = {"author_name: "+author};

        return bookModel.find(filters);
    }

    /**
     * returns list of books sorted by title alphabetically.
     * @return list of books sorted.
     */
    public ArrayList<Model> getBooksSortedByTitle() {
        return bookModel.sortBy("name");
    }

    /**
     * returns list of books sorted by author's name alphabetically.
     * @return list of books sorted.
     */
    public ArrayList<Model> getBooksSortedByAuthor() {
        return bookModel.sortBy("author");
    }

    /**
     * returns list of readers filtered by name
     * @param name name that is going to be searched
     * @return list of readers filtered
     */
    public ArrayList<Model> getReadersByName(String name) {
        String[] filters = {"name: "+name};

        return readerModel.find(filters);
    }

    /**
     * returns reader filtered by id
     * @param id that is going to be searched
     * @return reader filtered
     */
    public Model getReaderById(int id) {
        return readerModel.findById(id);
    }

    /**
     * returns list of readers sorted by name alphabetically.
     * @return list of readers sorted.
     */
    public ArrayList<Model> getReadersSortedByName() {
        return readerModel.sortBy("name");
    }

    /**
     * returns list of readers sorted by id.
     * @return list of readers sorted.
     */
    public ArrayList<Model> getReadersSortedById() {
        return readerModel.find();
    }

    /**
     * register new borrowing
     * @param id_reader reader who is going to borrow the book
     * @param id_book book that is going to be borrowed
     * @return boolean if the book was borrowed true, or false if the reader was added to the queue.
     */
    public boolean registerNewBorrowing(int id_reader, int id_book) throws Exception{
        // check if the book is borrowed.
        String[] filters = {"book_id: " + id_book, "status: active"};
        ArrayList<Model> booksBorrowed = borrowingModel.find(filters);

        boolean bookBorrowed = booksBorrowed.size() != 0;

        // check if the book has a queue.
        // validate that book and reader exist.
        Model bookToBorrow = bookModel.findById(id_book);
        if(bookToBorrow == null){
            throw new Exception("The ID of the book does not exist in the database.");
        }

        Model reader = readerModel.findById(id_reader);
        if(reader == null){
            throw new Exception("The ID of the reader does not exist in the database.");
        }

        // borrow book if the book is borrowed, go directly to waiting list, if not, check is there are readers
        // waiting for the book.

        boolean bookWithWaitingList = !bookToBorrow.getWaitingQueue().isEmpty();

        if(bookBorrowed){
            bookToBorrow.getWaitingQueue().enqueue(id_book, id_reader);
            return false;
        }
        else{
            // if the book does not have an active borrowing, check if there is queue for the book
            if(bookWithWaitingList){
                // check if the first user waiting for the book is the one that is going to borrow the book.
                int nextReaderInWaitingList = Integer.parseInt(bookToBorrow.getWaitingQueue().first().get("reader_id"));
                // if the next reader is the same that is borrowing the book.
                if(nextReaderInWaitingList == id_reader){
                    bookToBorrow.getWaitingQueue().dequeue();
                    return true;
                }
                else{
                    bookToBorrow.getWaitingQueue().enqueue(id_book, id_reader);
                    System.out.println("Next borrowing in the waiting list is:");
                    System.out.println(bookToBorrow.getWaitingQueue().first());
                    return false;
                }

            }
            // create new borrowing if there is no waiting list for the book
            else{
                Date date = new Date();
                borrowingModel.create(
                        id_book,
                        id_reader,
                        formatter.format(date),
                        null,
                        "active"
                );

                return true;
            }
        }
    }

    /**
     * Return size of the queue of an specific book
     * @param id_book book to be searched
     * @return number of readers in the queue of the book
     */
    public int getQueueSize(int id_book) {
        return bookModel.findById(id_book).getWaitingQueue().size();
    }

    /**
     * Return next borrowing in the waiting list
     * @param borrowing_id borrowing to be searched
     * @return next borrowing in the waiting list
     */
    public Model getNextInWaitingListByBorrowing(int borrowing_id) {
        Model borrowing = borrowingModel.findById(borrowing_id);
        Model book = bookModel.findById(Integer.parseInt(borrowing.get("book_id")));

        return book.getWaitingQueue().first();
    }

    /**
     * get list of active borrowings
     * @return list of active borrowings
     */
    public ArrayList<Model> getActiveBorrowings() {
        String[] filters = {"status: active"};
        return borrowingModel.find(filters);
    }

    /**
     * returns a book borrowed.
     * @param id_borrowing borrowing that is going to be returned.
     * @return true or false depending on if there is a queue for the book.
     */
    public boolean returnBorrowing(int id_borrowing) throws Exception {
        // check if the borrowing is active.
        String[] filters = {"id: " + id_borrowing, "status: active"};
        ArrayList<Model> borrowingActive = borrowingModel.find(filters);

        boolean bookBorrowed = borrowingActive.size() != 0;

        // throws an error if the borrowing is not active
        if(!bookBorrowed){
            throw new Exception("The ID of the borrowing does not have an active status.");
        }

        // register return
        Date date = new Date();
        String[] update = {"status: returned", "date_returned: "+formatter.format(date)};

        // element that will be returned - execute update
        Model borrowingUpdated = borrowingModel.update(filters, update).get(0);

        //check if the book has readers waiting in the queue.
        Model bookReturned = bookModel.findById(Integer.parseInt(borrowingUpdated.get("book_id")));
        return !bookReturned.getWaitingQueue().isEmpty();
    }

    /**
     * returns borrowings filtered by reader_id
     * @param id_reader that is going to be searched
     * @return borrowings filtered
     */
    public ArrayList<Model> getBorrowingsPerReader(int id_reader) throws Exception {
        Model reader = readerModel.findById(id_reader);

        if (reader == null) {
            throw new Exception("The ID of the reader does not exist in the database.");
        }
        String[] filters = {"reader_id: " + id_reader};
        return borrowingModel.find(filters);
    }
}
