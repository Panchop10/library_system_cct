package com.cct.model;

import com.cct.database.RetrieveData;
import com.cct.database.StoreData;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class AuthorModel extends Model {
    private int id;
    private String first_name;
    private String last_name;

    // Load info from the csv files when the class is instantiated.
    public AuthorModel(){ loadData(); }

    // Private constructor that can be invoked by the create method.
    private AuthorModel(int id, String first_name, String last_name){
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    /**
     * Create method that returns a new author with an id by default **It won't save it in the database**
     * This author will have to be added manually to the database later.
     * @param id positive integer.
     * @param first_name String with the first name of the author
     * @param last_name String with the last name of the author
     * @return a new author with the values given.
     */
    public static AuthorModel create(int id, String first_name, String last_name) {
        return new AuthorModel(id, first_name, last_name);
    }

    /**
     * Generates a new id and add the new author to the database.
     * @param first_name String with the first name of the author
     * @param last_name String with the last name of the author
     * @return the new author that was stored in the database.
     */
    public AuthorModel create(String first_name, String last_name){
        AuthorModel newAuthor = new AuthorModel(this.getLastId()+1, first_name, last_name);
        this.binaryInsert(newAuthor);
        return newAuthor;
    }


    @Override
    void loadData() {
        // load all the information from the csv files in the folder database and store the results in memory.
        RetrieveData.loadAuthors(this);
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
            case "first_name":
                return getFirst_name();
            case "last_name":
                return getLast_name();
            default:
                return null;
        }
    }

    /**
     * Return name of the attributes of the class
     * @return name of attributes and its type of the class as an HashMap
     */
    @Override
    Map<String, String> getAttributes() {
        Map<String, String> attributes = new HashMap<>();

        for(Field field:AuthorModel.class.getDeclaredFields()){
            attributes.put(field.getName(), field.getType().getSimpleName());
        }
        return attributes;
    }

    @Override
    void save() {
        StoreData.saveAuthors(this.find());
    }

    @Override
    public String toCSV() {
        return this.id+","+this.first_name+","+this.last_name;
    }

    @Override
    int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getName(){
        return this.first_name + " " + this.last_name;
    }

    @Override
    public String toString() {
        return "ID: "+this.id+" - Name: "+this.first_name+" "+this.last_name;
    }
}
