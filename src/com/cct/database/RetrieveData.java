package com.cct.database;

import com.cct.model.BookModel;
import com.cct.model.Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class that reads csv files and store the result in ArrayLists of each object parsed by the model.
 */
public class RetrieveData {
    /**
     * Reads books from csv file using FileReader and store the result in an ArrayList.
     * Throws an error if the file cannot be read or if a line does not match the model of the object.
     */
    public static void loadBooks(BookModel bookModel){
        ArrayList<Model> arrayBook = new ArrayList<>();

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
                    BookModel newBook = BookModel.create(
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

            // add all the books to the programme memory from the files.
            bookModel.bulkInsert(arrayBook);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("There was an error while loading database 'Books'");
        }
    }
}
