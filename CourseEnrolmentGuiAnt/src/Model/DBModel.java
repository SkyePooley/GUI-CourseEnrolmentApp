/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Observable;
import java.util.Scanner;

/**
 *
 * @author Skye 
 */
public class DBModel extends Observable {
    private DBManager dbManager;
    
    public DBModel() {
        this.dbManager = new DBManager();
    }
    
    public static void main(String[] args) {
        DBManager dbManager = new DBManager();
        Scanner scanner = new Scanner(System.in);
        String input = "";
        
        while (true) {
            System.out.println("SQL command or q to quit");
            input = scanner.nextLine();
            if (input.equals("q")) { break; }
            dbManager.update(input);
        }
    }
}
