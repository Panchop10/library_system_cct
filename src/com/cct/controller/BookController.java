package com.cct.controller;

import com.cct.model.Book;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookController {
    private static ArrayList<Book> books = new ArrayList<>();
    private static ArrayList<Book> booksRollBack = new ArrayList<>();

    /**
     * It performs a binary search by id.
     * @param id value of the id that is going to be searched
     * @return Book that matches the id or null of none was found.
     */
    public static Book findById(int id){
        return binarySearch(id);
    }

    /**
     * Find all the books in the database
     * @return The books in the database as an ArrayList
     */
    public static ArrayList<Book> find(){
        return books;
    }

    /**
     * Receives filters and returns all the books that matches with the filters in the database.
     * @param stringQuery is a Array of Strings with the filters example {"name: francisco", "author: 1"}
     * @return an ArrayList of books in the database that matched the filters
     */
    public static ArrayList<Book> find(String[] stringQuery){
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
            return filterBooks(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Please try again.");
            return null;
        }

    }

    /**
     * Inserts new book using binary insert
     * @param newBook new book that is going to be added
     */
    public static void insert(Book newBook){
        // store the current books in case a roll back must be perform by errors in the bulk insert.
        booksRollBack.addAll(books);

        // add the new book performing a binary search.
        binaryInsertBook(newBook);

        // check the integrity of the books, calling validateBook method.
        validateInsert();
    }

    /**
     * Inserts an ArrayList of Books to the database, if there is an error during the process, it will roll back the
     * database to the previous state.
     * @param newBooks ArrayList of new books to be added to the database.
     */
    public static void bulkInsert(ArrayList<Book> newBooks){
        // store the current books in case a roll back must be perform by errors in the bulk insert.
        booksRollBack.addAll(books);
        // add the books to the ArrayList and perform a quick sort by id.
        books.addAll(newBooks);
        quickSortBooks(0, books.size()-1, "id");

        // check the integrity of the books, calling validateBook method.
        validateInsert();
    }

    /**
     * Sorts the books by three different attributes "id", "name", "author"
     * @param attribute String "id", "name", "author"
     * @return ArrayList<Book> sorted
     */
    public static ArrayList<Book> sortBy(String attribute){
        booksRollBack = new ArrayList<>();
        booksRollBack.addAll(books);

        ArrayList<Book> tempBooks = new ArrayList<>();

        switch (attribute){
            case "name":
                quickSortBooks(0, books.size()-1, "name");
                tempBooks.addAll(books);
                books = new ArrayList<>();
                books.addAll(booksRollBack);
                booksRollBack = new ArrayList<>();
                return tempBooks;
            case "author":
                quickSortBooks(0, books.size()-1, "author");
                tempBooks.addAll(books);
                books = new ArrayList<>();
                books.addAll(booksRollBack);
                booksRollBack = new ArrayList<>();
                return tempBooks;
            default:
                return books;
        }
    }

    /**
     * Shows an error in case the last insert does not meet the validation and rolls back the database.
     */
    private static void validateInsert() {
        try {
            validateBooks();
            booksRollBack = new ArrayList<>();
        } catch (Exception e) {
            // rolls back the last bulkInsert
            books = new ArrayList<>();
            books.addAll(booksRollBack);
            booksRollBack = new ArrayList<>();
            System.out.println(e.getMessage());
            System.out.println("No books were inserted into the database. Please try again.");
        }
    }

    /**
     * Find if there is a duplicated id in the ArrayList in that case, throws an error.
     * Also checks if all the IDs are positive integers, otherwise throws an error.
     * In order to perform the validation, the ArrayList must be sorted by id.
     */
    private static void validateBooks() throws Exception{
        int lastId = 0;
        for(Book book: books){
            if(book.getId() <= 0){
                throw new Exception("***ID has to be a positive integer*** - " + book);
            }
            if(book.getId() == lastId){
                throw new Exception("***Duplicated ID*** - " + book);
            }

            // store last id
            lastId = book.getId();
        }
    }

    /**
     * Performs a binary search in the ArrayList books.
     * This code was provided by Amilcar Aponte in Data Structures and Algorithms Course (Binary search)
     * and modified by Francisco Olivares to return an object instead of the position.
     * @param idBook id that is going to be searched in the ArrayList
     * @return Book that matches the id searched or null if no book was found.
     */
    private static Book binarySearch(int idBook){

        // Keeping track of the indexes. These will define the limits
        // between which we'll be looking for the value
        int low = 0;
        int high = books.size() - 1;

        // The search will happen as long a the low index remains
        // lower than the high index
        while(low <= high){

            // Looking for the middle index
            int mid = (high + low)/2;

            // Assessing the value in the middle index
            if(books.get(mid).getId() == idBook){
                return books.get(mid);
            }
            else if(books.get(mid).getId() > idBook){
                high = mid - 1;
            }
            else {
                low = mid + 1;
            }
        }
        return null;
    }

    /**
     * It performs a binary search to add the book in the right position according to its id.
     * @param newBook receive the new book to be added to the database.
     */
    private static void binaryInsertBook(Book newBook){
        // Add book straightaway if there are no more books in the database.
        if(books.size() == 0){
            books.add(newBook);
        }

        // If there is just one book in the database, insert the new one at the beginning
        // or at the end depending on its id
        else if (books.size() == 1){
            int highestId = books.get(0).getId();

            if (newBook.getId() >= highestId){
                books.add(newBook);
            }
            else {
                Book tempBook = books.get(0);
                books.set(0, newBook);
                books.add(tempBook);
            }
        }
        // If there are two or more books in the database, perform a binary insert.
        // This code was provided by Amilcar Aponte in Data Structures and Algorithms Course (Binary search)
        // and modified by Francisco Olivares.
        else {
            int low = 0;
            int high = books.size() - 1;

            while(true){
                // Looking for the middle index
                int mid = (high + low)/2;

                // if the high element is <= zero, then the element is added at the beginning of the ArrayList.
                if (high <= 0){
                    ArrayList<Book> tempArray = new ArrayList<>();
                    tempArray.add(newBook);
                    tempArray.addAll(books);

                    books = new ArrayList<>();
                    books.addAll(tempArray);
                    break;
                }
                // if pivot is in the last element of the ArrayList, the new object is added at the end.
                else if (mid >= books.size() - 1){
                    books.add(newBook);
                    break;
                }
                // if new element is between two elements of the ArrayList, then it is added in that position
                else if (books.get(mid).getId() <= newBook.getId() && newBook.getId() <= books.get(mid+1).getId()){
                    ArrayList<Book> tempArray = new ArrayList<>(books.subList(0, mid+1));
                    tempArray.add(newBook);
                    tempArray.addAll(books.subList(mid+1, books.size()));

                    books = new ArrayList<>();
                    books.addAll(tempArray);
                    break;
                }
                // if the element is smaller than the mid element, then the new high is the mid minus one.
                else if(books.get(mid).getId() > newBook.getId()){
                    high = mid - 1;
                }
                // if the element is bigger than the mid element, then the new low is the mid plus one.
                else {
                    low = mid + 1;
                }

            }

        }
    }

    /**
     * Performs a quick sort to the ArrayList of Books.
     * This code was provided by Amilcar Aponte in Data Structures and Algorithms Course
     * and modified by Francisco Olivares.
     * @param start lowest position of the array that is going to be sorted
     * @param end highest position of the array that is going to be sorted
     */
    private static void quickSortBooks(int start, int end, String attribute){
        if(start < end){
            int pivotIndex = partitionBooks(start, end, attribute);

            quickSortBooks(start, pivotIndex - 1, attribute);

            quickSortBooks(pivotIndex + 1, end, attribute);
        }
    }

    /**
     * Second part of the quick sort algorithm described above.
     * @param low lowest position of the array where the algorithm should find a pivot and sort.
     * @param high highest position of the array where the algorithm should find a pivot and sort.
     * @return position of the last element sorted int the array
     */
    private static int partitionBooks(int low, int high, String attribute) {
        int pivotInteger = 0;
        String pivotString = "";

        switch (attribute){
            case "id":
                pivotInteger = books.get(high).getId();
                break;
            case "name":
                pivotString = books.get(high).getName();
                break;
            case "author":
                pivotString = books.get(high).getAuthorName();
                break;
            default:
                System.out.println("Attribute not sortable: " + attribute);
                break;
        }
        int i = low - 1;
        Book bookTemp;

        label:
        for (int j = low; j < high; j++){
            switch (attribute) {
                case "id":
                    if (books.get(j).getId() <= pivotInteger) {
                        i++;
                        bookTemp = books.get(i);
                        books.set(i, books.get(j));
                        books.set(j, bookTemp);
                    }
                    break;
                case "name":
                    if (books.get(j).getName().compareTo(pivotString) <= 0) {
                        i++;
                        bookTemp = books.get(i);
                        books.set(i, books.get(j));
                        books.set(j, bookTemp);
                    }
                    break;
                case "author":
                    if (books.get(j).getAuthorName().compareTo(pivotString) <= 0) {
                        i++;
                        bookTemp = books.get(i);
                        books.set(i, books.get(j));
                        books.set(j, bookTemp);
                    }
                    break;
                default:
                    break label;
            }
        }

        bookTemp = books.get(i+1);
        books.set(i+1, books.get(high));
        books.set(high, bookTemp);

        return i+1;
    }

    /**
     * Filters the ArrayList of books based on the parameters sent
     * @param filters filters as map
     * @return ArrayList of books filtered
     * @throws Exception if they key of the filters is not present in the model.
     */
    private static ArrayList<Book> filterBooks(Map<String, String> filters) throws Exception{
        ArrayList<Book> filteredBooks = new ArrayList<>();

        // validate filters throws an exception if a filter is not found in the model.
        validateFilters(filters);

        // perform a linear search to filter books.
        for (Book book: books) {
            boolean matchFilter = true;
            // iterate over the filters given by the user
            for (Map.Entry<String, String> filter : filters.entrySet()) {
                // iterate over the methods of the class to find the one that returns the value of the filter
                for (Method method : book.getClass().getDeclaredMethods()) {
                    // compare the name of the method with the key of the filter
                    if (method.getName().equalsIgnoreCase("get" + filter.getKey())) {
                        // compare the result of the method with the value of the filter
                        if (!method.invoke(book).toString().toLowerCase().contains(filter.getValue().toLowerCase())) {
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
                filteredBooks.add(book);
            }
        }
        return filteredBooks;
    }


    /**
     * Validate that all the attributes in the filters exist in the model.
     * @param filters as Map<String,String>
     * @throws Exception if a filter is not found in the model.
     */
    private static void validateFilters(Map<String, String> filters) throws Exception{
        // iterate over the filters
        for(Map.Entry<String, String> filter: filters.entrySet()){
            boolean found = false;
            // iterate over the attributes of the book
            for(Map.Entry<String, String> bookAttribute: Book.getAttributes().entrySet()){
                // compare filter key with attribute of the book
                if(bookAttribute.getKey().equals(filter.getKey())){
                    found = true;
                    break;
                }
            }
            // throw an error if the attribute was not found
            if (!found){
                throw new Exception("Attribute not found in the class Book: " + filter.getKey());
            }
        }
    }
}
