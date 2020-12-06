package com.cct.database;

import com.cct.model.Model;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

/**
 * Class that creates csv files and store the result of ArrayLists of each model.
 */
@SuppressWarnings("Duplicates")
public class StoreData {

    /**
     * Save books to csv file using FileWriter.
     */
    public static void saveBooks(ArrayList<Model> books){
        String actualPath = "src/com/cct/database/books.csv";
        String tempPath = "src/com/cct/database/books.csv.temp";

        try {
            // create new temp file, in case something goes wrong while writing the file.
            FileWriter f = new FileWriter(tempPath);
            // write headers
            f.write("id,name,author_id,isbn");

            // write each book in the file
            for(Model book: books){
                f.write(System.lineSeparator());
                f.write(book.toCSV());
            }
            f.close();

            // Replace actual book file with the new one.
            Path source = Paths.get(tempPath);
            Path target = Paths.get(actualPath);
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            System.out.println("An error has occurred while saving the" +
                    " database internally in the files. (Books)");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Save authors to csv file using FileWriter.
     */
    public static void saveAuthors(ArrayList<Model> authors){
        String actualPath = "src/com/cct/database/authors.csv";
        String tempPath = "src/com/cct/database/authors.csv.temp";

        try {
            // create new temp file, in case something goes wrong while writing the file.
            FileWriter f = new FileWriter(tempPath);
            // write headers
            f.write("id,first_name,last_name");

            // write each book in the file
            for(Model author: authors){
                f.write(System.lineSeparator());
                f.write(author.toCSV());
            }
            f.close();

            // Replace actual book file with the new one.
            Path source = Paths.get(tempPath);
            Path target = Paths.get(actualPath);
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            System.out.println("An error has occurred while saving the " +
                    "database internally in the files. (Authors)");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Save readers to csv file using FileWriter.
     */
    public static void saveReaders(ArrayList<Model> readers){
        String actualPath = "src/com/cct/database/readers.csv";
        String tempPath = "src/com/cct/database/readers.csv.temp";

        try {
            // create new temp file, in case something goes wrong while writing the file.
            FileWriter f = new FileWriter(tempPath);
            // write headers
            f.write("id,name,email,address,phone");

            // write each book in the file
            for(Model reader: readers){
                f.write(System.lineSeparator());
                f.write(reader.toCSV());
            }
            f.close();

            // Replace actual book file with the new one.
            Path source = Paths.get(tempPath);
            Path target = Paths.get(actualPath);
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            System.out.println("An error has occurred while saving the " +
                    "database internally in the files. (Readers)");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Save borrowings to csv file using FileWriter.
     */
    public static void saveBorrowings(ArrayList<Model> borrowings){
        String actualPath = "src/com/cct/database/borrowings.csv";
        String tempPath = "src/com/cct/database/borrowings.csv.temp";

        try {
            // create new temp file, in case something goes wrong while writing the file.
            FileWriter f = new FileWriter(tempPath);
            // write headers
            f.write("id,book_id,reader_id,date_borrowed,date_returned,status");

            // write each book in the file
            for(Model borrowing: borrowings){
                f.write(System.lineSeparator());
                f.write(borrowing.toCSV());
            }
            f.close();

            // Replace actual book file with the new one.
            Path source = Paths.get(tempPath);
            Path target = Paths.get(actualPath);
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            System.out.println("An error has occurred while saving the " +
                    "database internally in the files. (Borrowings)");
            System.out.println(e.getMessage());
        }
    }
}
