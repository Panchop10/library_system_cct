package com.cct.model;

import com.cct.database.RetrieveData;
import com.cct.database.StoreData;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BookModel extends Model{
    private int id;
    private String name;
    private int author_id;
    private String isbn;

    // Load info from the csv files when the class is instantiated.
    public BookModel(){
        loadData();
    }

    // Private constructor that can be invoked by the create method.
    private BookModel(int id, String name, int author_id, String isbn){
        this.id = id;
        this.name = name;
        this.author_id = author_id;
        this.isbn = isbn;
    }

    /**
     * Create method that returns a new book with an id by default **It won't save it in the database**
     * This book will have to be added manually to the database later.
     * @param id positive integer.
     * @param name string with the name of the book.
     * @param author_id id of the author of the book.
     * @param isbn unique number for each book.
     * @return a new book with the values given.
     */
    public static BookModel create(int id, String name, int author_id, String isbn) {
        return new BookModel(id, name, author_id, isbn);
    }

    /**
     * Generates a new id and add the new book to the database.
     * @param name string with the name of the book.
     * @param author_id id of the author of the book.
     * @param isbn unique number for each book.
     * @return the new book that was stored in the database.
     */
    public BookModel create(String name, int author_id, String isbn){
        BookModel newBook = new BookModel(this.getLastId()+1, name, author_id, isbn);
        this.binaryInsert(newBook);
        return newBook;
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
            case "name":
                return getName();
            case "author_id":
                return String.valueOf(getAuthor_id());
            case "author":
                return getAuthorName();
            case "isbn":
                return getIsbn();
            default:
                return null;
        }
    }

    @Override
    void loadData() {
        // load all the information from the csv files in the folder database and store the results in memory.
        RetrieveData.loadBooks(this);
    }

    /**
     * Return name of the attributes of the class
     * @return name of attributes and its type of the class as an HashMap
     */
    @Override
    public Map<String, String> getAttributes(){
        Map<String, String> attributes = new HashMap<>();

        for(Field field:BookModel.class.getDeclaredFields()){
            attributes.put(field.getName(), field.getType().getSimpleName());
        }
        return attributes;
    }

    /**
     * Store and save all the books in their actual state in text files.
     */
    @Override
    void save() {
        StoreData.saveBooks(this.find());
    }

    /**
     * converts the model to csv line
     * @return string with the values of the model.
     */
    @Override
    public String toCSV() {
        return this.id+","+this.name+","+this.author_id+","+this.isbn;
    }

    // Return the id of the book
    @Override
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAuthor_id() {
        return this.author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = Integer.parseInt(author_id);
    }

    public String getIsbn() {
        return this.isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthorName() {
        return "";
    }

    @Override
    public String toString() {
        return "ID: "+this.id+" - Name: "+this.name+" - Author: "+this.author_id+" - ISBN: "+this.isbn;
    }
}
