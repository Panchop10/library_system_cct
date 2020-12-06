package com.cct.model;

import com.cct.database.RetrieveData;
import com.cct.database.StoreData;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class BorrowingModel extends Model {
    private int id;
    private int book_id;
    private int reader_id;
    private String date_borrowed;
    private String date_returned;
    private String status;

    // Load info from the csv files when the class is instantiated.
    public BorrowingModel() { loadData(); }

    // Private constructor that can be invoked by the create method.
    private BorrowingModel(int id, int book_id, int reader_id, String date_borrowed,
                           String date_returned, String status){
        this.id = id;
        this.book_id = book_id;
        this.reader_id = reader_id;
        this.date_borrowed = date_borrowed;
        this.date_returned = date_returned;
        this.status = status;
    }

    /**
     * Create method that returns a new borrowing with an id by default **It won't save it in the database**
     * This borrowing will have to be added manually to the database later.
     * @param id positive integer.
     * @param book_id id of the book borrowed
     * @param reader_id id of the reader who borrowed the book
     * @param date_borrowed date when the book was borrowed
     * @param date_returned date when the reader returned the book
     * @param status queued / active / returned
     * @return a new borrowing with the values given.
     */
    public static BorrowingModel create(int id, int book_id, int reader_id, String date_borrowed,
                                        String date_returned, String status){
        return new BorrowingModel(id, book_id, reader_id, date_borrowed, date_returned, status);
    }

    /**
     * Generates a new id and add the new borrowing to the database.
     * @param book_id id of the book borrowed
     * @param reader_id id of the reader who borrowed the book
     * @param date_borrowed date when the book was borrowed
     * @param date_returned date when the reader returned the book
     * @param status queued / active / returned
     * @return the new borrowing that was stored in the database.
     */
    public BorrowingModel create(int book_id, int reader_id, String date_borrowed,
                                 String date_returned, String status){
        BorrowingModel newBorrowing = new BorrowingModel(
                this.getLastId()+1,
                book_id,
                reader_id,
                date_borrowed,
                date_returned,
                status
        );
        this.binaryInsert(newBorrowing);
        return newBorrowing;
    }

    @Override
    void loadData() {
        // load all the information from the csv files in the folder database and store the results in memory.
        RetrieveData.loadBorrowings(this);
    }

    /**
     * Returns value of the the attributes of the object as String
     * @param attribute that is going to be gotten from the element
     * @return value of the attribute as string
     */
    @Override
    String get(String attribute) {
        switch (attribute){
            case "id":
                return String.valueOf(getId());
            case "book_id":
                return String.valueOf(getBook_id());
            case "reader_id":
                return String.valueOf(getReader_id());
            case "date_borrowed":
                return getDate_borrowed();
            case "date_returned":
                return getDate_returned();
            case "status":
                return getStatus();
            default:
                return null;
        }
    }

    @Override
    Map<String, String> getAttributes() {
        Map<String, String> attributes = new HashMap<>();

        for(Field field:BorrowingModel.class.getDeclaredFields()){
            attributes.put(field.getName(), field.getType().getSimpleName());
        }
        return attributes;
    }

    @Override
    void save() {
        StoreData.saveBorrowings(this.find());
    }

    @Override
    public String toCSV() {
        return this.id+","+this.book_id+","+this.reader_id+","+this.date_borrowed
                +","+this.date_returned+","+this.status;
    }


    @Override
    int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getReader_id() {
        return reader_id;
    }

    public void setReader_id(int reader_id) {
        this.reader_id = reader_id;
    }

    public String getDate_borrowed() {
        return date_borrowed;
    }

    public void setDate_borrowed(String date_borrowed) {
        this.date_borrowed = date_borrowed;
    }

    public String getDate_returned() {
        return date_returned;
    }

    public void setDate_returned(String date_returned) {
        this.date_returned = date_returned;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ID: "+this.id+" - Book: "+this.book_id+" - Reader: "+this.reader_id
                +" - Date borrowed: "+this.date_borrowed+" - Date returned: "+this.date_returned
                +" - Status: "+this.status;
    }
}
