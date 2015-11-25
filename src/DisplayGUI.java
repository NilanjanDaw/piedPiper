
import logic.ManageFlight;
import UI.DisplayManager;
import data.DataManager;
import data.Flight;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nilanjan Daw
 * This class handles the GUI version of the project
 */
public class DisplayGUI extends DisplayArbitrator{
    
    private int choice;
    private DataManager datMan=new DataManager();
    private ManageFlight manFlg=new ManageFlight();
    private DisplayManager disMan= new DisplayManager();
    
    /**
     * Constructor to initialise class variables
     * @param dataManager
     * @param manageFlight
     * @param displayManager 
     */
    public DisplayGUI(DataManager dataManager, ManageFlight manageFlight, DisplayManager displayManager) {    
        super(dataManager, manageFlight, displayManager);
        datMan.init(FRSManager.path);
        manFlg.init(datMan);
    }
    /**
     * Method to execute the GUI mode
     */
    @Override
    public void run() {
        try {
            displayManager.showScreen();
            if(takeInput()) {
                manageFlight.searchFlight();    //searching for flights
                String[][] searchData = new String[manageFlight.getFlightFoundSilk().size()][7];
                Flight[] spiceSearch = new Flight[manageFlight.getFlightFoundSpice().size()];
                manageFlight.getFlightFoundSpice().toArray(spiceSearch);
                Flight[] silkSearch = new Flight[manageFlight.getFlightFoundSilk().size()];
                manageFlight.getFlightFoundSilk().toArray(silkSearch);
                /**
                 * Parsing data received from ManageFlight so that it can be properly fed into the GUI
                 */
                for(int i = 0;i <searchData.length; i++) {
                    searchData[i][0] = spiceSearch[i].getDepartureCity() + " (" + spiceSearch[i].getFlightNumber() + ")";
                    searchData[i][1] = spiceSearch[i].getDepartureTime().toString();
                    searchData[i][2] = spiceSearch[i].getArrivalTime().toString();
                    if(spiceSearch[i].getVia() != null)
                        for (String via : spiceSearch[i].getVia()) {
                            searchData[i][3] += ", "+via;
                        }
                    else
                        searchData[i][3] = "-";
                    searchData[i][4] = silkSearch[i].getDepartureCity() + " (" + silkSearch[i].getFlightNumber() + ")";
                    searchData[i][5] = silkSearch[i].getDepartureTime().toString();
                    searchData[i][6] = silkSearch[i].getArrivalTime().toString();
                }
                /**
                 * Get user choice and confirm booking if choices are present else 
                 * recreate the windows
                 */
                if(displayManager.showSearchResult(searchData)) {
                    choice = getChoice();
                    System.out.println("Choice " + choice);
                    confirm();
                    manageFlight.confirmBook(choice, 1);
                }else {
                    DisplayArbitrator displayArbitrator = new DisplayGUI(datMan, manFlg, disMan);
                    displayArbitrator.run();
                }
            }
        }catch(NullPointerException e) {
            System.err.println("Null");
        }catch(Exception e) {
            System.out.println("runGUI " + e.getMessage());
        }
    }
    /**
     * Method to get user choice
     * @return 
     */
    private int getChoice() {
        while(!displayManager.isChoiceMarked()) {
            System.out.println("Choice not marked");
        }
        return displayManager.getChoice();
    }
    /**
     * Method to confirm user choice
     */
    public void confirm() {
        displayManager.showNextTab(2);
        /**
         * Confirmation block will be enabled later
         */
        while(!displayManager.isConfirmed()) {
            System.out.println("Not Confirmed");
        }//*/
        displayManager.closeScreen();
    }
    
    /**
     * Method to take input from user to be fed to ManageFlight
     * @return true if input process completes successfully
     */
    @Override
    public boolean takeInput() {
        try {
            while(true) {
                System.out.println(displayManager.isRead());
                if(displayManager.isRead())
                    break;
            }
            displayManager.getData();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = dateFormat.format(displayManager.getDate().getTime());
            manageFlight.setDepartDate(LocalDate.parse(date,manageFlight.getDtf()));
            manageFlight.setSrc(displayManager.getSource().trim().toUpperCase());
            manageFlight.setTicketNo(displayManager.getNumberOfSeats());
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
}
