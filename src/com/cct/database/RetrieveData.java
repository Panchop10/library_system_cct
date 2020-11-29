package com.cct.database;

import com.cct.model.Book;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class that reads csv files and store the result in ArrayLists of each object parsed by the model.
 */
public class RetrieveData {
    private ArrayList<Book> arrayBook = new ArrayList<>();

    public RetrieveData(){
        retrieveBooks();

    }

    /**
     * Reads books from csv file using FileReader and store the result in an ArrayList.
     * Throws an error if the file cannot be read or if a line does not match the model of the object.
     */
    private void retrieveBooks(){
        String text;
        FileReader f;
        int currentLine = 0;
        try {
            f = new FileReader("src/com/cct/database/books.csv");
            BufferedReader b = new BufferedReader(f);
            while((text = b.readLine())!=null) {
                // skip first line of the file that contains the headers.
                if (currentLine > 0){
                    // create array splitting the file by coma character then create the object.
                    String[] textBook = text.split(",");
                    Book newBook = new Book(
                            Integer.parseInt(textBook[0]),
                            textBook[1],
                            Integer.parseInt(textBook[2]),
                            textBook[3]
                    );
                    arrayBook.add(newBook);
                }
                currentLine++;
            }
            b.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("There was an error while loading database 'Books'");
        }
    }

    public ArrayList<Book> getBooks() {
        return arrayBook;
    }
}
