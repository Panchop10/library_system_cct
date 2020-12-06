package com.cct.model;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Model {
    private ArrayList<Model> db = new ArrayList<>();
    private ArrayList<Model> dbRollBack = new ArrayList<>();

    /**
     * Abstract class method to ensure that every class implements the method load data.
     */
     abstract void loadData();

    /**
     * Abstract method to ensure that the method getId exists
     * @return ID of the element.
     */
    abstract int getId();

    /**
     * Abstract method to ensure the creation of a general getter in each element.
     * @param attribute that is going to be gotten from the element
     * @return String with the value of the attribute.
     */
    abstract String get(String attribute);

    /**
     * Return name of the attributes of the class
     * @return name of attributes and its type of the class as an HashMap
     */
    abstract Map<String, String> getAttributes();

    /**
     * Abstract method to save the elements of the model in csv files.
     */
    abstract void save();

    /**
     * Abstract class method to ensure that each model has a method that converts the model to csv line.
     * @return the model as csv
     */
    abstract public String toCSV();

    /**
     * Inserts an ArrayList of Elements to the database, if there is an error during the process, it will roll back the
     * database to the previous state.
     * @param newElements ArrayList of new elements to be added to the database.
     */
    public void bulkInsert(ArrayList<Model> newElements){
        // store the current books in case a roll back must be perform by errors in the bulk insert.
        dbRollBack.addAll(db);
        // add the books to the ArrayList and perform a quick sort by id.
        db.addAll(newElements);
        quickSortElements(0, db.size()-1, "id");

        // check the integrity of the books, calling validateBook method.
        validateInsert();

        // store new elements in text files
        save();
    }

    /**
     * It performs a binary search by id.
     * @param id value of the id that is going to be searched
     * @return Book that matches the id or null of none was found.
     */
    public Model findById(int id){
        return binarySearch(id);
    }

    /**
     * Find all the books in the database
     * @return The books in the database as an ArrayList
     */
    public ArrayList<Model> find(){
        return db;
    }

    /**
     * Receives filters and returns all the books that matches with the filters in the database.
     * @param stringQuery is a Array of Strings with the filters example {"name: francisco", "author: 1"}
     * @return an ArrayList of books in the database that matched the filters
     */
    public ArrayList<Model> find(String[] stringQuery){
        // Create a HashMap to store the pair Key - Value of the filters.
        Map<String, String> query = new HashMap<>();

        // iterate over the array of strings query
        for(String str : stringQuery){
            // create array of string by using split with the char ":"
            String[] keyValuePair = str.split(":");

            // put the key and value in the HashMap removing blank spaces at the beginning and at the end
            query.put(keyValuePair[0].trim(), keyValuePair[1].trim());
        }

        // return filtered books, if there is an error (like the key sent is not present in the model),
        // returns null, if there are no matches, it returns an empty ArrayList of Books
        try {
            return filterElements(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Please try again.");
            return null;
        }

    }

    /**
     * Updates one or more elements
     * @param stringQuery Filter which books are going to be updated
     * @param stringUpdate New parameters
     * @return ArrayList of Model with the elements updated.
     */
    public ArrayList<Model> update(String[] stringQuery, String[] stringUpdate){
        // Create a HashMap to store the pair Key - Value of the filters.
        Map<String, String> query = new HashMap<>();
        Map<String, String> updateValues = new HashMap<>();

        // iterate over the array of strings query
        for(String str : stringQuery){
            // create array of string by using split with the char ":"
            String[] keyValuePair = str.split(":");

            // put the key and value in the HashMap removing blank spaces at the beginning and at the end
            query.put(keyValuePair[0].trim(), keyValuePair[1].trim());
        }

        // iterate over the array of strings stringUpdate
        for(String str : stringUpdate){
            // create array of string by using split with the char ":"
            String[] keyValuePair = str.split(":");

            // put the key and value in the HashMap removing blank spaces at the beginning and at the end
            updateValues.put(keyValuePair[0].trim(), keyValuePair[1].trim());
        }

        try {
            ArrayList<Model> updatedElements = updateElements(query, updateValues);

            // store new elements in text files
            save();

            // return updated elements
            return updatedElements;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Please try again.");
            return new ArrayList<>();
        }
    }

    /**
     * Removes all the elements in the database that match the attributes sent.
     * @param stringQuery is a Array of Strings with the filters example {"name: francisco", "author: 1"}
     */
    public void remove(String[] stringQuery){
        // Create a HashMap to store the pair Key - Value of the filters.
        Map<String, String> query = new HashMap<>();

        // iterate over the array of strings query
        for(String str : stringQuery){
            // create array of string by using split with the char ":"
            String[] keyValuePair = str.split(":");

            // put the key and value in the HashMap removing blank spaces at the beginning and at the end
            query.put(keyValuePair[0].trim(), keyValuePair[1].trim());
        }

        // throws an error if an attribute in the filter is not present in the model.
        try {
            removeElements(query);

            // store new elements in text files
            save();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Please try again.");
        }

    }

    /**
     * Get last id stored in the database
     * @return bigger id of the database as integer
     */
    int getLastId(){
        return db.get(db.size()-1).getId();
    }

    /**
     * Sorts the books by three different attributes "id", "name", "author"
     * @param attribute String "id", "name", "author"
     * @return ArrayList<Model> sorted
     */
    public ArrayList<Model> sortBy(String attribute){
        dbRollBack = new ArrayList<>();
        dbRollBack.addAll(db);

        if(attribute.equals("id")){
            dbRollBack = new ArrayList<>();
            return db;
        }
        else{
            quickSortElements(0, db.size()-1, attribute);
            ArrayList<Model> tempElements = new ArrayList<>(db);
            db = new ArrayList<>();
            db.addAll(dbRollBack);
            dbRollBack = new ArrayList<>();
            return tempElements;
        }
    }

    /**
     * Filters the ArrayList of books based on the parameters sent then remove them from the database.
     * @param filters filters as map
     * @throws Exception if they key of the filters is not present in the model.
     */
    @SuppressWarnings("Duplicates")
    private void removeElements(Map<String, String> filters) throws Exception{
        ArrayList<Model> filteredElements = new ArrayList<>();

        // validate filters throws an exception if a filter is not found in the model.
        validateFilters(filters);

        // perform a linear search to filter books.
        for (Model element: db) {
            boolean matchFilter = true;
            // iterate over the filters given by the user
            for (Map.Entry<String, String> filter : filters.entrySet()) {
                // iterate over the methods of the class to find the one that returns the value of the filter
                for (Method method : element.getClass().getDeclaredMethods()) {
                    // compare the name of the method with the key of the filter
                    if (method.getName().equalsIgnoreCase("get" + filter.getKey())) {
                        // compare the result of the method with the value of the filter
                        if (!method.invoke(element).toString().toLowerCase().contains(filter.getValue().toLowerCase())) {
                            matchFilter = false;
                            break;
                        }
                    }
                }
                // stop searching through the filters if the first one did not match with the book.
                if (!matchFilter) {
                    break;
                }
            }

            // if the book matches the filters, add it to the list of books to delete
            if (matchFilter) {
                filteredElements.add(element);
            }
        }

        // remove books from the database (ArrayList)
        for (Model element: filteredElements){
            db.remove(element);
        }
    }

    /**
     * Perform the update in the database (ArrayList)
     * @param filters which elements are going to be updated
     * @param newValues new value of the fields to update
     * @return books filtered
     * @throws Exception if the filter does not exist in the model
     */
    private ArrayList<Model> updateElements(Map<String, String> filters, Map<String, String> newValues) throws Exception{
        // get elements filtered, it throws an error if the filter does not exist in the model
        ArrayList<Model> filteredElements = filterElements(filters);

        for (Model element: filteredElements) {
            // iterate over the filters given by the user
            for (Map.Entry<String, String> newValue : newValues.entrySet()) {
                // iterate over the methods of the class to find the one that returns the value of the filter
                for (Method method : element.getClass().getDeclaredMethods()) {
                    // compare the name of the method with the key of the filter
                    if (method.getName().equalsIgnoreCase("set" + newValue.getKey())) {
                        // invoke set method with new value.
                        method.invoke(element, newValue.getValue());
                        break;
                    }
                }
            }
        }
        return filteredElements;
    }

    /**
     * Shows an error in case the last insert does not meet the validation and rolls back the database.
     */
    private void validateInsert() {
        try {
            validateElements();
            dbRollBack = new ArrayList<>();
        } catch (Exception e) {
            // rolls back the last bulkInsert
            db = new ArrayList<>();
            db.addAll(dbRollBack);
            dbRollBack = new ArrayList<>();
            System.out.println(e.getMessage());
            System.out.println("No elements were inserted into the database. Please try again.");
        }
    }

    /**
     * Validate that all the attributes in the filters exist in the model.
     * @param filters as Map<String,String>
     * @throws Exception if a filter is not found in the model.
     */
    private void validateFilters(Map<String, String> filters) throws Exception{
        // iterate over the filters
        for(Map.Entry<String, String> filter: filters.entrySet()){
            boolean found = false;
            // iterate over the attributes of the book
            for(Map.Entry<String, String> elementAttribute: this.getAttributes().entrySet()){
                // compare filter key with attribute of the model
                if(elementAttribute.getKey().equals(filter.getKey())){
                    found = true;
                    break;
                }
            }
            // throw an error if the attribute was not found
            if (!found){
                throw new Exception("Attribute not found in the class: " + filter.getKey());
            }
        }
    }

    /**
     * Find if there is a duplicated id in the ArrayList in that case, throws an error.
     * Also checks if all the IDs are positive integers, otherwise throws an error.
     * In order to perform the validation, the ArrayList must be sorted by id.
     */
    private void validateElements() throws Exception{
        int lastId = 0;
        for(Model element: db){
            if(element.getId() <= 0){
                throw new Exception("***ID has to be a positive integer*** - " + element);
            }
            if(element.getId() == lastId){
                throw new Exception("***Duplicated ID*** - " + element);
            }

            // store last id
            lastId = element.getId();
        }
    }

    /**
     * Filters the ArrayList of elements based on the parameters sent
     * @param filters filters as map
     * @return Database filtered as an ArrayList
     * @throws Exception if they key of the filters is not present in the model.
     */
    @SuppressWarnings("Duplicates")
    private ArrayList<Model> filterElements(Map<String, String> filters) throws Exception{
        ArrayList<Model> filteredElements = new ArrayList<>();

        // validate filters throws an exception if a filter is not found in the model.
        validateFilters(filters);

        // perform a linear search to filter books.
        for (Model element: db) {
            boolean matchFilter = true;
            // iterate over the filters given by the user
            for (Map.Entry<String, String> filter : filters.entrySet()) {
                // iterate over the methods of the class to find the one that returns the value of the filter
                for (Method method : element.getClass().getDeclaredMethods()) {
                    // compare the name of the method with the key of the filter
                    if (method.getName().equalsIgnoreCase("get" + filter.getKey())) {
                        // compare the result of the method with the value of the filter
                        if (!method.invoke(element).toString().toLowerCase().contains(filter.getValue().toLowerCase())) {
                            matchFilter = false;
                            break;
                        }
                    }
                }
                // stop searching through the filters if the first one did not match with the book.
                if (!matchFilter) {
                    break;
                }
            }

            if (matchFilter) {
                filteredElements.add(element);
            }
        }
        return filteredElements;
    }

    /**
     * Performs a binary search in the ArrayList books.
     * This code was provided by Amilcar Aponte in Data Structures and Algorithms Course (Binary search)
     * and modified by Francisco Olivares to return an object instead of the position.
     * @param idElement id that is going to be searched in the ArrayList
     * @return Model that matches the id searched or null if no book was found.
     */
    private Model binarySearch(int idElement){

        // Keeping track of the indexes. These will define the limits
        // between which we'll be looking for the value
        int low = 0;
        int high = db.size() - 1;

        // The search will happen as long a the low index remains
        // lower than the high index
        while(low <= high){

            // Looking for the middle index
            int mid = (high + low)/2;

            // Assessing the value in the middle index
            if(db.get(mid).getId() == idElement){
                return db.get(mid);
            }
            else if(db.get(mid).getId() > idElement){
                high = mid - 1;
            }
            else {
                low = mid + 1;
            }
        }
        return null;
    }

    /**
     * It performs a binary search to add the element in the right position according to its id.
     * @param newElement receive the new element to be added to the database.
     */
     void binaryInsert(Model newElement){
        // Add book straightaway if there are no more elements in the database.
        if(db.size() == 0){
            db.add(newElement);
        }

        // If there is just one element in the database, insert the new one at the beginning
        // or at the end depending on its id
        else if (db.size() == 1){
            int highestId = db.get(0).getId();

            if (newElement.getId() >= highestId){
                db.add(newElement);
            }
            else {
                Model tempBook = db.get(0);
                db.set(0, newElement);
                db.add(tempBook);
            }
        }
        // If there are two or more elements in the database, perform a binary insert.
        // This code was provided by Amilcar Aponte in Data Structures and Algorithms Course (Binary search)
        // and modified by Francisco Olivares.
        else {
            int low = 0;
            int high = db.size() - 1;

            while(true){
                // Looking for the middle index
                int mid = (high + low)/2;

                // if the high element is <= zero, then the element is added at the beginning of the ArrayList.
                if (high <= 0){
                    ArrayList<Model> tempArray = new ArrayList<>();
                    tempArray.add(newElement);
                    tempArray.addAll(db);

                    db = new ArrayList<>();
                    db.addAll(tempArray);
                    break;
                }
                // if pivot is in the last element of the ArrayList, the new object is added at the end.
                else if (mid >= db.size() - 1){
                    db.add(newElement);
                    break;
                }
                // if new element is between two elements of the ArrayList, then it is added in that position
                else if (db.get(mid).getId() <= newElement.getId() && newElement.getId() <= db.get(mid+1).getId()){
                    ArrayList<Model> tempArray = new ArrayList<>(db.subList(0, mid+1));
                    tempArray.add(newElement);
                    tempArray.addAll(db.subList(mid+1, db.size()));

                    db = new ArrayList<>();
                    db.addAll(tempArray);
                    break;
                }
                // if the element is smaller than the mid element, then the new high is the mid minus one.
                else if(db.get(mid).getId() > newElement.getId()){
                    high = mid - 1;
                }
                // if the element is bigger than the mid element, then the new low is the mid plus one.
                else {
                    low = mid + 1;
                }
            }
        }
        // save new info in text files.
        save();
    }

    /**
     * Performs a quick sort to the database (ArrayList of Elements).
     * This code was provided by Amilcar Aponte in Data Structures and Algorithms Course
     * and modified by Francisco Olivares.
     * @param start lowest position of the array that is going to be sorted
     * @param end highest position of the array that is going to be sorted
     */
    private void quickSortElements(int start, int end, String attribute){
        if(start < end){
            int pivotIndex = partitionElements(start, end, attribute);

            quickSortElements(start, pivotIndex - 1, attribute);

            quickSortElements(pivotIndex + 1, end, attribute);
        }
    }

    /**
     * Second part of the quick sort algorithm described above.
     * @param low lowest position of the array where the algorithm should find a pivot and sort.
     * @param high highest position of the array where the algorithm should find a pivot and sort.
     * @return position of the last element sorted int the array
     */
    private int partitionElements(int low, int high, String attribute) {
        int pivotInteger = 0;
        String pivotString = "";

        //Get pivot from the element, if it is the id, will use the integer pivot, otherwise the string pivot.
        if ("id".equals(attribute)) {
            pivotInteger = db.get(high).getId();
        } else {
            pivotString = db.get(high).get(attribute);
        }


        int i = low - 1;
        Model elementTemp;

        for (int j = low; j < high; j++){
            if ("id".equals(attribute)) {
                if (db.get(j).getId() <= pivotInteger) {
                    i++;
                    elementTemp = db.get(i);
                    db.set(i, db.get(j));
                    db.set(j, elementTemp);
                }
            } else {
                if (db.get(j).get(attribute).compareTo(pivotString) <= 0) {
                    i++;
                    elementTemp = db.get(i);
                    db.set(i, db.get(j));
                    db.set(j, elementTemp);
                }
            }
        }

        elementTemp = db.get(i+1);
        db.set(i+1, db.get(high));
        db.set(high, elementTemp);

        return i+1;
    }
}
