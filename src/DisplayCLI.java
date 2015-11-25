
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
                System.out.println("Verification sucessful! Proceeding to search:");
                manageFlight.searchFlight();
                manageFlight.showList();
            } else {
                run();
            }
            if(bookNext())
                run();
        } catch(Exception e) {
            System.out.println("CLI Mode error: "+e.getMessage());
            System.out.println("Escaping sequence");
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
                LocalDate departureDate = LocalDate.parse(s,manageFlight.getDtf());
                System.out.println("Please enter Source City (DELHI, MUMBAI or PUNE):");
                String city = reader.readLine().trim().toUpperCase();
                System.out.print("Destination is Singapore.\nHow many tickets?(1-10)\n");
                int numberOfTicket = Integer.parseInt(reader.readLine().trim());
                System.out.println("You have entered the following details:\n"
                        + "Departure City: " + city + "\n"
                        + "Departure Date: " + departureDate + "\n"
                        + "Number of Tickets: " + numberOfTicket + "\n"
                        + "Verifying..."
                );
                if(checkInput(departureDate, city, numberOfTicket)) {
                    manageFlight.setDepartDate(departureDate);
                    manageFlight.setSrc(city);
                    manageFlight.setTicketNo(numberOfTicket);
                }
                else {
                    System.out.println("Seems like there was an input error!"
                            + "\nPlease try again");
                    return false;
                }
            }
            return true;
        }
        catch(IOException | NumberFormatException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    /**
     * Method to verify inputs entered by the user
     * @param departureDate The departure date entered by the user
     * @param city The departure city entered by the user
     * @param numberOfTicket The number of tickets entered by the user
     * @return true if all inputs are valid, false otherwise
     */
    boolean checkInput(LocalDate departureDate, String city, int numberOfTicket){
        if(numberOfTicket > 10 || numberOfTicket < 1){
            System.out.println("Number of Passengers must be between 1-10");
            return false;
        }
        if(!city.equalsIgnoreCase("DELHI") && !city.equalsIgnoreCase("MUMBAI") && !city.equalsIgnoreCase("PUNE") ){
            System.out.println("No such source present.");
            return false;
        }
        if(departureDate.isBefore(manageFlight.firstDate)==true || departureDate.isAfter(manageFlight.lastDate)==true){
            System.out.println("Service available between 29-03-2015 to 24-10-2015.Please Re-enter:");
            return false;
        }
        return true;
    }  
}
