/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nilanjan Daw
 */
import logic.ManageFlight;
import UI.*;
import data.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
public class FRSManager {
    /**
     * Class variables
     * Containing the required class objects
     */
    public static String[] path;        //static variable for data file path in GUI
    private DataManager dataManager;    //DataManager class instance
    private ManageFlight manageFlight;  //ManageFlight class instance
    private DisplayManager displayManager;  //DisplayManager class instance
    private int choice; //integer variable to take user choice of CLI or GUI mode
    BufferedReader reader;  //Buffered Reader class instance
    
    /**
     * Initialiser method to instantiate various member classes
     * @param args Contains the path to the database files
     */
    private void init(String[] args) {
        path=args;
        reader = new BufferedReader(new InputStreamReader(System.in));
        dataManager = new DataManager();
        dataManager.init(args);
        manageFlight = new ManageFlight();
        manageFlight.init(dataManager);
        displayManager = new DisplayManager();
        
    }
    
    /**
     * The main method that starts the program execution
     * @param args Takes the runtime arguments passed by user
     */
    public static void main(String[] args) {
        try {
            if(args.length != 2) {
            System.out.println("Database files input error\nExiting");
            return;
            }
            FRSManager manager = new FRSManager();
            DisplayArbitrator arbitrator;
            manager.init(args);
            System.out.println("Enter 1 to start CLI version or 2 to start GUI version");
            manager.choice = Integer.parseInt(manager.reader.readLine());
            switch(manager.choice) {
                case 1: arbitrator = new DisplayCLI(manager.dataManager, 
                                manager.manageFlight, 
                                manager.displayManager);
                    arbitrator.run();
                    break;
                case 2: arbitrator = new DisplayGUI(manager.dataManager, 
                                manager.manageFlight, 
                                manager.displayManager);
                    arbitrator.run();
                    break;
                default: System.out.println("Wrong Choice!");
            }
            
        } catch (IOException | NumberFormatException ex) {
            System.out.println("Seems like an input error just occured! Shutting systems down");
        } catch (Exception e) {
            System.out.println("Seems like an unexpected error just occured! Shutting systems down");
        }
    }

}
