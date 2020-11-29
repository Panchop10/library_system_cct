package com.cct.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Book {
    private int id;
    private String name;
    private int author_id;
    private String isbn;

    public Book(int id, String name, int author_id, String isbn){
        this.id = id;
        this.name = name;
        this.author_id = author_id;
        this.isbn = isbn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthorName() {
        return "";
    }

    /**
     * Return name of the attributes of the class
     * @return name of attributes and its type of the class as an HashMap
     */
    public static Map<String, String> getAttributes(){
        Map<String, String> attributes = new HashMap<>();

        for(Field field:Book.class.getDeclaredFields()){
            attributes.put(field.getName(), field.getType().getSimpleName());
        }
        return attributes;
    }

    @Override
    public String toString() {
        return "ID: "+this.id+" - Name: "+this.name+" - Author: "+this.author_id+" - ISBN: "+this.isbn;
    }

}
