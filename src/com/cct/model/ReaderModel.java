package com.cct.model;

import com.cct.database.RetrieveData;
import com.cct.database.StoreData;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ReaderModel extends Model {
    private int id;
    private String name;
    private String email;
    private String address;
    private String phone;

    // Load info from the csv files when the class is instantiated.
    public ReaderModel(){ loadData(); }

    // Private constructor that can be invoked by the create method.
    private ReaderModel(int id, String name, String email, String address, String phone){
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    /**
     * Create method that returns a new reader with an id by default **It won't save it in the database**
     * This reader will have to be added manually to the database later.
     * @param name String with the name of the reader
     * @param email String with the email of the reader
     * @param address String with the address of the reader
     * @param phone String with the phone of the reader
     * @return the new reader with the values given.
     */
    public static ReaderModel create(int id, String name, String email, String address, String phone) {
        return new ReaderModel(id, name, email, address, phone);
    }

    /**
     * Generates a new id and add the new reader to the database.
     * @param name String with the name of the reader
     * @param email String with the email of the reader
     * @param address String with the address of the reader
     * @param phone String with the phone of the reader
     * @return the new reader that was stored in the database.
     */
    public ReaderModel create(String name, String email, String address, String phone){
        ReaderModel newReader = new ReaderModel(this.getLastId()+1, name, email, address, phone);
        this.binaryInsert(newReader);
        return newReader;
    }

    @Override
    void loadData() {
        // load all the information from the csv files in the folder database and store the results in memory.
        RetrieveData.loadReaders(this);
    }

    @Override
    public String get(String attribute) {
        switch (attribute){
            case "id":
                return String.valueOf(getId());
            case "name":
                return getName();
            case "email":
                return getEmail();
            case "address":
                return getAddress();
            case "phone":
                return getPhone();
            default:
                return null;
        }
    }

    @Override
    Map<String, String> getAttributes() {
        Map<String, String> attributes = new HashMap<>();

        for(Field field:ReaderModel.class.getDeclaredFields()){
            attributes.put(field.getName(), field.getType().getSimpleName());
        }
        return attributes;
    }

    @Override
    void save() {
        StoreData.saveReaders(this.find());
    }

    @Override
    int getId() {
        return this.id;
    }

    @Override
    public String toCSV() {
        return this.id+","+this.name+","+this.email+","+this.address+","+this.phone;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "ID: "+this.id+" - Name: "+this.name+" - Email: "+this.email+" " +
                "- Address: "+this.address+" - Phone: "+this.phone;
    }
}
