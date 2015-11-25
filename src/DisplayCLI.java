
import logic.ManageFlight;
import UI.DisplayManager;
import data.DataManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nilanjan Daw
 * This class handles the CLI version of the project
 */
public class DisplayCLI extends DisplayArbitrator {

    BufferedReader reader;
    public DisplayCLI(DataManager dataManager, ManageFlight manageFlight, DisplayManager displayManager) {
        super(dataManager, manageFlight, displayManager);
        reader = new BufferedReader(new InputStreamReader(System.in));
    }
    
    /**
     * Checks if the user wants to book again
     * @return true if choice is true, else false
     */
    private boolean bookNext(){
        int ch;
        try{
            System.out.print("Please Selct your Choice:\n1.Book Another Ticket\n2.Exit\n");
            ch=Integer.parseInt(reader.readLine());
            return ch==1;
        }catch(IOException | NumberFormatException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    /**
     * The method to execute the CLI mode
     */
    @Override
    public void run() {
        try {
            if(takeInput()) {
                manageFlight.searchFlight();
                manageFlight.showList();
            }
            if(bookNext())
                run();
        } catch(Exception e) {
            System.out.println("CLI Mode error: "+e.getMessage());
        }
        
    }
    /**
     * Takes input from the user Console
     * @return true if a input is successful
     */
    @Override
    public boolean takeInput() {
        String s;
        try{
            System.out.println("Welcome to the Flight Reservation System!");
            System.out.println("-----------------------------------------");
            System.out.println("-----------------------------------------");
            System.out.println("Please enter the Date of Travel(yyyy-mm-dd):");
            s=reader.readLine();
            if(manageFlight != null) {
                manageFlight.setDepartDate(LocalDate.parse(s,manageFlight.getDtf()));
                System.out.println("Please enter Source City (DELHI, MUMBAI or PUNE):");
                manageFlight.setSrc(reader.readLine().trim().toUpperCase());
                System.out.print("Destination is Singapore.\nHow many tickets?(1-10)\n");
                manageFlight.setTicketNo(Integer.parseInt(reader.readLine().trim()));
            }
            return true;
        }
        catch(IOException | NumberFormatException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    
}
