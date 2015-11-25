/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UI;

/**
 * Class to initialize the Display Manager
 * @author Anudeep Ghosh
 */
public class Display {
    DisplayManager displayManagerObj;
    public static void main(String[] args) {
        Display display = new Display();
        display.displayManagerObj = new DisplayManager();
        display.displayManagerObj.showScreen();
    }
}
