/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.util.Calendar;
import javax.swing.JOptionPane;

/**
 * Class to manage the display sequence of screen/tabs in form Screen
 * @author Anudeep Ghosh
 */
public class DisplayManager implements DisplayInterface {
    private String source,destination;
    private int numberOfSeats;
    private Calendar date;
    private Object[] objects;
    boolean read = false;
    boolean choiceMarked = false;
    boolean confirm = false;
    boolean state = false;
    Screen scr;
    /**
     * DisplayManager default constructor
     * creates and initialises the screen object
     */
    public DisplayManager() {
        scr = new Screen(this);
    }
    /**
     * Method to Display info messages
     * @param infoMessage Message to display
     * @param titleBar    Title bar information
     */
    public static void infoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: "+titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
    /**
     * 
     * Method to receive data from Object array
     */
    @Override
    public void getData() {
        objects=this.scr.getData();
        this.source=(String)objects[0];
        this.destination=(String)objects[1];
        this.numberOfSeats=(Integer)objects[2];
        this.date=(Calendar)objects[3];
        this.read=true;
    }
    /**
     * Method to return the index of selected row
     * @return the index of the selected row
     */
    @Override
    public int getChoice() {
        return scr.getSelectedRow();
    }
    /**
     * Method to show the main jForm Screen
     */
    @Override
    public void showScreen() {
        scr.init();
        scr.setVisible(true);
    }
    /**
     * Method to show search result in second tab
     * @param searchResult  required flight constraints
     * @return the existence of any valid flight combo
     */
    @Override
    public boolean showSearchResult(String[][] searchResult) {
        if(scr.fillTable(searchResult, numberOfSeats)>0){
            state = true;
            showNextTab(1);
        }
        else{
            infoBox("Sorry, No flight found for the current entries,\ntry searching for a new date and/or location.","");
            this.scr.dispose();
        }
        return state;
    }
    /**
     * Method to return if flight confirmed
     * @return the confirmation of a flight
     */
    public boolean isConfirmed() {
        return confirm;
    }
    /**
     * Method to return source
     * @return the source of the flight 
     */
    public String getSource() {
        return this.source;
    }
    /**
     * Method to return destination
     * @return the destination of the flight
     */
    public String getDestination() {
        return this.destination;
    }
    /**
     * Method to return the number of seats required
     * @return number of seats required
     */
    public int getNumberOfSeats() {
        return this.numberOfSeats;
    }
    /**
     * Method to return date of journey
     * @return the date of departure of flight
     */
    public Calendar getDate() {
        return this.date;
    }
    /**
     * Method to return if data from given flight database is read*
     * @return  boolean for data read
     */
    @Override
    public boolean isRead() {
        return this.read;
    }
    /**
     * Method to return whether the flight is selected or not
     * @return boolean for flight selection
     */
    public boolean isChoiceMarked() {
        return this.choiceMarked;
    }
    /**
     * Method to open a particular tab in the Screen form
     * @param index number of tab to be opened
     */
    @Override
    public void showNextTab(int index) {
        System.out.println(index);
        if(index==1)
            scr.showTab1();
        else if(index ==2)
            scr.showTab2();
        else if(index==0){
            scr.showTab0();
        }
    }
    /**
     * disposes the form Screen
     */
    public void closeScreen() {
        scr.setVisible(false);
        scr.dispose();
    }
}
