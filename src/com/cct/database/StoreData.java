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
            System.out.println("An error has occurred while saving the database internally in the files.");
            System.out.println(e.getMessage());
        }
    }
}
