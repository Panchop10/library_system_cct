package com.cct.view;

import java.util.Scanner;

public class MenuView {
    public MenuView(){
        System.out.println("WELCOME TO CCT LIBRARY SYSTEM");
        System.out.println("");
        System.out.println("Select an option:");
        System.out.println("1 - Print books order alphabetically");
    }

    public int getUserOption(){
        Scanner myScanner = new Scanner(System.in);  // Create a Scanner object

        return Integer.parseInt(myScanner.nextLine());  // Read user input
    }
}
