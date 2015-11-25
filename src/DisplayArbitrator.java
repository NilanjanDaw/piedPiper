
import logic.ManageFlight;
import UI.DisplayManager;
import data.DataManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nilanjan Daw
 * Abstract class for arbitrating between various display modes
 */
public abstract class DisplayArbitrator implements FRSInterface {
    protected DataManager dataManager;
    protected ManageFlight manageFlight;
    protected DisplayManager displayManager;
    
    /**
     * Constructor to initialise various data members
     * @param dataManager 
     * @param manageFlight
     * @param displayManager 
     */
    public DisplayArbitrator(DataManager dataManager, ManageFlight manageFlight, DisplayManager displayManager) {
        
       this.dataManager = dataManager;
       this.displayManager = displayManager;
       this.manageFlight = manageFlight;
    }
    /**
     * Abstract method to be implemented by children class
     * Will run the main display looper
     */
    @Override
    public abstract void run();
    /**
     * Abstract method to be implemented by children class
     * Will take input from respective display mode's console
     * @return true if a successful data input is taken
     */
    @Override
    public abstract boolean takeInput();
    
}
