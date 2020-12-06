package com.cct.database;

import com.cct.model.*;

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
            System.out.println(e.getMessage());
            System.out.println("There was an error while loading database 'Books'");
        }
    }

    /**
     * Reads authors from csv file using FileReader and store the result in an ArrayList.
     * Throws an error if the file cannot be read or if a line does not match the model of the object.
     */
    public static void loadAuthors(AuthorModel authorModel){
        ArrayList<Model> arrayAuthor = new ArrayList<>();

        String text;
        FileReader f;
        int currentLine = 0;
        try {
            f = new FileReader("src/com/cct/database/authors.csv");
            BufferedReader b = new BufferedReader(f);
            while((text = b.readLine())!=null) {
                // skip first line of the file that contains the headers.
                if (currentLine > 0){
                    // create array splitting the file by coma character then create the object.
                    String[] textAuthor = text.split(",");
                    AuthorModel newAuthor = AuthorModel.create(
                            Integer.parseInt(textAuthor[0]),
                            textAuthor[1],
                            textAuthor[2]
                    );
                    arrayAuthor.add(newAuthor);
                }
                currentLine++;
            }
            b.close();

            // add all the books to the programme memory from the files.
            authorModel.bulkInsert(arrayAuthor);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("There was an error while loading database 'Authors'");
        }
    }

    /**
     * Reads authors from csv file using FileReader and store the result in an ArrayList.
     * Throws an error if the file cannot be read or if a line does not match the model of the object.
     */
    public static void loadReaders(ReaderModel readerModel){
        ArrayList<Model> arrayReader = new ArrayList<>();

        String text;
        FileReader f;
        int currentLine = 0;
        try {
            f = new FileReader("src/com/cct/database/readers.csv");
            BufferedReader b = new BufferedReader(f);
            while((text = b.readLine())!=null) {
                // skip first line of the file that contains the headers.
                if (currentLine > 0){
                    // create array splitting the file by coma character then create the object.
                    String[] textReader = text.split(",");
                    ReaderModel newReader = ReaderModel.create(
                            Integer.parseInt(textReader[0]),
                            textReader[1],
                            textReader[2],
                            textReader[3],
                            textReader[4]
                    );
                    arrayReader.add(newReader);
                }
                currentLine++;
            }
            b.close();

            // add all the books to the programme memory from the files.
            readerModel.bulkInsert(arrayReader);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("There was an error while loading database 'Readers'");
        }
    }

    /**
     * Reads authors from csv file using FileReader and store the result in an ArrayList.
     * Throws an error if the file cannot be read or if a line does not match the model of the object.
     */
    public static void loadBorrowings(BorrowingModel borrowingModel){
        ArrayList<Model> arrayBorrowing = new ArrayList<>();

        String text;
        FileReader f;
        int currentLine = 0;
        try {
            f = new FileReader("src/com/cct/database/borrowings.csv");
            BufferedReader b = new BufferedReader(f);
            while((text = b.readLine())!=null) {
                // skip first line of the file that contains the headers.
                if (currentLine > 0){
                    // create array splitting the file by coma character then create the object.
                    String[] textBorrowing = text.split(",");
                    BorrowingModel newBorrowing = BorrowingModel.create(
                            Integer.parseInt(textBorrowing[0]),
                            Integer.parseInt(textBorrowing[1]),
                            Integer.parseInt(textBorrowing[2]),
                            textBorrowing[3],
                            textBorrowing[4],
                            textBorrowing[5]
                    );
                    arrayBorrowing.add(newBorrowing);
                }
                currentLine++;
            }
            b.close();

            // add all the books to the programme memory from the files.
            borrowingModel.bulkInsert(arrayBorrowing);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("There was an error while loading database 'Borrowings'");
        }
    }
}
