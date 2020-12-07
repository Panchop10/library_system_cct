package com.cct;

import com.cct.controller.MainController;
import com.cct.model.*;
import com.cct.view.MenuView;


public class Main {

    public static void main(String[] args) {

        // initialize all the models, load info from text files and pass models to set relationships.
        AuthorModel authorModel = new AuthorModel();
        ReaderModel readerModel = new ReaderModel();
        BorrowingModel borrowingModel = new BorrowingModel(readerModel);
        BookModel bookModel = new BookModel(authorModel, borrowingModel);

        // Create borrowingModel many to many relationship with bookModel
        borrowingModel.setRelationBook(bookModel);
        for (Model borrowing: borrowingModel.find()){
            borrowing.setRelationBook(bookModel);
        }

        // create main controller with all the models loaded.
        MainController mainController = new MainController(authorModel, readerModel, borrowingModel, bookModel);

        // display menu for user.
        new MenuView(mainController);

    }
}
