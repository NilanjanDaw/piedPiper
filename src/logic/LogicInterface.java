/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import data.Flight;
import java.util.ArrayList;

/**
 *
 * @author Nilanjan Daw
 */
public interface LogicInterface {
    public void searchFlight();
    public ArrayList<Flight> getFlightFoundSpice();
    public ArrayList<Flight> getFlightFoundSilk();
    public void confirmBook(int choice, int mode);
}
